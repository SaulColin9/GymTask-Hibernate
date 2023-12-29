package org.example.service.serviceimpl.jpa;

import org.example.dao.jpa.JpaDaoTraineeImpl;
import org.example.dao.jpa.JpaDaoTrainerImpl;
import org.example.dao.jpa.JpaDaoTrainingImpl;
import org.example.model.Trainee;
import org.example.model.Trainer;
import org.example.model.Training;
import org.example.model.TrainingType;
import org.example.service.serviceimpl.TrainingServiceImpl;

import java.util.*;

public class JpaTrainingServiceImpl extends TrainingServiceImpl implements JpaTrainingService {


    @Override
    public List<Training> selectTraineeTrainings(String username, String trainingTypeName,
                                                 Double minDuration, Double maxDuration) {
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        validator.validateFieldsNotNull(params);

        logger.info("Selecting Trainings with Trainee username {}", username);
        return ((JpaDaoTrainingImpl) trainingDao).getTraineeTrainings(username, trainingTypeName, minDuration, maxDuration);
    }


    @Override
    public List<Training> selectTrainerTrainings(String username, Boolean isCompleted, String trainingName) {
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        validator.validateFieldsNotNull(params);

        logger.info("Selecting Trainings with Trainer username {}", username);
        return ((JpaDaoTrainingImpl) trainingDao).getTrainingsByTrainerUsername(username, isCompleted, trainingName);
    }

    @Override
    public Training createTrainingProfile(String traineeUsername, String trainerUsername, String trainingName,
                                          int trainingTypeId, Date trainingDate, double trainingDuration) {
        Map<String, Object> params = new HashMap<>();
        params.put("trainingName", trainingName);
        params.put("trainingDate", trainingDate);
        params.put("traineeUsername", traineeUsername);
        params.put("trainerUsername", trainerUsername);
        validator.validateFieldsNotNull(params);
        validator.validatePositiveField("trainingTypeId", trainingTypeId);
        validator.validatePositiveField("trainingDuration", (int) trainingDuration);

        Optional<Trainee> trainee = ((JpaDaoTraineeImpl) traineeDao).getByUsername(traineeUsername);
        Optional<Trainer> trainer = ((JpaDaoTrainerImpl)trainerDao).getByUsername(trainerUsername);
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
}
