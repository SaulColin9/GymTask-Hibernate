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
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        user = new User("first", "last", ".");
        user.setId(1);

        trainerTest = new Trainer(1, 1);
        trainerTest.setUser(user);
        trainerTest.setId(1);

        when(userDao.get(anyInt())).thenReturn(Optional.ofNullable(user));
        when(trainerDao.get(anyInt())).thenReturn(Optional.ofNullable(trainerTest));

    }

    @Test
    void createTrainerProfile() {
        when(userDao.save(any(User.class))).thenReturn(user);
        when(trainerDao.save(any(Trainer.class))).thenReturn(trainerTest);
        Trainer createdTrainer = trainerService.createTrainerProfile("Test", "Test", 1);
        assertNotNull(createdTrainer);
    }

    @Test
    void updateTrainerProfile() {
        when(userDao.update(anyInt(),any(User.class))).thenReturn(user);
        when(trainerDao.update(anyInt(), any(Trainer.class))).thenReturn(trainerTest);
        Trainer trainerToUpdate = trainerService.selectTrainerProfile(1);
        String trainerTestUserName = trainerToUpdate.getUser().getUsername();

        Trainer updatedTrainer = trainerService.updateTrainerProfile(1, "Updated Trainer Name", "Updated Trainer Last", false, 1);
        assertNotEquals(trainerTestUserName, updatedTrainer.getUser().getUsername());
    }

    @Test
    void selectTrainerProfile() {
        Trainer trainerTest = new Trainer(1,1);
        trainerTest.setId(1);
        Trainer trainserSelected = trainerService.selectTrainerProfile(1);

        assertNotNull(trainserSelected);
        assertEquals(trainerTest.getId(), trainserSelected.getId());
        assertEquals(trainerTest.getUserId(), trainserSelected.getUserId());
    }
}