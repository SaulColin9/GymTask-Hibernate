package org.example.service;

import org.example.dao.Dao;
import org.example.dao.DaoImpl;
import org.example.model.*;

import java.util.Date;
import java.util.List;

public interface TrainingService {
    Training createTrainingProfile(int traineeId, int trainerId, String trainingName, int trainingTypeId, Date trainingDate, double trainingDuration);
    Training selectTrainingProfile(int id);
    void setTraineeDao(Dao<Trainee> traineeDao);

    void setTrainerDao(Dao<Trainer> trainerDao);

    void setTrainingTypeDao(Dao<TrainingType> trainingTypeDao);

    void setTrainingDao(Dao<Training> trainingDao);

    void setUserDao(Dao<User> userDao);
}
