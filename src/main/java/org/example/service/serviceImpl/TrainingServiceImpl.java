package org.example.service.serviceImpl;

import org.example.dao.Dao;
import org.example.model.*;
import org.example.service.TrainingService;
import org.example.service.utils.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class TrainingServiceImpl implements TrainingService {
    private Dao<Trainee> traineeDao;
    private Dao<Trainer> trainerDao;
    private Dao<TrainingType> trainingTypeDao;
    protected Dao<Training> trainingDao;
    private static final Logger logger = LoggerFactory.getLogger(TrainerServiceImpl.class);
    protected Validator<Training> validator;


    @Override
    public int createTrainingProfile(int traineeId, int trainerId, String trainingName, int trainingTypeId, Date trainingDate, double trainingDuration) {
        Map<String, Object> params = new HashMap<>();
        params.put("trainingName", trainingName);
        params.put("trainingDate", trainingDate);
        validator.validateFieldsNotNull(params);
        validator.validatePositiveField("traineeId", traineeId);
        validator.validatePositiveField("trainerId", trainerId);
        validator.validatePositiveField("trainingTypeId", trainingTypeId);
        validator.validatePositiveField("trainingDuration", (int) trainingDuration);

        Optional<Trainee> trainee = traineeDao.get(traineeId);
        Optional<Trainer> trainer = trainerDao.get(trainerId);
        Optional<TrainingType> trainingType = trainingTypeDao.get(trainingTypeId);

        Map<String, Object> entities = new HashMap<>();

        entities.put("trainee", trainee.orElse(null));
        entities.put("trainer", trainer.orElse(null));
        entities.put("trainingType", trainingType.orElse(null));
        validator.validateEntitiesNotNull(entities);

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
        validator.validateEntityNotNull(id, training);

        logger.info("Selecting Training Profile with id " + id);
        return training.get();
    }


    public void setValidator(Validator<Training> validator) {
        this.validator = validator;
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
