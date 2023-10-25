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
    private static final String UPDATED_NAME = "Updated Name";
    private static final String UPDATED_LAST = "Updated Last Name";
    private static final String UPDATED_ADDRESS = "Updated Address";


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User("first", "last", ".");
        user.setId(1);
        traineeTest = new Trainee(new Date(), "Test Address", user);
        traineeTest.setUser(user);
        traineeTest.setId(1);
        when(userDao.get(anyInt())).thenReturn(Optional.ofNullable(user));
        when(traineeDao.get(anyInt())).thenReturn(Optional.ofNullable(traineeTest));
    }

    @Test
    void createTraineeProfile() {
        when(userDao.save(any(User.class))).thenReturn(user);
        when(traineeDao.save(any(Trainee.class))).thenReturn(traineeTest);
        int createdTrainee = traineeService.createTraineeProfile("Test", "Test", new Date(), "Test");
        assertTrue(createdTrainee > 0);
    }

    @Test
    void updateTraineeProfile() {
        when(userDao.update(anyInt(), any(User.class))).thenReturn(user);
        when(traineeDao.update(anyInt(), any(Trainee.class))).thenReturn(traineeTest);
        boolean updatedTrainee = traineeService
                .updateTraineeProfile(1, UPDATED_NAME, UPDATED_LAST, false, new Date(), UPDATED_ADDRESS);

        assertTrue(updatedTrainee);
    }

    @Test
    void deleteTraineeProfile() {
        when(traineeDao.delete(anyInt())).thenReturn(Optional.ofNullable(traineeTest));

        Trainee trainee = traineeService.selectTraineeProfile(1);
        assertNotNull(trainee);

        traineeService.deleteTraineeProfile(1);
        verify(traineeDao, times(1)).delete(1);
    }

    @Test
    void selectTraineeProfile() {
        Trainee traineeTest = new Trainee(new Date(), "Test Address", new User());
        traineeTest = traineeTest.setId(1);
        when(traineeDao.get(anyInt())).thenReturn(Optional.ofNullable(traineeTest));
        Trainee traineeSelected = traineeService.selectTraineeProfile(1);

        assertNotNull(traineeSelected);
        assertEquals(traineeTest.getId(), traineeSelected.getId());
        assertEquals(traineeTest.getUser().getId(), traineeSelected.getUser().getId());
    }

    @Test
    void update_noTraineeFound_Should_Return_Null() {
        when(traineeDao.get(anyInt())).thenReturn(Optional.empty());
        assertFalse(traineeService.updateTraineeProfile(1, UPDATED_NAME, UPDATED_LAST, false, new Date(), UPDATED_ADDRESS));
    }

    @Test
    void update_noUserToUpdateFound_Should_Return_Null() {
        when(userDao.get(anyInt())).thenReturn(Optional.empty());
        when(traineeDao.get(anyInt())).thenReturn(Optional.ofNullable(traineeTest));
        assertFalse(traineeService.updateTraineeProfile(1, UPDATED_NAME, UPDATED_LAST, false, new Date(), UPDATED_ADDRESS));
    }


}