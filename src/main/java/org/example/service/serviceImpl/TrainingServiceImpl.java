package org.example.service.serviceImpl;

import org.example.configuration.Storage;
import org.example.model.*;
import org.example.service.TrainingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class TrainingServiceImpl implements TrainingService {
    Storage storage;
    private static final Logger logger = LoggerFactory.getLogger(TrainerServiceImpl.class);

    public TrainingServiceImpl(Storage storage){
        this.storage = storage;
    }

    @Override
    public Training createTrainingProfile(int traineeId, int trainerId, String trainingName, int trainingTypeId, Date trainingDate, double trainingDuration) {
        Optional<Trainee> trainee = storage.getTraineeDao().get(traineeId);
        Optional<Trainer> trainer = storage.getTrainerDao().get(trainerId);
        Optional<TrainingType> trainingType = storage.getTrainingTypeDao().get(trainingTypeId);
        if(trainee.isEmpty() || trainer.isEmpty() || trainingType.isEmpty()){
            logger.error("The next Ids do not exist: " +
                    (trainee.isEmpty()? "traineeId, ": "")+
                    (trainer.isEmpty()? "trainerId, ": "")+
                    (trainingType.isEmpty()? "trainingTypeId ": "")
            );
            return null;
        }
        Training newTraining = storage.getTrainingDao().save(
                new Training(trainee.get(), trainer.get(),
                        trainingName, trainingType.get(), trainingDate, trainingDuration )
        );
        System.out.println(newTraining);
        logger.info("Creating Training Profile with id " + newTraining.getId());
        return newTraining;
    }

    @Override
    public Training selectTrainingProfile(int id) {
        Training training  = storage.getTrainingDao().get(id).orElse(null);
        Optional<Trainer> trainer = storage.getTrainerDao().get(training.getTrainerId());
        Optional<User> trainersUser = storage.getUserDao().get(trainer.get().getUserId());
        trainer.get().setUser(trainersUser.orElse(null));
        Optional<Trainee> trainee = storage.getTraineeDao().get(training.getTraineeId());
        Optional<User> traineesUser = storage.getUserDao().get(trainee.get().getUserId());
        trainee.get().setUser(traineesUser.orElse(null));
        Optional<TrainingType> trainingType = storage.getTrainingTypeDao().get(training.getTrainingTypeId());

        training.setTrainer(trainer.orElse(null));
        training.setTrainee(trainee.orElse(null));
        training.setTrainingType(trainingType.orElse(null));

        logger.info("Selecting Training Profile with id " + id);
        return training;
    }

    @Override
    public List<Training> selectAll() {
        List<Training> trainings = storage.getTrainingDao().getAll();
        for(Training training: trainings){
            Optional<Trainer> trainer = storage.getTrainerDao().get(training.getTrainerId());
            Optional<User> trainersUser = storage.getUserDao().get(trainer.get().getUserId());
            trainer.get().setUser(trainersUser.orElse(null));
            Optional<Trainee> trainee = storage.getTraineeDao().get(training.getTraineeId());
            Optional<User> traineesUser = storage.getUserDao().get(trainee.get().getUserId());
            trainee.get().setUser(traineesUser.orElse(null));
            Optional<TrainingType> trainingType = storage.getTrainingTypeDao().get(training.getTrainingTypeId());

            training.setTrainer(trainer.orElse(null));
            training.setTrainee(trainee.orElse(null));
            training.setTrainingType(trainingType.orElse(null));
        }
        logger.info("Selecting All Training Profiles");
        return trainings;
    }
}
