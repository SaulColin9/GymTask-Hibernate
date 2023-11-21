package org.example.dao;

import org.example.configuration.GymStorageImpl;
import org.example.dao.entities.TrainerDao;
import org.example.model.Trainer;
import org.example.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class DaoImplTrainerTest {
    @InjectMocks
    TrainerDao trainerDao;
    @Mock
    Map<Integer, Trainer> storageEntities;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void givenEmptyStorageEntities_NextIdShouldBeOne() {
        // arrange
        storageEntities = new HashMap<>();

        GymStorageImpl storage = new GymStorageImpl();
        storage.setTrainers(storageEntities);
        trainerDao.setStorage(storage);
        // act
        int actualResponse = trainerDao.getNextId();
        // assert
        assertThat(actualResponse).isEqualTo(1);
    }

    @Test
    void givenNonEmptyStorageEntities_NextIdShouldBeReturned() {
        // arrange
        storageEntities = createNewStorageEntities();

        GymStorageImpl storage = new GymStorageImpl();
        storage.setTrainers(storageEntities);
        trainerDao.setStorage(storage);
        // act
        int actualResponse = trainerDao.getNextId();

        // assert
        assertThat(actualResponse).isEqualTo(3);
    }

    @Test
    void givenTrainerId_TrainerShouldBeReturned() {
        // arrange
        storageEntities = createNewStorageEntities();

        GymStorageImpl storage = new GymStorageImpl();
        storage.setTrainers(storageEntities);
        trainerDao.setStorage(storage);
        // act
        Optional<Trainer> actualResponse = trainerDao.get(1);

        // assert
        assertThat(actualResponse.get()).isEqualTo(storageEntities.get(1));
    }

    @Test
    void givenStorageEntitiesIsNotEmpty_ListOfTrainersShouldBeReturned() {
        // arrange
        storageEntities = createNewStorageEntities();

        GymStorageImpl storage = new GymStorageImpl();
        storage.setTrainers(storageEntities);
        trainerDao.setStorage(storage);
        // act
        List<Trainer> actualResponse = trainerDao.getAll();
        // assert
        assertThat(actualResponse).isEqualTo(new ArrayList<>(storageEntities.values()));
    }

    @Test
    void givenATrainer_TrainerShouldBeSaved() {
        // arrange
        Trainer newTrainer = new Trainer();

        newTrainer.setSpecialization(1);
        newTrainer.setUser(new User());

        storageEntities = createNewStorageEntities();

        GymStorageImpl storage = new GymStorageImpl();
        storage.setTrainers(storageEntities);
        trainerDao.setStorage(storage);
        // act
        Trainer actualResponse = trainerDao.save(newTrainer);

        // assert
        assertThat(actualResponse).isEqualTo(newTrainer);
        assertThat(actualResponse.getId()).isEqualTo(3);
    }

    @Test
    void givenNonExistingId_TrainerShouldBeUpdated() {
        // arrange
        Trainer newTrainer = new Trainer();

        newTrainer.setSpecialization(2);
        newTrainer.setUser(new User());
        newTrainer.setId(1);

        storageEntities = createNewStorageEntities();

        GymStorageImpl storage = new GymStorageImpl();
        storage.setTrainers(storageEntities);
        trainerDao.setStorage(storage);
        // act
        Trainer actualResponse = trainerDao.update(1, newTrainer);
        // assert
        assertThat(actualResponse).isNotNull();
        assertThat(actualResponse.getSpecialization()).isEqualTo(2);
        assertThat(actualResponse.getId()).isEqualTo(1);
    }


    @Test
    void givenExistingTrainerId_TrainerShouldBeDeleted() {
        // arrange
        storageEntities = createNewStorageEntities();

        GymStorageImpl storage = new GymStorageImpl();
        storage.setTrainers(storageEntities);
        trainerDao.setStorage(storage);

        // act
        Optional<Trainer> actualResponse = trainerDao.delete(1);
        // assert
        assertThat(actualResponse.get()).isNotNull();
        assertThat(storageEntities.get(1)).isNull();
    }

    Map<Integer, Trainer> createNewStorageEntities() {
        User user = new User("User Test", "User Test");
        user.setId(1);
        User user2 = new User("User Test2", "User Test2");
        user2.setId(2);

        Trainer trainer = new Trainer(1, user);
        trainer.setId(1);
        Trainer trainer2 = new Trainer(2, user2);
        trainer2.setId(2);
        Map<Integer, Trainer> trainers = new HashMap<>();
        trainers.put(1, trainer);
        trainers.put(2, trainer2);

        return trainers;
    }
}
