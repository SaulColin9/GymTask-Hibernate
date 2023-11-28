package org.example.service.serviceImpl.jpa;

import org.example.model.Trainee;
import org.example.service.TraineeService;

import java.util.Optional;

public interface JpaTraineeService extends TraineeService {
    Optional<Trainee> selectTraineeProfileByUsername(String username);

    boolean deleteTraineeProfileByUsername(String username);

    boolean updateTraineePassword(int id, String newPassword);

    void updateTraineeTraineeStatus(int id, boolean isActive);
}

