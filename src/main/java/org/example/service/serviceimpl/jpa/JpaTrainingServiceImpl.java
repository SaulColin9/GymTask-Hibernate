package org.example.service.serviceimpl.jpa;

import org.example.dao.jpa.JpaDaoTrainingImpl;
import org.example.model.Training;
import org.example.service.serviceimpl.TrainingServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}
