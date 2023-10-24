package org.example.service.serviceImpl;

import org.example.configuration.Storage;
import org.example.dao.DaoImpl;
import org.example.model.*;
import org.example.service.TrainingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class TrainingServiceImpl implements TrainingService {
    Storage storage;
    DaoImpl<Trainee> traineeDao;
    DaoImpl<Trainer> trainerDao;
    DaoImpl<TrainingType> trainingTypeDao;
    DaoImpl<Training> trainingDao;
    DaoImpl<User> userDao;
    private static final Logger logger = LoggerFactory.getLogger(TrainerServiceImpl.class);

    public TrainingServiceImpl(Storage storage){
        this.storage = storage;
    }

    @Override
    public Training createTrainingProfile(int traineeId, int trainerId, String trainingName, int trainingTypeId, Date trainingDate, double trainingDuration) {
        Optional<Trainee> trainee = traineeDao.get(traineeId);
        Optional<Trainer> trainer = trainerDao.get(trainerId);
        Optional<TrainingType> trainingType = trainingTypeDao.get(trainingTypeId);
        if(trainee.isEmpty() || trainer.isEmpty() || trainingType.isEmpty()){
            logger.error("The next Ids do not exist: " +
                    (trainee.isEmpty()? "traineeId, ": "")+
                    (trainer.isEmpty()? "trainerId, ": "")+
                    (trainingType.isEmpty()? "trainingTypeId ": "")
            );
            return null;
        }
        Training newTraining = trainingDao.save(
                new Training(trainee.get(), trainer.get(),
                        trainingName, trainingType.get(), trainingDate, trainingDuration )
        );
        System.out.println(newTraining);
        logger.info("Creating Training Profile with id " + newTraining.getId());
        return newTraining;
    }

    @Override
    public Training selectTrainingProfile(int id) {
        Training training  = trainingDao.get(id).orElse(null);
        Optional<Trainer> trainer = trainerDao.get(training.getTrainerId());
        Optional<User> trainersUser = userDao.get(trainer.get().getUserId());
        trainer.get().setUser(trainersUser.orElse(null));
        Optional<Trainee> trainee = traineeDao.get(training.getTraineeId());
        Optional<User> traineesUser = userDao.get(trainee.get().getUserId());
        trainee.get().setUser(traineesUser.orElse(null));
        Optional<TrainingType> trainingType = trainingTypeDao.get(training.getTrainingTypeId());

        training.setTrainer(trainer.orElse(null));
        training.setTrainee(trainee.orElse(null));
        training.setTrainingType(trainingType.orElse(null));

        logger.info("Selecting Training Profile with id " + id);
        return training;
    }

    @Override
    public List<Training> selectAll() {
        List<Training> trainings = trainingDao.getAll();
        for(Training training: trainings){
            Optional<Trainer> trainer = trainerDao.get(training.getTrainerId());
            Optional<User> trainersUser = userDao.get(trainer.get().getUserId());
            trainer.get().setUser(trainersUser.orElse(null));
            Optional<Trainee> trainee = traineeDao.get(training.getTraineeId());
            Optional<User> traineesUser = userDao.get(trainee.get().getUserId());
            trainee.get().setUser(traineesUser.orElse(null));
            Optional<TrainingType> trainingType = trainingTypeDao.get(training.getTrainingTypeId());

            training.setTrainer(trainer.orElse(null));
            training.setTrainee(trainee.orElse(null));
            training.setTrainingType(trainingType.orElse(null));
        }
        logger.info("Selecting All Training Profiles");
        return trainings;
    }
}
