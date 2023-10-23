package org.example.configuration;

import org.example.dao.Dao;
import org.example.model.*;

import java.util.HashMap;
import java.util.Map;

public class GymStorageImpl implements Storage {
    private Map<String, Dao> daos = new HashMap<>();
    @Override
    public Dao<User> getUserDao() {
        return getDao("users");
    }

    @Override
    public Dao<Trainee> getTraineeDao() {
        return getDao("trainees");
    }

    @Override
    public Dao<Trainer> getTrainerDao() {
        return getDao("trainers");
    }

    @Override
    public Dao<Training> getTrainingDao() {
        return getDao("trainings");
    }

    @Override
    public Dao<TrainingType> getTrainingTypeDao() {
        return getDao("trainingTypes");
    }
    @Override
    public void setDaos(Map<String, Dao> tables) {
        this.daos = tables;
    }

    @Override
    public Dao getDao(String tableName) {
        return daos.get(tableName);

    }
}
