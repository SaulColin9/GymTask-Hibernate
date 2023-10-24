package org.example.configuration;

import org.example.model.*;

import java.util.List;

public class EntitiesReader {
    private List<User> users;
    private List<Trainer> trainers;
    private List<Trainee> trainees;
    private List<Training> trainings;
    private List<TrainingType> trainingTypes;

    public List<User> getUsers() {
        return users;
    }

    public List<Trainer> getTrainers() {
        return trainers;
    }

    public List<Trainee> getTrainees() {
        return trainees;
    }

    public List<Training> getTrainings() {
        return trainings;
    }

    public List<TrainingType> getTrainingTypes() {
        return trainingTypes;
    }
}
