package org.example.service;

import org.example.model.Trainee;

import java.util.Date;

public interface TraineeService {
    int createTraineeProfile(String firstName, String lastName, Date dateOfBirth, String address);

    boolean updateTraineeProfile(int id, String firstName, String lastName, boolean isActive, Date dateOfBirth, String address);

    boolean deleteTraineeProfile(int id);

    Trainee selectTraineeProfile(int id);

}
