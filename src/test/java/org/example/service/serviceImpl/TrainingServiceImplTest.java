package org.example.service.serviceImpl;


import org.example.configuration.Storage;
import org.example.dao.DaoConnectionImpl;
import org.example.model.Trainee;
import org.example.model.Trainer;
import org.example.model.Training;
import org.example.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
class TrainingServiceImplTest {
    @Autowired
    private TrainingServiceImpl trainingService;
    @Autowired
    private Storage storage;
    private DaoConnectionImpl<Training> daoConnection = new DaoConnectionImpl<>(Training.class);

    private List<Training> trainings = new ArrayList<>();
    private List<User> users = new ArrayList<>();
    private List<Trainee> trainees = new ArrayList<>();
    private List<Trainer> trainers = new ArrayList<>();

    @BeforeEach
    public void setUp(){
        DaoConnectionImpl<User> daoConnectionUsers;
        DaoConnectionImpl<Trainer> daoConnectionTrainers;
        DaoConnectionImpl<Trainee> daoConnectionTrainees;

        Training trainingTest = new Training(1,1,"Test Training", 1, new Date(),1);
        trainingTest.setId(1);
        Training trainingTest2 = new Training(2,2,"Test Training 2", 2, new Date(),1.5);
        trainingTest2.setId(2);
        trainings.add(trainingTest);
        trainings.add(trainingTest2);

        User userTest = new User("User Test", "User Test", ".");
        User userTest2 = new User("User Test 2", "User Test 2", ".");
        users.add(userTest);
        users.add(userTest2);

        Trainee traineeTest =  new Trainee(new Date(), "Test Address", 1, userTest);
        traineeTest = traineeTest.setId(1);
        trainees.add(traineeTest);


        Trainer trainerTest2 = new Trainer(2, 2, userTest2);
        trainerTest2 = trainerTest2.setId(1);
        trainers.add(trainerTest2);


        daoConnection = mock(DaoConnectionImpl.class);
        when(daoConnection.getEntities(anyString())).thenReturn(trainings);
        when(daoConnection.writeEntities(anyString(), anyList())).thenReturn(trainings);
        storage.getTrainingDao().setDaoConnection(daoConnection);
        storage.getTrainingDao().setFilePath("mockFilePath");

        daoConnectionUsers = mock(DaoConnectionImpl.class);
        when(daoConnectionUsers.getEntities(anyString())).thenReturn(users);
        when(daoConnectionUsers.writeEntities(anyString(), anyList())).thenReturn(users);
        storage.getUserDao().setDaoConnection(daoConnectionUsers);
        storage.getUserDao().setFilePath("mockFilePath");

        daoConnectionTrainees = mock(DaoConnectionImpl.class);
        when(daoConnectionTrainees.getEntities(anyString())).thenReturn(trainees);
        when(daoConnectionTrainees.writeEntities(anyString(), anyList())).thenReturn(trainees);
        storage.getTraineeDao().setDaoConnection(daoConnectionTrainees);
        storage.getTraineeDao().setFilePath("mockFilePath");

        daoConnectionTrainers = mock(DaoConnectionImpl.class);
        when(daoConnectionTrainers.getEntities(anyString())).thenReturn(trainers);
        when(daoConnectionTrainers.writeEntities(anyString(), anyList())).thenReturn(trainers);
        storage.getTrainerDao().setDaoConnection(daoConnectionTrainers);
        storage.getTrainerDao().setFilePath("mockFilePath");
    }
    @Test
    void createTrainingProfileTest() {
        Training trainingsCreated = trainingService.createTrainingProfile(1, 1,"Test Training", 1, new Date(), 1);

        assertNotNull(trainingsCreated);
        verify(daoConnection, times(1)).writeEntities(anyString(), anyList());
    }

    @Test
    void selectTrainingProfileTest() {
        Training trainingTest = new Training(1,1,"Test Training", 1, new Date(),1);
        trainingTest.setId(1);

        Training trainingSelected = trainingService.selectTrainingProfile(1);

        assertNotNull(trainingSelected);
        assertEquals(trainingTest.getId(), trainingSelected.getId());
        assertEquals(trainingTest.getTrainingName(), trainingSelected.getTrainingName());
        verify(daoConnection, times(1)).getEntities(anyString());
    }

    @Test
    void selectAllTest() {
        List<Training> selectedTrainings = trainingService.selectAll();
        assertNotNull(trainings);
        assertEquals(selectedTrainings, trainings);
    }
}