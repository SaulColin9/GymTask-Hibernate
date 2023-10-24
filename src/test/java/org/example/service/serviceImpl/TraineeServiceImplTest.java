package org.example.service.serviceImpl;

import org.example.dao.Dao;
import org.example.model.Trainee;
import org.example.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;


class TraineeServiceImplTest {
    @Mock
    private Dao<Trainee> traineeDao;
    @Mock
    private Dao<User> userDao;
    @InjectMocks
    private TraineeServiceImpl traineeService;
    private User user;
    private Trainee traineeTest;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User("first", "last", ".");
        user.setId(1);
        traineeTest = new Trainee(new Date(), "Test Address", 1);
        traineeTest.setUser(user);
        traineeTest.setId(1);
        when(userDao.get(anyInt())).thenReturn(Optional.ofNullable(user));
        when(traineeDao.get(anyInt())).thenReturn(Optional.ofNullable(traineeTest));
    }

    @Test
    void createTraineeProfile() {
        when(userDao.save(any(User.class))).thenReturn(user);
        when(traineeDao.save(any(Trainee.class))).thenReturn(traineeTest);
        Trainee createdTrainee = traineeService.createTraineeProfile("Test", "Test", new Date(), "Test");
        assertNotNull(createdTrainee);
    }

    @Test
    void updateTraineeProfile() {
        when(userDao.update(anyInt(), any(User.class))).thenReturn(user);
        when(traineeDao.update(anyInt(), any(Trainee.class))).thenReturn(traineeTest);
        Trainee traineeTest = traineeService.selectTraineeProfile(1);
        traineeTest = traineeTest.setAddress("New Address Test");
        String traineeTestUserName = traineeTest.getUser().getUsername();

        Trainee updatedTrainee = traineeService
                .updateTraineeProfile(1, "Updated Name", "Updated Last Name", false, new Date(), "Updated Addresss");

        assertNotEquals(traineeTestUserName, updatedTrainee.getUser().getUsername());
    }

    @Test
    void deleteTraineeProfile() {
        when(traineeDao.delete(anyInt())).thenReturn(Optional.ofNullable(traineeTest));

        Trainee traineeTest = traineeService.selectTraineeProfile(1);
        assertNotNull(traineeTest);

        traineeService.deleteTraineeProfile(1);
        verify(traineeDao, times(1)).delete(1);
    }

    @Test
    void selectTraineeProfile() {
        Trainee traineeTest = new Trainee(new Date(), "Test Address", 1);
        traineeTest = traineeTest.setId(1);
        when(traineeDao.get(anyInt())).thenReturn(Optional.ofNullable(traineeTest));
        Trainee traineeSelected = traineeService.selectTraineeProfile(1);

        assertNotNull(traineeSelected);
        assertEquals(traineeTest.getId(), traineeSelected.getId());
        assertEquals(traineeTest.getUserId(), traineeSelected.getUserId());

    }


}