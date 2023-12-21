package org.example.facade;

import org.example.model.Trainee;
import org.example.model.Trainer;
import org.example.model.Training;
import org.example.service.authentication.Credentials;

import java.util.Date;

public interface SimpleGymFacade {
    Trainee addTrainee(String firstName, String lastName, Date dateOfBirth, String address);

    boolean updateTrainee(Credentials credentials, int id, String firstName, String lastName, boolean isActive, Date dateOfBirth, String address);

    boolean deleteTrainee(Credentials credentials, int id);

    Trainee getTrainee(Credentials credentials, int id);

    Trainer addTrainer(String firstName, String lastName, int specialization);

    boolean updateTrainer(Credentials credentials, int id, String firstName, String lastName, boolean isActive, int specialization);

    Trainer getTrainer(Credentials credentials, int id);

    Training addTraining(int traineeId, int trainerId, String trainingName, int trainingTypeId, Date trainingDate, double trainingDuration);

    Training getTraining(int id);
}
