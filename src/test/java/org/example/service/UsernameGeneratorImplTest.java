package org.example.service;

import org.example.dao.DaoImpl;
import org.example.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UsernameGeneratorImplTest {
    private DaoImpl userDao;
    private final String testName = "Test";
    private final String testLastName = "TestLast";
    private final String separator = ".";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        List<User> users;
        userDao = mock(DaoImpl.class);
        users = new ArrayList<>();
        users.add(new User(testName, testLastName, separator));
        users.add(new User(testName, testLastName, separator));
        when(userDao.getAll()).thenReturn(users);
    }

    @Test
    void userNameExists() {
        int serial = UsernameGeneratorImpl.userNameExists(testName, testLastName, separator, userDao);
        assertTrue(serial > 0);

    }

    @Test
    void generateUserName() {
        String usernameGenerated = UsernameGeneratorImpl.generateUserName(testName, testLastName, separator, userDao);
        assertNotEquals(usernameGenerated, "Test.TestLast");
    }


}