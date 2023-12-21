package org.example.service.serviceimpl;

import org.example.dao.Dao;
import org.example.model.Trainee;
import org.example.model.Trainer;
import org.example.model.Training;
import org.example.model.TrainingType;
import org.example.service.TrainingService;
import org.example.service.utils.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TrainingServiceImpl implements TrainingService {
    private Dao<Trainee> traineeDao;
    private Dao<Trainer> trainerDao;
    private Dao<TrainingType> trainingTypeDao;
    protected Dao<Training> trainingDao;
    protected static final Logger logger = LoggerFactory.getLogger(TrainerServiceImpl.class);
    protected Validator<Training> validator;


    @Override
    public Training createTrainingProfile(int traineeId, int trainerId, String trainingName, int trainingTypeId, Date trainingDate, double trainingDuration) {
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
                new Training(trainee.orElse(null), trainer.orElse(null),
                        trainingName, trainingType.orElse(null), trainingDate, trainingDuration)
        );
        logger.info("Creating Training Profile with id " + newTraining.getId());
        return newTraining;
    }

    @Override
    public Training selectTrainingProfile(int id) {
        Optional<Training> training = trainingDao.get(id);
        validator.validateEntityNotNull(id, training.orElse(null));

        logger.info("Selecting Training Profile with id " + id);
        return training.orElse(null);
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
