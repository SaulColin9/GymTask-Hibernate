package org.example.service;

import org.example.model.Trainee;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TraineeService {
    Trainee createTraineeProfile(String firstName, String lastName, Date dateOfBirth, String address);
    Trainee updateTraineeProfile(int id, String firstName, String lastName, boolean isActive, Date dateOfBirth, String address);
    Trainee deleteTraineeProfile(int id);
    Trainee selectTraineeProfile(int id);
    List<Trainee> selectAll();
}
