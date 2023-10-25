package org.example.service.serviceImpl;


import org.example.dao.Dao;
import org.example.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class TrainingServiceImplTest {
    @Mock
    private Dao<Trainee> traineeDao;
    @Mock
    private Dao<Trainer> trainerDao;
    @Mock
    private Dao<User> userDao;
    @Mock
    private Dao<TrainingType> trainingTypeDao;
    @Mock
    private Dao<Training> trainingDao;
    @InjectMocks
    private TrainingServiceImpl trainingService;
    private Training trainingTest;

    private static final String TRAINING_NAME = "Test Training";

    @BeforeEach
    public void setUp() {
        User user;
        User user2;
        Trainee traineeTest;
        Trainer trainerTest;
        TrainingType trainingTypeTest;
        MockitoAnnotations.openMocks(this);

        user = new User("User Test", "User Test", ".");
        user.setId(1);
        user2 = new User("User Test2", "User Test2", ".");
        user2.setId(2);

        traineeTest = new Trainee(new Date(), "Test Address",  user);
        traineeTest = traineeTest.setId(1);


        trainerTest = new Trainer(2,  user2);
        trainerTest = trainerTest.setId(1);

        trainingTypeTest = new TrainingType("TestTrainingType");
        trainingTypeTest.setId(1);

        trainingTest = new Training();
        trainingTest.setTrainingName(TRAINING_NAME);
        trainingTest.setId(1);
        trainingTest.setTrainingType(trainingTypeTest);
        trainingTest.setTrainingDate(new Date());
        trainingTest.setTrainer(trainerTest);
        trainingTest.setTrainee(traineeTest);
        trainingTest.setTrainingDuration(1);


        when(userDao.get(1)).thenReturn(Optional.of(user));
        when(traineeDao.get(anyInt())).thenReturn(Optional.of(traineeTest));
        when(trainerDao.get(anyInt())).thenReturn(Optional.of(trainerTest));
        when(trainingDao.get(anyInt())).thenReturn(Optional.of(trainingTest));
        when(trainingTypeDao.get(anyInt())).thenReturn(Optional.of(trainingTypeTest));

    }

    @Test
    void createTrainingProfileTest() {
        when(trainingDao.save(any(Training.class))).thenReturn(trainingTest);
        int trainingCreated = trainingService
                .createTrainingProfile(1, 1, TRAINING_NAME, 1, new Date(), 1);

        assertTrue(trainingCreated>0);
    }

    @Test
    void selectTrainingProfileTest() {
        Training training = trainingTest;
        training.setId(1);

        Training trainingSelected = trainingService.selectTrainingProfile(1);

        assertNotNull(trainingSelected);
        assertEquals(training.getId(), trainingSelected.getId());
        assertEquals(training.getTrainingName(), trainingSelected.getTrainingName());
    }


}