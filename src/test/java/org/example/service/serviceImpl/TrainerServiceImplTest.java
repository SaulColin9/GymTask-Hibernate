package org.example.service.serviceImpl;

import org.example.configuration.BeanConfiguration;
import org.example.configuration.Storage;
import org.example.configuration.StorageImpl;
import org.example.dao.Dao;
import org.example.dao.DaoConnectionImpl;
import org.example.dao.DaoImpl;
import org.example.model.Trainer;
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
class TrainerServiceImplTest {

//    private TrainerServiceImpl trainerService;
//    private Storage storage = new StorageImpl();
//    private DaoConnectionImpl<Trainer> daoConnection = new DaoConnectionImpl<>(Trainer.class);
//    private DaoConnectionImpl<User> daoConnectionUsers = new DaoConnectionImpl<>(User.class);
//    private List<Trainer> trainers = new ArrayList<>();
//    private List<User> users = new ArrayList<>();
    @Autowired
    private TrainerServiceImpl trainerService;
    @Autowired
    private Storage storage;
    private DaoConnectionImpl<Trainer> daoConnection = new DaoConnectionImpl<>(Trainer.class);
    private DaoConnectionImpl<User> daoConnectionUsers = new DaoConnectionImpl<>(User.class);
    private List<Trainer> trainers = new ArrayList<>();
    private List<User> users = new ArrayList<>();


    @BeforeEach
    public void setUp(){
//        Map<String, Dao> daos = new HashMap<>();
//        daos.put("trainers", new DaoImpl<>(Trainer.class));
//        daos.put("users", new DaoImpl<>(User.class));
//        storage.setDaos(daos);
//        trainerService = new TrainerServiceImpl(storage);
//
        Trainer trainerTest =  new Trainer(1,1);
        trainerTest = trainerTest.setId(1);
        Trainer trainerTest2 = new Trainer(2, 2);
        trainerTest2 = trainerTest2.setId(2);
        trainers.add(trainerTest);
        trainers.add(trainerTest2);

        User userTest = new User("User Test", "User Test", ".");
        User userTest2 = new User("User Test 2", "User Test 2", ".");
        users.add(userTest);
        users.add(userTest2);


        daoConnection = mock(DaoConnectionImpl.class);
        when(daoConnection.getEntities(anyString())).thenReturn(trainers);
        when(daoConnection.writeEntities(anyString(), anyList())).thenReturn(trainers);
        storage.getDao("trainers").setDaoConnection(daoConnection);
        storage.getDao("trainers").setFilePath("mockFilePath");

        daoConnectionUsers = mock(DaoConnectionImpl.class);
        when(daoConnectionUsers.getEntities(anyString())).thenReturn(users);
        when(daoConnectionUsers.writeEntities(anyString(), anyList())).thenReturn(users);
        storage.getDao("users").setDaoConnection(daoConnectionUsers);
        storage.getDao("users").setFilePath("mockFilePath");
    }

    @Test
    void createTrainerProfile() {

        Trainer trainerTest =  new Trainer(1,1);
        trainerTest = trainerTest.setId(storage.getDao("trainers").getNextId());

        Trainer trainerCreated = trainerService.createTrainerProfile("Test Trainer", "Test Trainer Last", 1);

        assertNotNull(trainerCreated);
        assertEquals(trainerTest.getId(), trainerCreated.getId());
        assertEquals(trainerTest.getUserId(), trainerCreated.getUserId());
        verify(daoConnection, times(1)).writeEntities(anyString(), anyList());
    }

    @Test
    void updateTrainerProfile() {
        Optional<Trainer> trainerTest = trainerService.selectTrainerProfile(1);
        trainerTest = Optional.ofNullable(trainerTest.get().setSpecialization(2));

        Trainer updatedTrainer = trainerService.updateTrainerProfile(1, trainerTest.get());
        assertEquals(trainerTest.get().getSpecialization(), updatedTrainer.getSpecialization());
    }

    @Test
    void selectTrainerProfile() {
        Trainer trainerTest = new Trainer(1,1);
        trainerTest.setId(1);
        Optional<Trainer> trainserSelected = trainerService.selectTrainerProfile(1);

        assertNotNull(trainserSelected);
        assertEquals(trainerTest.getId(), trainserSelected.get().getId());
        assertEquals(trainerTest.getUserId(), trainserSelected.get().getUserId());
    }


    @Test
    void selectAll() {
        List<Trainer> selectedTrainers = trainerService.selectAll();
        assertNotNull(selectedTrainers);
        assertEquals(trainers, selectedTrainers);
    }
}