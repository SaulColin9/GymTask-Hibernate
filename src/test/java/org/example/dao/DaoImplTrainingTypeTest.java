package org.example.dao;

import org.example.configuration.GymStorageImpl;
import org.example.model.TrainingType;
import org.example.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class DaoImplTrainingTypeTest {
    @InjectMocks
    DaoImpl<TrainingType> daoTrainingType;
    @Mock
    Map<Integer, TrainingType> storageEntities;
    GymStorageImpl storage;
    TrainingType trainingType;
    TrainingType trainingType2;

    @BeforeEach
    void setUp() {
        trainingType = new TrainingType("TestTrainingType");
        trainingType.setId(1);
        trainingType2 = new TrainingType("TestTraininigType2");
        trainingType2.setId(2);

        Map<Integer, TrainingType> trainingTypes = new HashMap<>();
        trainingTypes.put(1, trainingType);
        trainingTypes.put(2, trainingType2);
        storage = new GymStorageImpl();
        storage.setTrainingTypes(trainingTypes);
        daoTrainingType = new DaoImpl<>(TrainingType.class);
        daoTrainingType.setStorage(storage);

    }


    @Test
    void getNextId() {
        assertEquals(daoTrainingType.getNextId(), storage.getTrainingTypes().values().size());
    }

    @Test
    void get() {
        assertEquals(daoTrainingType.get(1).orElse(null), trainingType);
    }

    @Test
    void getAll() {
        assertEquals(daoTrainingType.getAll().size(), storage.getTrainingTypes().values().size());
    }

    @Test
    void save() {
        TrainingType newTrainigType = new TrainingType();
        newTrainigType.setId(2);
        assertEquals(newTrainigType.getId(), daoTrainingType.save(new TrainingType()).getId());
    }

    @Test
    void update() {
        TrainingType oldTrainingType = trainingType;
        TrainingType updatedTraningType = daoTrainingType.update(1, trainingType2);
        assertNotEquals(oldTrainingType.getTrainingTypeName(), updatedTraningType.getTrainingTypeName());
        assertEquals(oldTrainingType.getId(), updatedTraningType.getId());

    }

    @Test
    void delete() {
        Optional<TrainingType> deletedTrainingType = daoTrainingType.delete(1);
        assertNotNull(deletedTrainingType);
    }
}
