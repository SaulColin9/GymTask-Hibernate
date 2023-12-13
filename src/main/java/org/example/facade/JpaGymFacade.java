package org.example.facade;

import org.example.model.Trainee;
import org.example.model.Trainer;
import org.example.model.Training;
import org.example.service.authentication.Credentials;

import java.util.List;

public interface JpaGymFacade {
    Trainee getTraineeByUsername(Credentials credentials, String username);

    boolean updateTraineePassword(Credentials credentials, int id, String newPassword);

    boolean updateActiveTraineeStatus(Credentials credentials, int id, boolean isActive);

    boolean deleteTraineeByUsername(Credentials credentials, String username);

    Trainer getTrainerByUsername(Credentials credentials, String username);

    boolean updateTrainerPassword(Credentials credentials, int id, String newPassword);

    boolean updateActiveTrainerStatus(Credentials credentials, int id, boolean isActive);

    List<Training> getTraineeTrainingsByUsername(String username);

    List<Training> getTraineeTrainingsByUsernameAndTrainingType(String username, Integer trainingTypeId);

    List<Training> getTrainerTrainingsByUsername(String username);

    List<Training> getTrainerTrainingsByUsernameAndTrainingCompleteness(String username, Boolean isCompleted);


    List<Trainer> updateTraineeTrainersList(Credentials credentials, int traineeId, int trainerId);

    List<Trainer> getNotAssignedOnTraineeTrainersList(Credentials credentials, int traineeId);


}
