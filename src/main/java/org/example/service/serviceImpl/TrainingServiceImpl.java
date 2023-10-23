package org.example.service.serviceImpl;

import org.example.configuration.Storage;
import org.example.model.Trainee;
import org.example.model.Trainer;
import org.example.model.Training;
import org.example.model.TrainingType;
import org.example.service.TrainingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class TrainingServiceImpl implements TrainingService {
    Storage storage;
    private static Logger logger = LoggerFactory.getLogger(TrainerServiceImpl.class);

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
        Training newTraining = (Training) storage.getTrainingDao().save(
                new Training(traineeId,trainee.get(),trainerId, trainer.get(),
                        trainingName, trainingTypeId, trainingType.get(), trainingDate, trainingDuration )
        );
        System.out.println(newTraining);
        logger.info("Creating Training Profile with id " + newTraining.getId());
        return newTraining;
    }

    @Override
    public Training selectTrainingProfile(int id) {
        logger.info("Selecting Training Profile with id " + id);
        return (Training) storage.getTrainingDao().get(id).get();
    }

    @Override
    public List<Training> selectAll() {
        logger.info("Selecting All Training Profiles");
        return storage.getTrainingDao().getAll();
    }
}
