package org.example.service;

import org.example.model.*;

import java.util.Date;

public interface TrainingService {
    int createTrainingProfile(int traineeId, int trainerId, String trainingName, int trainingTypeId, Date trainingDate, double trainingDuration);

    Training selectTrainingProfile(int id);

}
