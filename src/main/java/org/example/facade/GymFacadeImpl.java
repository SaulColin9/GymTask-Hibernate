package org.example.facade;

import org.example.model.Trainee;
import org.example.model.Trainer;
import org.example.model.Training;
import org.example.service.TraineeService;
import org.example.service.TrainerService;
import org.example.service.TrainingService;

import java.util.Date;

public class GymFacadeImpl implements GymFacade {
    TraineeService traineeService;
    TrainerService trainerService;
    TrainingService trainingService;

    public GymFacadeImpl(TraineeService traineeService, TrainerService trainerService, TrainingService trainingService) {
        this.traineeService = traineeService;
        this.trainerService = trainerService;
        this.trainingService = trainingService;
    }

    @Override
    public Trainee addTrainee(String firstName, String lastName, Date dateOfBirth, String address) {
        return traineeService.createTraineeProfile(firstName, lastName, dateOfBirth, address);
    }

    @Override
    public Trainee updateTrainee(int id, String firstName, String lastName, boolean isActive, Date dateOfBirth, String address) {
        return traineeService.updateTraineeProfile(id, firstName, lastName, isActive, dateOfBirth, address);
    }

    @Override
    public Trainee deleteTrainee(int id) {
        return traineeService.deleteTraineeProfile(id);
    }

    @Override
    public Trainee getTrainee(int id) {
        return traineeService.selectTraineeProfile(id);
    }


    @Override
    public Trainer addTrainer(String firstName, String lastName, int specialization) {
        return trainerService.createTrainerProfile(firstName, lastName, specialization);
    }

    @Override
    public Trainer updateTrainer(int id, String firstName, String lastName, boolean isActive, int specialization) {
        return trainerService.updateTrainerProfile(id, firstName, lastName, isActive, specialization);
    }

    @Override
    public Trainer getTrainer(int id) {
        return trainerService.selectTrainerProfile(id);
    }


    @Override
    public Training addTraining(int traineeId, int trainerId, String trainingName, int trainingTypeId, Date trainingDate, double trainingDuration) {
        return trainingService.createTrainingProfile(traineeId, trainerId, trainingName, trainingTypeId, trainingDate, trainingDuration);

    }

    @Override
    public Training getTraining(int id) {
        return trainingService.selectTrainingProfile(id);
    }

}