package org.example.service.serviceImpl;

import org.example.dao.Dao;
import org.example.model.Trainer;
import org.example.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrainerServiceImplTest {
    @InjectMocks
    private TrainerServiceImpl trainerService;
    @Mock
    private Dao<Trainer> trainerDao;
    @Mock
    private Dao<User> userDao;
    private User user;
    private Trainer trainerTest;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User("first", "last", ".");
        user.setId(1);

        trainerTest = new Trainer(1, user);
        trainerTest.setUser(user);
        trainerTest.setId(1);

        when(userDao.get(anyInt())).thenReturn(Optional.ofNullable(user));
        when(trainerDao.get(anyInt())).thenReturn(Optional.ofNullable(trainerTest));

    }

    @Test
    void createTrainerProfile() {
        when(userDao.save(any(User.class))).thenReturn(user);
        when(trainerDao.save(any(Trainer.class))).thenReturn(trainerTest);
        int createdTrainer = trainerService.createTrainerProfile("Test", "Test", 1);
        assertTrue(createdTrainer>0);
    }

    @Test
    void updateTrainerProfile() {
        when(userDao.update(anyInt(), any(User.class))).thenReturn(user);
        when(trainerDao.update(anyInt(), any(Trainer.class))).thenReturn(trainerTest);

        boolean updatedTrainer = trainerService.updateTrainerProfile(1, "Updated Trainer Name", "Updated Trainer Last", false, 1);
        assertTrue(updatedTrainer);
    }

    @Test
    void selectTrainerProfile() {
        Trainer trainerTest = new Trainer(1, user);
        trainerTest.setId(1);
        Trainer trainserSelected = trainerService.selectTrainerProfile(1);

        assertNotNull(trainserSelected);
        assertEquals(trainerTest.getId(), trainserSelected.getId());
        assertEquals(trainerTest.getUser().getId(), trainserSelected.getUser().getId());
    }

    @Test
    void nullParameter_ShouldReturn_Invalid_Id(){
        int createdTrainer = trainerService.createTrainerProfile(null, "", 1);
        assertTrue(createdTrainer<0);
    }

    @Test
    void provided_Select_Id_Not_Found(){
        when(trainerDao.get(anyInt())).thenReturn(Optional.empty());
        assertNull(trainerService.selectTrainerProfile(100));
    }

    @Test
    void provided_Update_Id_Not_Found(){
        when(trainerDao.get(anyInt())).thenReturn(Optional.empty());
        assertFalse(trainerService
                .updateTrainerProfile(100, "First", "Last", true,1));
    }
}