package org.example.configuration;

import org.example.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


class GymStorageImplTest {

    @InjectMocks
    GymStorageImpl gymStorage;
    @Mock
    private Map<Integer, User> users;
    @Mock
    private Map<Integer, Trainee> trainees;
    @Mock
    private Map<Integer, Trainer> trainers;
    @Mock
    private Map<Integer, Training> trainings;
    @Mock
    private Map<Integer, TrainingType> trainingTypes;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void setFilePath() {
        gymStorage.setFilePath("path");
        assertNotNull(gymStorage.getFilePath());
        ;
    }

    @Test
    void getUsers() {
        gymStorage.setUsers(new HashMap<>());
        assertNotNull(gymStorage.getUsers());
    }

    @Test
    void getTrainees() {
        assertNotNull(gymStorage.getTrainees());
    }

    @Test
    void getTrainers() {
        assertNotNull(gymStorage.getTrainers());
    }

    @Test
    void getTrainings() {
        assertNotNull(gymStorage.getTrainings());
    }

    @Test
    void getTrainingTypes() {
        assertNotNull(gymStorage.getTrainingTypes());
    }

    @Test
    void afterPropertiesSet_ShouldThrowException() {
        gymStorage.setFilePath(null);
        assertThrows(NullPointerException.class, () -> gymStorage.afterPropertiesSet());
    }

    @Test
    void afterPropertiesSet() {
        try {
            gymStorage.setFilePath("data\\entities.json");
            gymStorage.afterPropertiesSet();
            assertNotNull(gymStorage.getUsers());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}