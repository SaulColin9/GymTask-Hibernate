package org.example.facade.jpa;

import org.example.facade.inMemory.GymFacade;
import org.example.model.Trainee;
import org.example.model.Trainer;
import org.example.model.Training;
import org.example.service.authentication.Credentials;

import java.util.Date;
import java.util.List;

public class JpaGymFacadeImpl implements JpaGymFacade{
    @Override
    public int addTrainee(String firstName, String lastName, Date dateOfBirth, String address) {
        return 0;
    }

    @Override
    public boolean updateTrainee(Credentials credentials, int id, String firstName, String lastName, boolean isActive, Date dateOfBirth, String address) {
        return false;
    }

    @Override
    public boolean deleteTrainee(Credentials credentials, int id) {
        return false;
    }

    @Override
    public Trainee getTrainee(Credentials credentials, int id) {
        return null;
    }

    @Override
    public Trainee getTraineeByUsername(Credentials credentials, String username) {
        return null;
    }

    @Override
    public Trainee updateTraineePassword(Credentials credentials, String newPassword) {
        return null;
    }

    @Override
    public void updateActiveTraineeStatus(Credentials credentials, boolean isActive) {

    }

    @Override
    public boolean deleteTraineeByUsername(Credentials credentials, int id) {
        return false;
    }

    @Override
    public int addTrainer(String firstName, String lastName, int specialization) {
        return 0;
    }

    @Override
    public boolean updateTrainer(Credentials credentials, int id, String firstName, String lastName, boolean isActive, int specialization) {
        return false;
    }

    @Override
    public Trainer getTrainer(Credentials credentials, int id) {
        return null;
    }

    @Override
    public Trainer getTrainerByUsername(Credentials credentials, String username) {
        return null;
    }

    @Override
    public Trainer updateTrainerPassword(Credentials credentials, String newPassword) {
        return null;
    }

    @Override
    public void updateActiveTrainerStatus(Credentials credentials, boolean isActive) {

    }

    @Override
    public int addTraining(Credentials credentials, int traineeId, int trainerId, String trainingName, int trainingTypeId, Date trainingDate, double trainingDuration) {
        return 0;
    }

    @Override
    public Training getTraining(Credentials credentials, int id) {
        return null;
    }

    @Override
    public List<Training> getTraineeTrainings(Credentials credentials, String username) {
        return null;
    }

    @Override
    public List<Training> getTraineeTrainingsByTrainingName(Credentials credentials, String username, String trainingName) {
        return null;
    }

    @Override
    public List<Training> getTraineeTrainingsByTrainingDuration(Credentials credentials, String username, Double trainingDuration) {
        return null;
    }

    @Override
    public List<Training> getTrainerTrainingsByUsername(Credentials credentials, String username) {
        return null;
    }

    @Override
    public List<Training> getTrainerTrainingsByTrainingName(Credentials credentials, String username, String trainingName) {
        return null;
    }

    @Override
    public List<Training> getTrainerTrainingsByTrainingDuration(Credentials credentials, String username, Double trainingDuration) {
        return null;
    }
}
