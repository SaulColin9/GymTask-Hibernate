package org.example.service.serviceimpl.jpa;

import org.example.model.Trainer;
import org.example.service.TrainerService;

import java.util.List;

public interface JpaTrainerService extends TrainerService {
    Trainer selectTrainerProfileByUsername(String username);

    boolean updateTrainerPassword(int id, String newPassword);

    boolean updateTrainerActiveStatus(int id, boolean isActive);

    List<Trainer> updateTraineeTrainersList(int traineeId, int trainerId);
}
