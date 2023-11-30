package org.example.service.serviceimpl.jpa;

import org.example.model.Training;
import org.example.service.TrainingService;

import java.util.List;

public interface JpaTrainingService extends TrainingService {
    List<Training> selectTraineeTrainingsByUsername(String username, String trainingName, Double trainingDuration);

    List<Training> selectTrainerTrainingsByUsername(String username, String trainingName, Double trainingDuration);


}
