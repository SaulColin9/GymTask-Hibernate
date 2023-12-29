package org.example.service.serviceimpl.jpa;

import org.example.model.Training;
import org.example.service.TrainingService;

import java.util.Date;
import java.util.List;

public interface JpaTrainingService extends TrainingService {
    List<Training> selectTraineeTrainings(String username, String trainingTypeName,
                                          Double minDuration, Double maxDuration);

    List<Training> selectTrainerTrainings(String username, Boolean isCompleted, String trainingName);

    Training createTrainingProfile(String traineeUsername, String trainerUsername, String trainingName, int trainingTypeId,
                                   Date trainingDate, double trainingDuration);
}
