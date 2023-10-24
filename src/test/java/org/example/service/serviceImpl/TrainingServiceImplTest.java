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
    private User user;
    private User user2;
    private Trainee traineeTest;
    private Trainer trainerTest;
    private TrainingType trainingTypeTest;
    private Training trainingTest;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User("User Test", "User Test", ".");
        user.setId(1);
        user2 = new User("User Test2", "User Test2", ".");
        user2.setId(2);

        traineeTest = new Trainee(new Date(), "Test Address", 1, user);
        traineeTest = traineeTest.setId(1);


        trainerTest = new Trainer(2, 2, user2);
        trainerTest = trainerTest.setId(1);

        trainingTest = new Training(1, 1, "Test Training", 1, new Date(), 1);
        trainingTest.setId(1);
        trainingTest.setTrainer(trainerTest);
        trainingTest.setTrainee(traineeTest);

        trainingTypeTest = new TrainingType("TestTrainingType");
        trainingTypeTest.setId(1);

        when(userDao.get(1)).thenReturn(Optional.ofNullable(user));
        when(traineeDao.get(anyInt())).thenReturn(Optional.of(traineeTest));
        when(trainerDao.get(anyInt())).thenReturn(Optional.ofNullable(trainerTest));
        when(trainingDao.get(anyInt())).thenReturn(Optional.ofNullable(trainingTest));
        when(trainingTypeDao.get(anyInt())).thenReturn(Optional.ofNullable(trainingTypeTest));

    }

    @Test
    void createTrainingProfileTest() {
        when(trainingDao.save(any(Training.class))).thenReturn(trainingTest);
        Training trainingCreated = trainingService.createTrainingProfile(1, 1, "Test Training", 1, new Date(), 1);

        assertNotNull(trainingCreated);
    }

    @Test
    void selectTrainingProfileTest() {
        Training trainingTest = new Training(1, 1, "Test Training", 1, new Date(), 1);
        trainingTest.setId(1);

        Training trainingSelected = trainingService.selectTrainingProfile(1);

        assertNotNull(trainingSelected);
        assertEquals(trainingTest.getId(), trainingSelected.getId());
        assertEquals(trainingTest.getTrainingName(), trainingSelected.getTrainingName());
    }


}