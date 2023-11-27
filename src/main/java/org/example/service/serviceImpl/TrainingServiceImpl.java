package org.example.service.serviceImpl;

import org.example.dao.Dao;
import org.example.model.*;
import org.example.service.TrainingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class TrainingServiceImpl implements TrainingService {
    private Dao<Trainee> traineeDao;
    private Dao<Trainer> trainerDao;
    private Dao<TrainingType> trainingTypeDao;
    private Dao<Training> trainingDao;
    private static final Logger logger = LoggerFactory.getLogger(TrainerServiceImpl.class);
    private static final String NO_ID_MSG = "Provided Training Id does not exist";


    @Override
    public int createTrainingProfile(int traineeId, int trainerId, String trainingName, int trainingTypeId, Date trainingDate, double trainingDuration) {
        if (!validateFields(trainingName, trainingDate) || !validateIds(traineeId, trainerId, trainingTypeId, trainingDuration)) {
            throw new IllegalArgumentException("Invalid fields were provided");
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
            throw new IllegalArgumentException("Provided ids were not found");

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
            logger.info(NO_ID_MSG);
            throw new IllegalArgumentException(NO_ID_MSG);
        }
        logger.info("Selecting Training Profile with id " + id);
        return training.get();
    }

    public List<Training> selectTrainingsByTraineeUsername(String username) {
        return null;
    }

    private boolean validateFields(String trainingName, Date trainingDate) {
        if (trainingName == null || trainingDate == null) {
            logger.error("The following fields were not provided: " +
                    (trainingName == null ? "trainingName, " : "") +
                    (trainingDate == null ? "trainingDate" : "")
            );
            return false;
        }
        return true;
    }

    private boolean validateIds(int traineeId, int trainerId, int trainingTypeId, double trainingDuration) {
        if (traineeId <= 0 || trainerId <= 0 || trainingTypeId <= 0 || trainingDuration < 0) {
            logger.error("The following fields are invalid: " +
                    (traineeId <= 0 ? "traineeId, " : "") +
                    (trainerId <= 0 ? "trainerId, " : "") +
                    (trainingTypeId <= 0 ? "trainingTypeId, " : "") +
                    (trainingDuration < 0 ? "trainingDuration" : "")
            );
            return false;
        }
        return true;
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
