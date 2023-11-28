package org.example.facade.inMemory;

import org.example.model.Trainee;
import org.example.model.Trainer;
import org.example.model.Training;

import java.util.Date;

public interface GymFacade {
    int addTrainee(String firstName, String lastName, Date dateOfBirth, String address);

    boolean updateTrainee(int id, String firstName, String lastName, boolean isActive, Date dateOfBirth, String address);

    boolean deleteTrainee(int id);

    Trainee getTrainee(int id);

    int addTrainer(String firstName, String lastName, int specialization);

    boolean updateTrainer(int id, String firstName, String lastName, boolean isActive, int specialization);

    Trainer getTrainer(int id);

    int addTraining(int traineeId, int trainerId, String trainingName, int trainingTypeId, Date trainingDate, double trainingDuration);

    Training getTraining(int id);

}
