package org.example.service.serviceImpl.jpa;

import org.example.model.Trainee;
import org.example.model.Trainer;
import org.example.service.TrainerService;

import java.util.Optional;

public interface JpaTrainerService extends TrainerService {
    Trainer selectTrainerProfileByUsername(String username);

    boolean updateTrainerPassword(int id, String newPassword);

    boolean updateTrainerTraineeStatus(int id, boolean isActive);
}
