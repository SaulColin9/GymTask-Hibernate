package org.example.service.serviceImpl.jpa;

import org.example.model.Trainee;
import org.example.model.Trainer;
import org.example.service.TraineeService;

import java.util.List;
import java.util.Optional;

public interface JpaTraineeService extends TraineeService {
    Trainee selectTraineeProfileByUsername(String username);

    boolean deleteTraineeProfileByUsername(String username);

    boolean updateTraineePassword(int id, String newPassword);

    boolean updateTraineeTraineeStatus(int id, boolean isActive);

    List<Trainer> selectNotAssignedOnTraineeTrainersList(int trainee_id);

}
