package org.example.service.serviceimpl.jpa;

import org.example.dao.jpa.JpaDaoTrainingImpl;
import org.example.model.Training;
import org.example.service.serviceimpl.TrainingServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JpaTrainingServiceImpl extends TrainingServiceImpl implements JpaTrainingService {

    @Override
    public List<Training> selectTraineeTrainingsByUsername(String username, String trainingName, Double trainingDuration) {
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        validator.validateFieldsNotNull(params);

        logger.info("Selecting Trainee with username {} Trainings", username);
        return ((JpaDaoTrainingImpl) trainingDao).getTrainingsByTraineeUsername(username, trainingName, trainingDuration);
    }

    @Override
    public List<Training> selectTrainerTrainingsByUsername(String username, String trainingName, Double trainingDuration) {
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        validator.validateFieldsNotNull(params);

        logger.info("Selecting Trainer with username {} Trainings", username);
        return ((JpaDaoTrainingImpl) trainingDao).getTrainingsByTrainerUsername(username, trainingName, trainingDuration);
    }
}
