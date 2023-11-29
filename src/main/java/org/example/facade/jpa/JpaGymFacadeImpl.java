package org.example.facade.jpa;

import org.example.facade.inMemory.GymFacade;
import org.example.model.Trainee;
import org.example.model.Trainer;
import org.example.model.Training;
import org.example.service.TraineeService;
import org.example.service.TrainerService;
import org.example.service.TrainingService;
import org.example.service.authentication.Credentials;
import org.example.service.authentication.CredentialsAuthenticator;
import org.example.service.authentication.CredentialsAuthenticatorImpl;
import org.example.service.serviceImpl.jpa.JpaTraineeService;
import org.example.service.serviceImpl.jpa.JpaTrainerService;
import org.example.service.serviceImpl.jpa.JpaTrainingService;

import javax.naming.AuthenticationException;
import java.util.Date;
import java.util.List;

public class JpaGymFacadeImpl implements JpaGymFacade {
    private final JpaTraineeService traineeService;
    private final JpaTrainerService trainerService;
    private final JpaTrainingService trainingService;
    private CredentialsAuthenticator credentialsAuthenticator;

    public JpaGymFacadeImpl(JpaTraineeService traineeService,
                            JpaTrainerService trainerService,
                            JpaTrainingService trainingService) {
        this.traineeService = traineeService;
        this.trainerService = trainerService;
        this.trainingService = trainingService;
    }


    @Override
    public int addTrainee(String firstName, String lastName, Date dateOfBirth, String address) {
        return traineeService.createTraineeProfile(firstName, lastName, dateOfBirth, address);
    }

    @Override
    public boolean updateTrainee(Credentials credentials, int id, String firstName, String lastName, boolean isActive, Date dateOfBirth, String address) {
        Trainee trainee = traineeService.selectTraineeProfile(id);
        try {
            credentialsAuthenticator.authorize(credentials, trainee.getUser());
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }
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
        return trainerService.createTrainerProfile(firstName, lastName, specialization);
    }

    @Override
    public boolean updateTrainer(Credentials credentials, int id, String firstName, String lastName, boolean isActive, int specialization) {
        Trainer trainer = trainerService.selectTrainerProfile(id);
        try {
            credentialsAuthenticator.authorize(credentials, trainer.getUser());
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }
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

    public void setCredentialsAuthenticator(CredentialsAuthenticator credentialsAuthenticator) {
        this.credentialsAuthenticator = credentialsAuthenticator;
    }
}
