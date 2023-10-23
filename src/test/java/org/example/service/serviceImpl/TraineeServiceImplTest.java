package org.example.service.serviceImpl;

import org.example.configuration.Storage;
import org.example.dao.DaoConnectionImpl;
import org.example.model.Trainee;
import org.example.model.Trainer;
import org.example.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
class TraineeServiceImplTest {

    @Autowired
    private TraineeServiceImpl traineeService;
    @Autowired
    Storage storage;
    private DaoConnectionImpl<Trainee> daoConnection = new DaoConnectionImpl<>(Trainee.class);
    private DaoConnectionImpl<User> daoConnectionUsers = new DaoConnectionImpl<>(User.class);
    private List<Trainee> trainees = new ArrayList<>();
    private List<User> users = new ArrayList<>();


    @BeforeEach
    public void setUp(){
        Trainee traineeTest =  new Trainee(new Date(), "Test Address", 1);
        traineeTest = traineeTest.setId(1);
        Trainee traineeTest2 =  new Trainee(new Date(), "Test Address 2", 2);
        traineeTest2 = traineeTest2.setId(2);
        trainees.add(traineeTest);
        trainees.add(traineeTest2);

        User userTest = new User("User Test", "User Test", ".");
        User userTest2 = new User("User Test 2", "User Test 2", ".");
        users.add(userTest);
        users.add(userTest2);


        daoConnection = mock(DaoConnectionImpl.class);
        when(daoConnection.getEntities(anyString())).thenReturn(trainees);
        when(daoConnection.writeEntities(anyString(), anyList())).thenReturn(trainees);
        storage.getDao("trainees").setDaoConnection(daoConnection);
        storage.getDao("trainees").setFilePath("mockFilePath");

        daoConnectionUsers = mock(DaoConnectionImpl.class);
        when(daoConnectionUsers.getEntities(anyString())).thenReturn(users);
        when(daoConnectionUsers.writeEntities(anyString(), anyList())).thenReturn(users);
        storage.getDao("users").setDaoConnection(daoConnectionUsers);
        storage.getDao("users").setFilePath("mockFilePath");
    }
    @Test
    void createTraineeProfile() {
        Trainee traineeTest = new Trainee(new Date(), "Test Address", 1);
        traineeTest = traineeTest.setId(storage.getDao("trainees").getNextId());

        Trainee traineeCreated = traineeService.createTraineeProfile("Test Trainee", "Test Trainee Last", new Date(), "Test Address");

        assertNotNull(traineeCreated);
        assertEquals(traineeTest.getId(), traineeCreated.getId());
        assertEquals(traineeTest.getUserId(), traineeCreated.getUserId());

    }

    @Test
    void updateTraineeProfile() {
        fail();
    }

    @Test
    void deleteTraineeProfile() {
        Optional<Trainee> traineeTest =  traineeService.selectTraineeProfile(1);
        assertTrue(traineeTest.isPresent());

        traineeService.deleteTraineeProfile(1);
        Optional<Trainee> deletedTrainee = traineeService.selectTraineeProfile(1);
        assertTrue(deletedTrainee.isEmpty());


    }

    @Test
    void selectTraineeProfile() {
        Trainee traineeTest =  new Trainee(new Date(), "Test Address", 1);
        traineeTest = traineeTest.setId(1);
        Optional<Trainee> traineeSelected = traineeService.selectTraineeProfile(1);

        assertNotNull(traineeSelected);
        assertEquals(traineeTest.getId(), traineeSelected.get().getId());
        assertEquals(traineeTest.getUserId(), traineeSelected.get().getUserId());

    }

    @Test
    void selectAll() {
        List<Trainee> selectedTrainees = traineeService.selectAll();
        assertNotNull(selectedTrainees);
        assertEquals(trainees, selectedTrainees);
    }
}