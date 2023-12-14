package org.example.dao;

import org.example.configuration.inmemory.storage.GymStorageImpl;
import org.example.dao.inmemory.TraineeDao;
import org.example.model.Trainee;
import org.example.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class DaoImplTraineeTest {
    @InjectMocks
    TraineeDao traineeDao;
    @Mock
    Map<Integer, Trainee> storageEntities;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void givenEmptyStorageEntities_NextIdShouldBeOne() {
        // arrange
        storageEntities = new HashMap<>();

        GymStorageImpl storage = new GymStorageImpl();
        storage.setTrainees(storageEntities);
        traineeDao.setStorage(storage);

        // act
        int actualResponse = traineeDao.getNextId();

        // assert
        assertThat(actualResponse).isEqualTo(1);

    }

    @Test
    void givenNonEmptyStorageEntities_NextIdShouldBeReturned() {
        // arrange
        storageEntities = createNewStorageEntities();

        GymStorageImpl storage = new GymStorageImpl();
        storage.setTrainees(storageEntities);
        traineeDao.setStorage(storage);

        // act
        int actualResponse = traineeDao.getNextId();

        // assert
        assertThat(actualResponse).isEqualTo(3);

    }

    @Test
    void givenTraineeId_TraineeShouldBeReturned() {
        // arrange
        storageEntities = createNewStorageEntities();

        GymStorageImpl storage = new GymStorageImpl();
        storage.setTrainees(storageEntities);
        traineeDao.setStorage(storage);

        // act
        Optional<Trainee> actualResponse = traineeDao.get(1);

        // assert
        assertThat(actualResponse.orElse(null)).isEqualTo(storageEntities.get(1));

    }

    @Test
    void givenStorageEntitiesIsNotEmpty_ListOfTraineesShouldBeReturned() {
        // arrange
        storageEntities = createNewStorageEntities();

        GymStorageImpl storage = new GymStorageImpl();
        storage.setTrainees(storageEntities);
        traineeDao.setStorage(storage);

        // act
        List<Trainee> actualResponse = traineeDao.getAll();

        // assert
        assertThat(actualResponse).isEqualTo(new ArrayList<>(storageEntities.values()));

    }

    @Test
    void givenATrainee_TraineeShouldBeSaved() {
        // arrange
        Trainee newTrainee = new Trainee();

        newTrainee.setAddress("Test Address");
        newTrainee.setDateOfBirth(new Date());
        newTrainee.setUser(new User());

        storageEntities = createNewStorageEntities();

        GymStorageImpl storage = new GymStorageImpl();
        storage.setTrainees(storageEntities);
        traineeDao.setStorage(storage);

        // act
        Trainee actualResponse = traineeDao.save(newTrainee);

        // assert
        assertThat(actualResponse).isEqualTo(newTrainee);
        assertThat(actualResponse.getId()).isEqualTo(3);

    }


    @Test
    void givenNonExistingId_TraineeBeUpdated() {
        // arrange
        Trainee newTrainee = new Trainee();
        newTrainee.setAddress("Test Address");
        newTrainee.setDateOfBirth(new Date());
        newTrainee.setUser(new User());
        newTrainee.setId(1);

        storageEntities = createNewStorageEntities();

        GymStorageImpl storage = new GymStorageImpl();
        storage.setTrainees(storageEntities);
        traineeDao.setStorage(storage);

        // act
        Trainee actualResponse = traineeDao.update(1, newTrainee);

        // assert
        assertThat(actualResponse).isNotNull();
        assertThat(actualResponse.getAddress()).isEqualTo("Test Address");
        assertThat(actualResponse.getId()).isEqualTo(1);

    }

    @Test
    void givenExistingTraineeId_TraineeShouldBeDeleted() {
        // arrange
        storageEntities = createNewStorageEntities();

        GymStorageImpl storage = new GymStorageImpl();
        storage.setTrainees(storageEntities);
        traineeDao.setStorage(storage);

        // act
        Optional<Trainee> actualResponse = traineeDao.delete(1);

        // assert
        assertThat(actualResponse.orElse(null)).isNotNull();
        assertThat(storageEntities.get(1)).isNull();

    }


    Map<Integer, Trainee> createNewStorageEntities() {
        Trainee trainee = new Trainee();
        trainee.setId(1);
        Trainee trainee2 = new Trainee();
        trainee2.setId(2);

        Map<Integer, Trainee> trainees = new HashMap<>();
        trainees.put(1, trainee);
        trainees.put(2, trainee2);

        return trainees;
    }
}
