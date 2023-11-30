package org.example.dao;

import org.example.configuration.inmemory.storage.GymStorageImpl;
import org.example.dao.inmemory.TrainingDao;
import org.example.entitiesFactory.EntitiesFactory;
import org.example.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class DaoImplTrainingTest {
    EntitiesFactory entitiesFactory;
    @InjectMocks
    TrainingDao trainingDao;
    @Mock
    Map<Integer, Training> storageEntities;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        entitiesFactory = new EntitiesFactory();
    }

    @Test
    void givenEmptyStorageEntities_NextIdShouldBeOne() {
        // arrange
        storageEntities = new HashMap<>();

        GymStorageImpl storage = new GymStorageImpl();
        storage.setTrainings(storageEntities);
        trainingDao.setStorage(storage);
        // act
        int actualResponse = trainingDao.getNextId();
        // assert
        assertThat(actualResponse).isEqualTo(1);
    }

    @Test
    void givenNonEmptyStorageEntities_NextIdShouldBeReturned() {
        // arrange
        storageEntities = createNewStorageEntities();

        GymStorageImpl storage = new GymStorageImpl();
        storage.setTrainings(storageEntities);
        trainingDao.setStorage(storage);
        // act
        int actualResponse = trainingDao.getNextId();

        // assert
        assertThat(actualResponse).isEqualTo(3);
    }

    @Test
    void givenTrainingId_TrainingShouldBeReturned() {
        // arrange
        storageEntities = createNewStorageEntities();

        GymStorageImpl storage = new GymStorageImpl();
        storage.setTrainings(storageEntities);
        trainingDao.setStorage(storage);
        // act
        Optional<Training> actualResponse = trainingDao.get(1);

        // assert
        assertThat(actualResponse.get()).isEqualTo(storageEntities.get(1));
    }

    @Test
    void givenExistingTrainingId_TrainingShouldBeDeleted() {
        // arrange
        storageEntities = createNewStorageEntities();
        GymStorageImpl storage = new GymStorageImpl();
        storage.setTrainings(storageEntities);
        trainingDao.setStorage(storage);

        // act
        Optional<Training> actualResponse = trainingDao.delete(1);
        // assert
        assertThat(actualResponse.get()).isNotNull();
        assertThat(storageEntities.get(1)).isNull();
    }

    @Test
    void givenATraining_TrainingShouldBeSaved() {
        // arrange
        Training newTraining = new Training();

        newTraining.setTrainingDuration(1.0);
        newTraining.setTrainingName("Elite");
        newTraining.setTrainingDate(new Date());
        newTraining.setTrainingType(new TrainingType());
        newTraining.setTrainer(new Trainer());
        newTraining.setTrainee(new Trainee());

        storageEntities = createNewStorageEntities();
        GymStorageImpl storage = new GymStorageImpl();
        storage.setTrainings(storageEntities);
        trainingDao.setStorage(storage);
        // act
        Training actualResponse = trainingDao.save(newTraining);

        // assert
        assertThat(actualResponse).isEqualTo(newTraining);
        assertThat(actualResponse.getId()).isEqualTo(3);
    }

    @Test
    void givenNonExistingId_TrainingShouldBeUpdated() {
        // arrange
        Training newTraining = new Training();

        newTraining.setTrainingDuration(1.0);
        newTraining.setTrainingName("New Training Name");
        newTraining.setTrainingDate(new Date());
        newTraining.setTrainingType(new TrainingType());
        newTraining.setTrainer(new Trainer());
        newTraining.setTrainee(new Trainee());
        newTraining.setId(1);

        storageEntities = createNewStorageEntities();
        GymStorageImpl storage = new GymStorageImpl();
        storage.setTrainings(storageEntities);
        trainingDao.setStorage(storage);
        // act
        Training actualResponse = trainingDao.update(1, newTraining);
        // assert
        assertThat(actualResponse).isNotNull();
        assertThat(actualResponse.getTrainingName()).isEqualTo("New Training Name");
        assertThat(actualResponse.getId()).isEqualTo(1);
    }


    @Test
    void givenStorageEntitiesIsNotEmpty_ListOfTrainingShouldBeReturned() {
        // arrange
        storageEntities = createNewStorageEntities();
        GymStorageImpl storage = new GymStorageImpl();
        storage.setTrainings(storageEntities);
        trainingDao.setStorage(storage);
        // act
        List<Training> actualResponse = trainingDao.getAll();
        // assert
        assertThat(actualResponse).isEqualTo(new ArrayList<>(storageEntities.values()));
    }

    Map<Integer, Training> createNewStorageEntities() {
        User user = new User("User Test", "User Test");
        user.setId(1);
        User user2 = new User("User Test2", "User Test2");
        user2.setId(2);

        Trainer trainer = new Trainer(entitiesFactory.createNewTrainingType(), user);
        trainer.setUser(user);
        trainer.setId(1);

        Trainee trainee = new Trainee(new Date(), "Test Address 2", user2);
        trainee.setUser(user2);
        trainee.setId(1);

        TrainingType trainingType = new TrainingType("FitnessPlan");
        trainingType.setId(1);

        Training training = new Training(trainee, trainer, "Test Name", trainingType, new Date(), 1);
        training.setId(1);
        Training training2 = new Training(trainee, trainer, "Test Name", trainingType, new Date(), 1);
        training2.setId(2);

        Map<Integer, Training> trainings = new HashMap<>();
        trainings.put(1, training);
        trainings.put(2, training2);
        return trainings;
    }


}
