package org.example.service;

import org.example.dao.Dao;
import org.example.model.Trainee;
import org.example.model.User;

import java.util.Date;
import java.util.List;

public interface TraineeService {
    Trainee createTraineeProfile(String firstName, String lastName, Date dateOfBirth, String address);
    Trainee updateTraineeProfile(int id, String firstName, String lastName, boolean isActive, Date dateOfBirth, String address);
    Trainee deleteTraineeProfile(int id);
    Trainee selectTraineeProfile(int id);
    List<Trainee> selectAll();
    void setTraineeDao(Dao<Trainee> traineeDao);
    void setUserDao(Dao<User> userDao);
}
