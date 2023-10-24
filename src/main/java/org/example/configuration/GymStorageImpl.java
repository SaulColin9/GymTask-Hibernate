package org.example.configuration;

import org.example.model.*;

import java.util.HashMap;
import java.util.Map;

public class GymStorageImpl implements Storage {
    public String getFilePath() {
        return filePath;
    }

    private String filePath;

    @Override
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    private Map<Integer, User> users;
    private Map<Integer, Trainee> trainees;
    private Map<Integer, Trainer> trainers;
    private Map<Integer, Training> trainings;
    private Map<Integer, TrainingType> trainingTypes;

    public void setUsers(Map<Integer, User> users) {
        this.users = users;
    }

    public void setTrainers(Map<Integer, Trainer> trainers) {
        this.trainers = trainers;
    }

    public void setTrainingTypes(Map<Integer, TrainingType> trainingTypes) {
        this.trainingTypes = trainingTypes;
    }

    public void setTrainees(Map<Integer, Trainee> trainees) {
        this.trainees = trainees;
    }

    @Override
    public Map<Integer, User> getUsers() {
        return users;
    }

    @Override
    public Map<Integer, Trainee> getTrainees() {
        return trainees;
    }

    @Override
    public Map<Integer, Trainer> getTrainers() {

        return trainers;
    }

    @Override
    public Map<Integer, Training> getTrainings() {
        return trainings;
    }

    @Override
    public Map<Integer, TrainingType> getTrainingTypes() {
        return trainingTypes;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        users = new HashMap<>();
        trainees = new HashMap<>();
        trainers = new HashMap<>();
        trainings = new HashMap<>();
        trainingTypes = new HashMap<>();

        StorageConnection<EntitiesReader> storageConnection = new StorageConnectionImpl<>(EntitiesReader.class);
        EntitiesReader entitiesReader = storageConnection.getEntities(filePath);
        entitiesReader.getUsers().forEach(user -> users.put(user.getId(), user));
        entitiesReader.getTrainees().forEach(trainee -> trainees.put(trainee.getId(), trainee));
        entitiesReader.getTrainers().forEach(trainer -> trainers.put(trainer.getId(), trainer));
        entitiesReader.getTrainings().forEach(training -> trainings.put(training.getId(), training));
        entitiesReader.getTrainingTypes().forEach(trainingType -> trainingTypes.put(trainingType.getId(), trainingType));

    }


}
