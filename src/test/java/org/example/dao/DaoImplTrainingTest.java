package org.example.dao;

import org.example.configuration.GymStorageImpl;
import org.example.dao.entities.TrainerDao;
import org.example.dao.entities.TrainingDao;
import org.example.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class DaoImplTrainingTest {
    @InjectMocks
    DaoImpl<Training> trainingDao;
    @Mock
    Map<Integer, Trainer> storageEntities;
    GymStorageImpl storage;
    Trainer trainer;
    Trainer trainer2;
    Training training;
    Training training2;
    Trainee trainee;
    TrainingType trainingType;

    @BeforeEach
    void setUp() {
        User user = new User("User Test", "User Test");
        user.setId(1);
        User user2 = new User("User Test2", "User Test2");
        user2.setId(2);
        Map<Integer, User> users = new HashMap<>();
        users.put(1, user);
        users.put(2, user2);

        trainer = new Trainer(1, user);
        trainer.setId(1);

        trainee = new Trainee(new Date(), "Test Address 2", user2);
        trainee.setId(1);

        trainingType = new TrainingType("FitnessPlan");
        trainingType.setId(1);

        Map<Integer, Trainer> trainers = new HashMap<>();
        trainers.put(1, trainer);
        trainers.put(2, trainer2);
        storage = new GymStorageImpl();
        storage.setTrainers(trainers);
        storage.setUsers(users);

        training = new Training(trainee, trainer, "Test Name", trainingType, new Date(), 1);
        training.setId(1);
        training2 = new Training(trainee, trainer, "Test Name", trainingType, new Date(), 1);
        training2.setId(2);

        Map<Integer, Training> trainings = new HashMap<>();
        trainings.put(1, training);
        storage = new GymStorageImpl();
        storage.setTrainings(trainings);
        storage.setUsers(users);
        trainingDao = new TrainingDao();
        trainingDao.setStorage(storage);

    }

    @Test
    void getNextId() {
        assertEquals(storage.getTrainings().values().size() + 1, trainingDao.getNextId());
    }

    @Test
    void get() {
        assertEquals(training, trainingDao.get(1).orElse(null));
    }

    @Test
    void getAll() {
        assertEquals(trainingDao.getAll().size(), storage.getTrainings().values().size());
    }

    @Test
    void save() {
        Training newTraining = new Training();
        newTraining.setId(trainingDao.getNextId());
        assertEquals(newTraining.getId(), trainingDao.save(new Training()).getId());
    }

    @Test
    void update() {
        Training oldTraining = training;
        training2.setTrainingName("Updated");
        Training updatedTraining = trainingDao.update(1, training2);
        assertNotEquals(oldTraining.getTrainingName(), updatedTraining.getTrainingName());
        assertEquals(oldTraining.getId(), updatedTraining.getId());

    }

    @Test
    void delete() {
        Optional<Training> deletedTraining = trainingDao.delete(1);
        assertNotNull(deletedTraining);
    }


}
