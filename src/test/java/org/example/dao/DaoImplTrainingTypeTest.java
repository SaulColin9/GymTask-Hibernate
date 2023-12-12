package org.example.dao;

import org.example.configuration.inmemory.storage.GymStorageImpl;
import org.example.dao.inmemory.TrainingTypeDao;
import org.example.model.TrainingType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class DaoImplTrainingTypeTest {
    @InjectMocks
    TrainingTypeDao daoTrainingType;
    @Mock
    Map<Integer, TrainingType> storageEntities;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void givenEmptyStorageEntities_NextIdShouldBeOne() {
        // arrange
        storageEntities = new HashMap<>();

        GymStorageImpl storage = new GymStorageImpl();
        storage.setTrainingTypes(storageEntities);
        daoTrainingType.setStorage(storage);
        // act
        int actualResponse = daoTrainingType.getNextId();
        // assert
        assertThat(actualResponse).isEqualTo(1);
    }

    @Test
    void givenNonEmptyStorageEntities_NextIdShouldBeReturned() {
        // arrange
        storageEntities = createNewStorageEntities();

        GymStorageImpl storage = new GymStorageImpl();
        storage.setTrainingTypes(storageEntities);
        daoTrainingType.setStorage(storage);
        // act
        int actualResponse = daoTrainingType.getNextId();

        // assert
        assertThat(actualResponse).isEqualTo(3);
    }

    @Test
    void givenTrainingTypeId_TrainingTypeShouldBeReturned() {
        // arrange
        storageEntities = createNewStorageEntities();

        GymStorageImpl storage = new GymStorageImpl();
        storage.setTrainingTypes(storageEntities);
        daoTrainingType.setStorage(storage);
        // act
        Optional<TrainingType> actualResponse = daoTrainingType.get(1);

        // assert
        assertThat(actualResponse.orElse(null)).isEqualTo(storageEntities.get(1));
    }

    @Test
    void givenStorageEntitiesIsNotEmpty_ListOfTrainingTypesShouldBeReturned() {
        // arrange
        storageEntities = createNewStorageEntities();
        GymStorageImpl storage = new GymStorageImpl();
        storage.setTrainingTypes(storageEntities);
        daoTrainingType.setStorage(storage);        // act
        List<TrainingType> actualResponse = daoTrainingType.getAll();
        // assert
        assertThat(actualResponse).isEqualTo(new ArrayList<>(storageEntities.values()));
    }

    @Test
    void givenATrainingType_TrainingTypeShouldBeSaved() {
        // arrange
        TrainingType newTrainingType = new TrainingType();

        newTrainingType.setTrainingTypeName("Elite");

        storageEntities = createNewStorageEntities();
        GymStorageImpl storage = new GymStorageImpl();
        storage.setTrainingTypes(storageEntities);
        daoTrainingType.setStorage(storage);        // act
        TrainingType actualResponse = daoTrainingType.save(newTrainingType);

        // assert
        assertThat(actualResponse).isEqualTo(newTrainingType);
        assertThat(actualResponse.getId()).isEqualTo(3);
    }

    @Test
    void givenNonExistingId_TrainingTypeShouldBeUpdated() {
        // arrange
        TrainingType newTrainingType = new TrainingType();
        newTrainingType.setTrainingTypeName("Elite");
        newTrainingType.setId(1);

        storageEntities = createNewStorageEntities();
        GymStorageImpl storage = new GymStorageImpl();
        storage.setTrainingTypes(storageEntities);
        daoTrainingType.setStorage(storage);
        // act
        TrainingType actualResponse = daoTrainingType.update(1, newTrainingType);
        // assert
        assertThat(actualResponse).isNotNull();
        assertThat(actualResponse.getTrainingTypeName()).isEqualTo("Elite");
        assertThat(actualResponse.getId()).isEqualTo(1);
    }


    @Test
    void givenExistingTrainingTypeId_TrainingTypeShouldBeDeleted() {
        // arrange
        storageEntities = createNewStorageEntities();
        GymStorageImpl storage = new GymStorageImpl();
        storage.setTrainingTypes(storageEntities);
        daoTrainingType.setStorage(storage);
        // act
        Optional<TrainingType> actualResponse = daoTrainingType.delete(1);
        // assert
        assertThat(actualResponse.orElse(null)).isNotNull();
        assertThat(storageEntities.get(1)).isNull();
    }

    Map<Integer, TrainingType> createNewStorageEntities() {
        TrainingType trainingType = new TrainingType("TestTrainingType");
        trainingType.setId(1);
        TrainingType trainingType2 = new TrainingType("TestTrainingType2");
        trainingType2.setId(2);

        Map<Integer, TrainingType> trainingTypes = new HashMap<>();
        trainingTypes.put(1, trainingType);
        trainingTypes.put(2, trainingType2);

        return trainingTypes;
    }


}
