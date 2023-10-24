package org.example.dao;

import org.example.configuration.GymStorageImpl;
import org.example.model.Trainee;
import org.example.model.Trainer;
import org.example.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class DaoImplTraineeTest {
    @InjectMocks
    DaoImpl<Trainee> traineeDao;
    @Mock
    Map<Integer, Trainee> storageEntities;
    GymStorageImpl storage;
    Trainee trainee;
    Trainee trainee2;

    @BeforeEach
    void setUp() {
        User user = new User("User Test", "User Test", ".");
        user.setId(1);
        User user2 = new User("User Test2", "User Test2", ".");
        user2.setId(2);
        Map<Integer, User> users = new HashMap<>();
        users.put(1, user);
        users.put(2, user2);

        trainee = new Trainee(new Date(), "Test Address", 1, user);
        trainee.setId(1);
        trainee2 = new Trainee(new Date(), "Test Address 2", 1, user2);
        trainee2.setId(2);
        Map<Integer, Trainee> trainees = new HashMap<>();
        trainees.put(1, trainee);
        trainees.put(2, trainee2);
        storage = new GymStorageImpl();
        storage.setTrainees(trainees);
        storage.setUsers(users);
        traineeDao = new DaoImpl<>(Trainee.class);
        traineeDao.setStorage(storage);

    }

    @Test
    void getNextId() {
        assertEquals(traineeDao.getNextId(), storage.getTrainees().values().size());
    }

    @Test
    void get() {
        assertEquals(trainee, traineeDao.get(1).orElse(null));
    }

    @Test
    void getAll() {
        assertEquals(traineeDao.getAll().size(), storage.getTrainees().values().size());
    }

    @Test
    void save() {
        Trainee newTrainee = new Trainee();
        newTrainee.setId(2);
        assertEquals(newTrainee.getId(), traineeDao.save(new Trainee()).getId());
    }

    @Test
    void update() {
        Trainee oldTrainee = trainee;
        Trainee updatedTrainee = traineeDao.update(1, trainee2);
        assertNotEquals(oldTrainee.getAddress(), updatedTrainee.getAddress());
        assertEquals(oldTrainee.getId(), updatedTrainee.getId());

    }

    @Test
    void delete() {
        Optional<Trainee> deletedTrainee = traineeDao.delete(1);
        assertNotNull(deletedTrainee);
    }
}
