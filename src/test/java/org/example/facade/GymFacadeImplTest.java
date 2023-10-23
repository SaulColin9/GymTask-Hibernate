package org.example.facade;

import org.example.configuration.Storage;
import org.example.dao.DaoConnectionImpl;
import org.example.model.Trainee;
import org.example.model.Trainer;
import org.example.model.Training;
import org.example.model.User;
import org.example.service.serviceImpl.TestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
class GymFacadeImplTest {

    @Autowired
    private GymFacade gymFacade;
    @Autowired
    private Storage storage;
    @BeforeEach
    public void setUp(){
        DaoConnectionImpl<Training> daoConnection;
        DaoConnectionImpl<User> daoConnectionUsers;
        DaoConnectionImpl<Trainer> daoConnectionTrainers;
        DaoConnectionImpl<Trainee> daoConnectionTrainees;
        List<Training> trainings = new ArrayList<>();
        List<User> users = new ArrayList<>();
        List<Trainee> trainees = new ArrayList<>();
        List<Trainer> trainers = new ArrayList<>();

        User userTest = new User("User Test", "User Test", ".");
        userTest.setId(1);
        User userTest2 = new User("User Test 2", "User Test 2", ".");
        userTest2.setId(2);
        users.add(userTest);
        users.add(userTest2);

        Trainee traineeTest =  new Trainee(new Date(), "Test Address", 1, userTest);
        traineeTest = traineeTest.setId(1);
        traineeTest.setUser(userTest);
        trainees.add(traineeTest);


        Trainer trainerTest2 = new Trainer(2, 2, userTest2);
        trainerTest2 = trainerTest2.setId(1);
        trainerTest2.setUser(userTest2);
        trainers.add(trainerTest2);

        Training trainingTest = new Training(1,1,"Test Training", 1, new Date(),1);
        trainingTest.setId(1);
        trainingTest.setTrainee(traineeTest);
        trainingTest.setTrainer(trainerTest2);
        trainings.add(trainingTest);

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
    void addTrainee() {
        Trainee newTrainee = gymFacade.addTrainee("New Test Trainee","Test Last", new Date(), "Test Address");
        assertNotNull(newTrainee);
    }

    @Test
    void updateTrainee() {
        Trainee oldTrainee = gymFacade.getTrainee(1);
        String oldTraineeName = oldTrainee.getUser().getUsername();
        Trainee updatedTrainee = gymFacade.updateTrainee(1, "Updated Name", "Updated Last",
                false, new Date(), "Test Address");

        assertNotEquals(oldTraineeName,updatedTrainee.getUser().getUsername());
    }

    @Test
    void deleteTrainee() {
        assertNotNull(gymFacade.getTrainee(1));
        gymFacade.deleteTrainee(1);
        assertNull(gymFacade.getTrainee(1));
    }

    @Test
    void getTrainee() {
        Trainee trainee = gymFacade.getTrainee(1);
        assertNotNull(trainee);
    }

    @Test
    void getAllTrainees() {
        List<Trainee> testTrainees = gymFacade.getAllTrainees();
        assertNotNull(testTrainees);
    }

    @Test
    void addTrainer() {
        Trainer newTrainer = gymFacade.addTrainer("New Trainer", "New Trainer Last", 1);
        assertNotNull(newTrainer);
    }

    @Test
    void updateTrainer() {
        Trainer oldTrainer = gymFacade.getTrainer(1);
        String oldTrainerName = oldTrainer.getUser().getUsername();
        Trainer updatedTrainer = gymFacade
                .updateTrainer(1, "Updated Name", "Updated Last", false, 2);

        assertNotEquals(oldTrainerName,updatedTrainer.getUser().getUsername());
    }

    @Test
    void getTrainer() {
        Trainer trainer = gymFacade.getTrainer(1);
        assertNotNull(trainer);
    }

    @Test
    void getAllTrainers() {
        List<Trainer> testTrainers = gymFacade.getAllTrainers();
        assertNotNull(testTrainers);
    }

    @Test
    void addTraining() {
        Training newTraining = gymFacade
                .addTraining(1,1, "New Training", 1,new Date(), 1);
        assertNotNull(newTraining);
    }

    @Test
    void getTraining() {
        Training training = gymFacade.getTraining(1);
        assertNotNull(training);
    }

    @Test
    void getAllTrainings() {
        List<Training> testTrainings = gymFacade.getAllTrainings();
        assertNotNull(testTrainings);
    }
}