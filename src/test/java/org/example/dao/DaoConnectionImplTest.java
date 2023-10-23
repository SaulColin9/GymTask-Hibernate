package org.example.dao;

import org.example.configuration.Storage;
import org.example.model.Trainer;
import org.example.model.User;
import org.example.service.serviceImpl.TestConfig;
import org.example.service.serviceImpl.TrainerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
class DaoConnectionImplTest {


    @Autowired
    private TrainerServiceImpl trainerService;
    @Autowired
    private Storage storage;
    private DaoConnectionImpl<User> daoConnectionUsers = new DaoConnectionImpl<>(User.class);
    private List<User> users = new ArrayList<>();


    @BeforeEach
    public void setUp(){
//        daoConnectionUsers = mock(DaoConnectionImpl.class);
//        when(daoConnectionUsers.getEntities(anyString())).thenReturn(users);
//        when(daoConnectionUsers.writeEntities(anyString(), anyList())).thenReturn(users);
//        storage.getDao("users").setDaoConnection(daoConnectionUsers);
//        storage.getDao("users").setFilePath("mockFilePath");
    }
    @Test
    void getEntities() {
        assertThrows(RuntimeException.class,()->daoConnectionUsers.getEntities(""));
    }

    @Test
    void writeEntities() {
        assertThrows(RuntimeException.class,()->daoConnectionUsers.writeEntities("", new ArrayList<>()));
    }
}