package org.example.service;

import org.example.dao.DaoImpl;
import org.example.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UsernameGeneratorImplTest {


    private DaoImpl<User> userDao;
    private List<User> users;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        userDao = mock(DaoImpl.class);
        users = new ArrayList<>();
        users.add(new User("Test","TestLast", "."));
        users.add(new User("Test","TestLast", "."));
        when(userDao.getAll()).thenReturn(users);
    }
    @Test
    void userNameExists() {
        int serial = UsernameGeneratorImpl.userNameExists("Test", "TestLast", ".", userDao);
        assertTrue(serial>0);

    }

    @Test
    void generateUserName() {
        String usernameGenerated = UsernameGeneratorImpl.generateUserName("Test", "TestLast", ".", userDao);
        assertNotEquals(usernameGenerated, "Test.TestLast");
    }
}