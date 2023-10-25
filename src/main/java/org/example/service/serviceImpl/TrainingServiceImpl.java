package org.example.service.serviceImpl;

import org.example.dao.Dao;
import org.example.model.*;
import org.example.service.TrainingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Optional;

public class TrainingServiceImpl implements TrainingService {
    private Dao<Trainee> traineeDao;
    private Dao<Trainer> trainerDao;
    private Dao<TrainingType> trainingTypeDao;
    private Dao<Training> trainingDao;
    private static final Logger logger = LoggerFactory.getLogger(TrainerServiceImpl.class);


    @Override
    public int createTrainingProfile(int traineeId, int trainerId, String trainingName, int trainingTypeId, Date trainingDate, double trainingDuration) {
        if (trainingName == null || trainingDate == null) {
            logger.error("The next fields were not provided " +
                    (trainingName == null ? "trainingName, " : "") +
                    (trainingDate == null ? "trainingDate, " : "")
            );
            return -1;
        }
        if (traineeId <= 0 || trainingTypeId <= 0 || trainerId <= 0
                || trainingDuration < 0) {
            logger.error("The next fields are invalid" +
                    (traineeId <= 0 ? "traineeId, " : "") +
                    (trainerId <= 0 ? "trainerId, " : "") +
                    (trainingTypeId <= 0 ? "trainingTypeId, " : "") +
                    (trainingDuration < 0 ? "trainingDuration " : "")
            );
            return -1;
        }
        Optional<Trainee> trainee = traineeDao.get(traineeId);
        Optional<Trainer> trainer = trainerDao.get(trainerId);
        Optional<TrainingType> trainingType = trainingTypeDao.get(trainingTypeId);
        if (trainee.isEmpty() || trainer.isEmpty() || trainingType.isEmpty()) {
            logger.error("The next Ids do not exist: " +
                    (trainee.isEmpty() ? "traineeId, " : "") +
                    (trainer.isEmpty() ? "trainerId, " : "") +
                    (trainingType.isEmpty() ? "trainingTypeId " : "")
            );
            return -1;
        }
        Training newTraining = trainingDao.save(
                new Training(trainee.get(), trainer.get(),
                        trainingName, trainingType.get(), trainingDate, trainingDuration)
        );
        logger.info("Creating Training Profile with id " + newTraining.getId());
        return newTraining.getId();
    }

    @Override
    public Training selectTrainingProfile(int id) {
        Optional<Training> training = trainingDao.get(id);
        if (training.isEmpty()) {
            logger.info("Provided Training Id does not exist" + id);
            return null;
        }
        logger.info("Selecting Training Profile with id " + id);
        return training.get();
    }

    public void setTraineeDao(Dao<Trainee> traineeDao) {
        this.traineeDao = traineeDao;
    }

    public void setTrainerDao(Dao<Trainer> trainerDao) {
        this.trainerDao = trainerDao;
    }

    public void setTrainingTypeDao(Dao<TrainingType> trainingTypeDao) {
        this.trainingTypeDao = trainingTypeDao;
    }

    public void setTrainingDao(Dao<Training> trainingDao) {
        this.trainingDao = trainingDao;
    }

}
