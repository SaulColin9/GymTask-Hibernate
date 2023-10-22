package org.example.service;

import org.example.model.Trainee;

import java.util.Date;
import java.util.Optional;

public interface TraineeService {
    Trainee createTraineeProfile(String firstName, String lastName, Date dateOfBirth, String address);
    Trainee updateTraineeProfile(int id, Trainee trainee);
    Optional<Trainee> deleteTraineeProfile(int id);
    Optional<Trainee> selectTraineeProfile(int id);
}
