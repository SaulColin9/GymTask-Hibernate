package org.example.facade;

import org.example.model.Trainee;
import org.example.model.Trainer;
import org.example.model.Training;

import java.util.Date;
import java.util.List;

public interface GymFacade {
    Trainee addTrainee(String firstName, String lastName, Date dateOfBirth, String address);
    Trainee updateTrainee(int id,String firstName, String lastName, boolean isActive, Date dateOfBirth, String address);
    Trainee deleteTrainee(int id);
    Trainee getTrainee(int id);
    List<Trainee> getAllTrainees();

    Trainer addTrainer(String firstName, String lastName, int specialization);
    Trainer updateTrainer(int id, String firstName, String lastName, boolean isActive, int specialization);
    Trainer getTrainer(int id);
    List<Trainer> getAllTrainers();

    Training addTraining(int traineeId, int trainerId, String trainingName, int trainingTypeId, Date trainingDate, double trainingDuration);
    Training getTraining(int id);
    List<Training> getAllTrainings();

}
