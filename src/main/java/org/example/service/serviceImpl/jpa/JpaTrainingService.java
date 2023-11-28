package org.example.service.serviceImpl.jpa;

import org.example.model.Training;
import org.example.service.TrainingService;
import org.example.service.authentication.Credentials;

import java.util.List;

public interface JpaTrainingService extends TrainingService {
    List<Training> selectTraineeTrainingsByUsername(String username, String trainingName, Double trainingDuration);

    List<Training> selectTrainerTrainingsByUsername(String username, String trainingName, Double trainingDuration);


}
