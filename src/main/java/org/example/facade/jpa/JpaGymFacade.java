package org.example.facade.jpa;

import org.example.model.Trainee;
import org.example.model.Trainer;
import org.example.model.Training;
import org.example.service.authentication.Credentials;

import java.util.Date;
import java.util.List;

public interface JpaGymFacade {
    int addTrainee(String firstName, String lastName, Date dateOfBirth, String address);

    boolean updateTrainee(Credentials credentials, int id, String firstName, String lastName, boolean isActive, Date dateOfBirth, String address);

    boolean deleteTrainee(Credentials credentials, int id);

    Trainee getTrainee(Credentials credentials, int id);

    Trainee getTraineeByUsername(Credentials credentials, String username);

    Trainee updateTraineePassword(Credentials credentials, String newPassword);

    void updateActiveTraineeStatus(Credentials credentials, boolean isActive);

    boolean deleteTraineeByUsername(Credentials credentials, int id);

    int addTrainer(String firstName, String lastName, int specialization);

    boolean updateTrainer(Credentials credentials, int id, String firstName, String lastName, boolean isActive, int specialization);

    Trainer getTrainer(Credentials credentials, int id);

    Trainer getTrainerByUsername(Credentials credentials, String username);

    Trainer updateTrainerPassword(Credentials credentials, String newPassword);

    void updateActiveTrainerStatus(Credentials credentials, boolean isActive);


    int addTraining(Credentials credentials, int traineeId, int trainerId, String trainingName, int trainingTypeId, Date trainingDate, double trainingDuration);

    Training getTraining(Credentials credentials, int id);

    List<Training> getTraineeTrainings(Credentials credentials, String username);

    List<Training> getTraineeTrainingsByTrainingName(Credentials credentials, String username, String trainingName);

    List<Training> getTraineeTrainingsByTrainingDuration(Credentials credentials, String username, Double trainingDuration);

    List<Training> getTrainerTrainingsByUsername(Credentials credentials, String username);

    List<Training> getTrainerTrainingsByTrainingName(Credentials credentials, String username, String trainingName);

    List<Training> getTrainerTrainingsByTrainingDuration(Credentials credentials, String username, Double trainingDuration);


}
