package org.example.service.serviceimpl.jpa;

import org.example.dao.jpa.JpaDaoTrainingImpl;
import org.example.model.Training;
import org.example.service.serviceimpl.TrainingServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JpaTrainingServiceImpl extends TrainingServiceImpl implements JpaTrainingService {


    @Override
    public List<Training> selectTraineeTrainingsByUsername(String username, Integer trainingTypeId) {
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        validator.validateFieldsNotNull(params);
        validator.validatePositiveField("trainingTypeId", trainingTypeId);

        logger.info("Selecting Trainings with Trainee username {}", username);
        return ((JpaDaoTrainingImpl) trainingDao).getTrainingsByTraineeUsername(username, trainingTypeId);
    }


    @Override
    public List<Training> selectTrainerTrainingsByUsername(String username, Boolean isCompleted) {
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        validator.validateFieldsNotNull(params);

        logger.info("Selecting Trainings with Trainer username {}", username);
        return ((JpaDaoTrainingImpl) trainingDao).getTrainingsByTrainerUsername(username, isCompleted);
    }
}
