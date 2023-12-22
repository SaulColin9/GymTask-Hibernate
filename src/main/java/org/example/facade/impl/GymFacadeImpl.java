package org.example.facade.impl;

import org.example.facade.JpaGymFacade;
import org.example.facade.SimpleGymFacade;
import org.example.model.Trainee;
import org.example.model.Trainer;
import org.example.model.Training;
import org.example.model.User;
import org.example.service.TraineeService;
import org.example.service.TrainerService;
import org.example.service.TrainingService;
import org.example.service.authentication.Credentials;
import org.example.service.authentication.CredentialsAuthenticator;
import org.example.service.serviceimpl.jpa.JpaTraineeService;
import org.example.service.serviceimpl.jpa.JpaTrainerService;
import org.example.service.serviceimpl.jpa.JpaTrainingService;

import java.util.Date;
import java.util.List;

public class GymFacadeImpl implements JpaGymFacade, SimpleGymFacade {
    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final TrainingService trainingService;
    private CredentialsAuthenticator credentialsAuthenticator;

    public GymFacadeImpl(TraineeService traineeService,
                         TrainerService trainerService,
                         TrainingService trainingService) {
        this.traineeService = traineeService;
        this.trainerService = trainerService;
        this.trainingService = trainingService;
    }


    @Override
    public Trainee addTrainee(String firstName, String lastName, Date dateOfBirth, String address) {
        return traineeService.createTraineeProfile(firstName, lastName, dateOfBirth, address);
    }

    @Override
    public boolean updateTrainee(Credentials credentials, int id, String firstName, String lastName, boolean isActive, Date dateOfBirth, String address) {
        Trainee trainee = traineeService.selectTraineeProfile(id);
        executeAuth(credentials, trainee.getUser());
        return traineeService.updateTraineeProfile(id, firstName, lastName, isActive, dateOfBirth, address);
    }

    @Override
    public boolean deleteTrainee(Credentials credentials, int id) {
        Trainee trainee = traineeService.selectTraineeProfile(id);
        executeAuth(credentials, trainee.getUser());
        return traineeService.deleteTraineeProfile(id);
    }

    @Override
    public Trainee getTrainee(Credentials credentials, int id) {
        Trainee trainee = traineeService.selectTraineeProfile(id);
        executeAuth(credentials, trainee.getUser());
        return trainee;
    }

    @Override
    public Trainee getTraineeByUsername(Credentials credentials, String username) {
        Trainee trainee = ((JpaTraineeService) traineeService).selectTraineeProfileByUsername(username);
        executeAuth(credentials, trainee.getUser());
        return trainee;
    }

    @Override
    public boolean updateTraineePassword(Credentials credentials, int id, String newPassword) {
        Trainee trainee = traineeService.selectTraineeProfile(id);
        executeAuth(credentials, trainee.getUser());
        return ((JpaTraineeService) traineeService).updateTraineePassword(id, newPassword);
    }

    @Override
    public boolean updateActiveTraineeStatus(Credentials credentials, int id, boolean isActive) {
        Trainee trainee = traineeService.selectTraineeProfile(id);
        executeAuth(credentials, trainee.getUser());
        return ((JpaTraineeService) traineeService).updateTraineeActiveStatus(id, isActive);
    }

    @Override
    public boolean deleteTraineeByUsername(Credentials credentials, String username) {
        Trainee trainee = ((JpaTraineeService) traineeService).selectTraineeProfileByUsername(username);
        executeAuth(credentials, trainee.getUser());
        return ((JpaTraineeService) traineeService).deleteTraineeProfileByUsername(username);
    }

    @Override
    public Trainer addTrainer(String firstName, String lastName, int specialization) {
        return trainerService.createTrainerProfile(firstName, lastName, specialization);
    }

    @Override
    public boolean updateTrainer(Credentials credentials, int id, String firstName, String lastName, boolean isActive, int specialization) {
        Trainer trainer = trainerService.selectTrainerProfile(id);
        executeAuth(credentials, trainer.getUser());

        return trainerService.updateTrainerProfile(id, firstName, lastName, isActive, specialization);
    }

    @Override
    public Trainer getTrainer(Credentials credentials, int id) {
        Trainer trainer = trainerService.selectTrainerProfile(id);
        executeAuth(credentials, trainer.getUser());
        return trainer;
    }

    @Override
    public Trainer getTrainerByUsername(Credentials credentials, String username) {
        Trainer trainer = ((JpaTrainerService) trainerService).selectTrainerProfileByUsername(username);
        executeAuth(credentials, trainer.getUser());
        return trainer;
    }

    @Override
    public boolean updateTrainerPassword(Credentials credentials, int id, String newPassword) {
        Trainer trainer = trainerService.selectTrainerProfile(id);
        executeAuth(credentials, trainer.getUser());
        return ((JpaTrainerService) trainerService).updateTrainerPassword(id, newPassword);
    }

    @Override
    public boolean updateActiveTrainerStatus(Credentials credentials, int id, boolean isActive) {
        Trainer trainer = trainerService.selectTrainerProfile(id);
        executeAuth(credentials, trainer.getUser());
        return ((JpaTrainerService) trainerService).updateTrainerActiveStatus(id, isActive);
    }

    @Override
    public Training addTraining(int traineeId, int trainerId, String trainingName, int trainingTypeId, Date trainingDate, double trainingDuration) {
        return trainingService.createTrainingProfile(traineeId, trainerId, trainingName, trainingTypeId, trainingDate, trainingDuration);
    }

    @Override
    public Training getTraining(int id) {
        return trainingService.selectTrainingProfile(id);
    }

    @Override
    public List<Training> getTraineeTrainings(String username,
                                              String trainingTypeName,
                                              Double minDuration,
                                              Double maxDuration) {
        return ((JpaTrainingService) trainingService).selectTraineeTrainings(username, trainingTypeName, minDuration, maxDuration);
    }

    @Override
    public List<Training> getTrainerTrainings(String username,
                                              Boolean isCompleted,
                                              String trainingName) {
        return ((JpaTrainingService) trainingService).selectTrainerTrainings(username, isCompleted, trainingName);
    }


    @Override
    public List<Trainer> updateTraineeTrainersList(Credentials credentials, int traineeId, int trainerId) {
        Trainee trainee = traineeService.selectTraineeProfile(traineeId);
        executeAuth(credentials, trainee.getUser());

        return ((JpaTrainerService) trainerService).updateTraineeTrainersList(traineeId, trainerId);
    }

    @Override
    public List<Trainer> getNotAssignedOnTraineeTrainersList(Credentials credentials, int traineeId) {
        Trainee trainee = traineeService.selectTraineeProfile(traineeId);
        executeAuth(credentials, trainee.getUser());

        return ((JpaTraineeService) traineeService).selectNotAssignedOnTraineeTrainersList(traineeId);
    }

    public void setCredentialsAuthenticator(CredentialsAuthenticator credentialsAuthenticator) {
        this.credentialsAuthenticator = credentialsAuthenticator;
    }

    private void executeAuth(Credentials credentials, User user) {
        credentialsAuthenticator.authorize(credentials, user);
    }
}
