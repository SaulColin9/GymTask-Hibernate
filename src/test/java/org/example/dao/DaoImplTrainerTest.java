package org.example.dao;

import org.example.configuration.GymStorageImpl;
import org.example.model.Trainer;
import org.example.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class DaoImplTrainerTest {
    @InjectMocks
    DaoImpl<Trainer> trainerDao;
    @Mock
    Map<Integer, Trainer> storageEntities;
    GymStorageImpl storage;
    Trainer trainer;
    Trainer trainer2;

    @BeforeEach
    void setUp() {
        User user = new User("User Test", "User Test", ".");
        user.setId(1);
        User user2 = new User("User Test2", "User Test2", ".");
        user2.setId(2);
        Map<Integer, User> users = new HashMap<>();
        users.put(1, user);
        users.put(2, user2);

        trainer = new Trainer(1,1, user);
        trainer.setId(1);
        trainer2 = new Trainer(2,2, user2);
        trainer2.setId(2);
        Map<Integer, Trainer> trainers = new HashMap<>();
        trainers.put(1, trainer);
        trainers.put(2, trainer2);
        storage = new GymStorageImpl();
        storage.setTrainers(trainers);
        storage.setUsers(users);
        trainerDao = new DaoImpl<>(Trainer.class);
        trainerDao.setStorage(storage);

    }

    @Test
    void getNextId() {
        assertEquals(trainerDao.getNextId(), storage.getTrainers().values().size());
    }

    @Test
    void get() {
        assertEquals(trainer, trainerDao.get(1).orElse(null));
    }

    @Test
    void getAll() {
        assertEquals(trainerDao.getAll().size(), storage.getUsers().values().size());
    }

    @Test
    void save() {
        Trainer newTrainer = new Trainer();
        newTrainer.setId(2);
        assertEquals(newTrainer.getId(), trainerDao.save(new Trainer()).getId());
    }

    @Test
    void update() {
        Trainer oldTrainer = trainer;
        Trainer updatedTrainer = trainerDao.update(1, trainer2);
        assertNotEquals(oldTrainer.getSpecialization(), updatedTrainer.getSpecialization());
        assertEquals(oldTrainer.getId(), updatedTrainer.getId());

    }

    @Test
    void delete() {
        Optional<Trainer> deletedTrainer = trainerDao.delete(1);
        assertNotNull(deletedTrainer);
    }
}
