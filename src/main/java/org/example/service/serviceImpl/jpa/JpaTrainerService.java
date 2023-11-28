package org.example.service.serviceImpl.jpa;

import org.example.model.Trainee;
import org.example.model.Trainer;
import org.example.service.TrainerService;

import java.util.Optional;

public interface JpaTrainerService extends TrainerService {
    Optional<Trainer> selectTrainerProfileByUsername(String username);

    boolean updateTrainerPassword(int id, String newPassword);

    void updateTrainerTraineeStatus(int id, boolean isActive);
}
