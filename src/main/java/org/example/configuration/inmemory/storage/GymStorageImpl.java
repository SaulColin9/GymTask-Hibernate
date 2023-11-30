package org.example.configuration.inmemory.storage;

import org.example.model.*;
import org.springframework.beans.factory.InitializingBean;

import java.util.HashMap;
import java.util.Map;

public class GymStorageImpl implements Storage, InitializingBean {
    private String filePath;
    private Map<Integer, User> users;
    private Map<Integer, Trainee> trainees;
    private Map<Integer, Trainer> trainers;
    private Map<Integer, Training> trainings;
    private Map<Integer, TrainingType> trainingTypes;

    @Override
    public void afterPropertiesSet() throws Exception {
        users = new HashMap<>();
        trainees = new HashMap<>();
        trainers = new HashMap<>();
        trainings = new HashMap<>();
        trainingTypes = new HashMap<>();

        StorageConnection<EntitiesReader> storageConnection = new StorageConnectionImpl<>(EntitiesReader.class);
        EntitiesReader entitiesReader = storageConnection.getEntities(filePath);
        entitiesReader.getUsers().forEach(user -> getUsers().put(user.getId(), user));
        entitiesReader.getTrainees().forEach(trainee -> getTrainees().put(trainee.getId(), trainee));
        entitiesReader.getTrainers().forEach(trainer -> getTrainers().put(trainer.getId(), trainer));
        entitiesReader.getTrainings().forEach(training -> getTrainings().put(training.getId(), training));
        entitiesReader.getTrainingTypes().forEach(trainingType -> getTrainingTypes().put(trainingType.getId(), trainingType));

    }

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

    public void setTrainings(Map<Integer, Training> trainings) {
        this.trainings = trainings;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
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


}
