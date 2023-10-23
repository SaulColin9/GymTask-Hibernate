package org.example.service;

import org.example.model.Training;

import java.util.Date;
import java.util.List;

public interface TrainingService {
    Training createTrainingProfile(int traineeId, int trainerId, String trainingName, int trainingTypeId, Date trainingDate, double trainingDuration);
    Training selectTrainingProfile(int id);
    List<Training> selectAll();
}
