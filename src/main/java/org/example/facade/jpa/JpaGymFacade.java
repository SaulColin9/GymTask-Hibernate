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

    boolean updateTraineePassword(Credentials credentials, int id, String newPassword);

    boolean updateActiveTraineeStatus(Credentials credentials, int id, boolean isActive);

    boolean deleteTraineeByUsername(Credentials credentials, String username);

    int addTrainer(String firstName, String lastName, int specialization);

    boolean updateTrainer(Credentials credentials, int id, String firstName, String lastName, boolean isActive, int specialization);

    Trainer getTrainer(Credentials credentials, int id);

    Trainer getTrainerByUsername(Credentials credentials, String username);

    boolean updateTrainerPassword(Credentials credentials, int id, String newPassword);

    boolean updateActiveTrainerStatus(Credentials credentials, int id, boolean isActive);


    int addTraining(int traineeId, int trainerId, String trainingName, int trainingTypeId, Date trainingDate, double trainingDuration);

    Training getTraining(int id);

    List<Training> getTraineeTrainings(String username);

    List<Training> getTraineeTrainingsByTrainingName( String username, String trainingName);

    List<Training> getTraineeTrainingsByTrainingDuration(String username, Double trainingDuration);

    List<Training> getTrainerTrainingsByUsername(String username);

    List<Training> getTrainerTrainingsByTrainingName(String username, String trainingName);

    List<Training> getTrainerTrainingsByTrainingDuration(String username, Double trainingDuration);


}
