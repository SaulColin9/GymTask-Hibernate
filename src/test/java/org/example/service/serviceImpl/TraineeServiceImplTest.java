package org.example.service.serviceImpl;

import org.example.configuration.Storage;
import org.example.dao.DaoConnectionImpl;
import org.example.model.Trainee;
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
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
class TraineeServiceImplTest {


    @Autowired
    private TraineeServiceImpl traineeService;
    @Autowired
    Storage storage;
    private DaoConnectionImpl<Trainee> daoConnection = new DaoConnectionImpl<>(Trainee.class);
    private List<Trainee> trainees = new ArrayList<>();
    private List<User> users = new ArrayList<>();


    @BeforeEach
    public void setUp(){
        DaoConnectionImpl<User> daoConnectionUsers;
        User userTest = new User("User Test", "User Test", ".");
        userTest.setId(1);
        User userTest2 = new User("User Test 2", "User Test 2", ".");
        userTest2.setId(2);
        users.add(userTest);
        users.add(userTest2);

        Trainee traineeTest =  new Trainee(new Date(), "Test Address", 1, userTest);
        traineeTest = traineeTest.setId(1);
        traineeTest.setUser(userTest);
        Trainee traineeTest2 =  new Trainee(new Date(), "Test Address 2", 2, userTest2);
        traineeTest2 = traineeTest2.setId(2);
        traineeTest2.setUser(userTest2);
        System.out.println(traineeTest2.getUser());
        trainees.add(traineeTest);
        trainees.add(traineeTest2);

        daoConnection = mock(DaoConnectionImpl.class);
        when(daoConnection.getEntities(anyString())).thenReturn(trainees);
        when(daoConnection.writeEntities(anyString(), anyList())).thenReturn(trainees);

        daoConnectionUsers = mock(DaoConnectionImpl.class);
        when(daoConnectionUsers.getEntities(anyString())).thenReturn(users);
        when(daoConnectionUsers.writeEntities(anyString(), anyList())).thenReturn(users);
    }
    @Test
    void createTraineeProfile() {
        Trainee traineeTest = new Trainee(new Date(), "Test Address", 1);
        traineeTest = traineeTest.setId(storage.getTraineeDao().getNextId());

        Trainee traineeCreated = traineeService.createTraineeProfile("Test Trainee", "Test Trainee Last", new Date(), "Test Address");

        assertNotNull(traineeCreated);
        assertEquals(traineeTest.getId(), traineeCreated.getId());
        verify(daoConnection, times(1)).writeEntities(anyString(), anyList());
    }

    @Test
    void updateTraineeProfile() {
        Trainee traineeTest = traineeService.selectTraineeProfile(1);
        traineeTest = traineeTest.setAddress("New Address Test");
        String traineeTestUserName = traineeTest.getUser().getUsername();

        Trainee updatedTrainee = traineeService
                .updateTraineeProfile(1, "Updated Name", "Updated Last Name", false, new Date(), "Updated Addresss");

        assertNotEquals(traineeTestUserName, updatedTrainee.getUser().getUsername());
    }

    @Test
    void deleteTraineeProfile() {
        Trainee traineeTest =  traineeService.selectTraineeProfile(1);
        assertNotNull(traineeTest);

        traineeService.deleteTraineeProfile(1);
        Trainee deletedTrainee = traineeService.selectTraineeProfile(1);
        assertNull(deletedTrainee);
        verify(daoConnection, times(1)).writeEntities(anyString(), anyList());
    }

    @Test
    void selectTraineeProfile() {
        Trainee traineeTest =  new Trainee(new Date(), "Test Address", 1);
        traineeTest = traineeTest.setId(1);
        Trainee traineeSelected = traineeService.selectTraineeProfile(1);

        assertNotNull(traineeSelected);
        assertEquals(traineeTest.getId(), traineeSelected.getId());
        assertEquals(traineeTest.getUserId(), traineeSelected.getUserId());

    }

    @Test
    void selectAll() {
        List<Trainee> selectedTrainees = traineeService.selectAll();
        assertNotNull(selectedTrainees);
        assertEquals(trainees, selectedTrainees);
    }
    @Test
    void trainersUsernamesDifferent(){
        Trainee trainee1 = traineeService.createTraineeProfile("John", "Smith", new Date(), "Test address");
        Trainee trainee2 = traineeService.createTraineeProfile("John", "Smith", new Date(), "Test address");

        assertNotEquals(trainee1.getUser().getUsername(), trainee2.getUser().getUsername());

    }


}