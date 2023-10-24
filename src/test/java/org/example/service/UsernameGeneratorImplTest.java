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
    private DaoImpl<User> userDao;
    private final static String TEST_NAME = "Test";
    private final static String TEST_LASTNAME = "TestLast";
    private final static String SEPARATOR = ".";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        List<User> users;
        userDao = mock(DaoImpl.class);
        users = new ArrayList<>();
        users.add(new User(TEST_NAME, TEST_LASTNAME, SEPARATOR));
        users.add(new User(TEST_NAME, TEST_LASTNAME, SEPARATOR));
        when(userDao.getAll()).thenReturn(users);
    }

    @Test
    void userNameExists() {
        int serial = UsernameGeneratorImpl.userNameExists(TEST_NAME, TEST_LASTNAME, SEPARATOR, userDao);
        assertTrue(serial > 0);

    }

    @Test
    void generateUserName() {
        String usernameGenerated = UsernameGeneratorImpl.generateUserName(TEST_NAME, TEST_LASTNAME, SEPARATOR, userDao);
        assertNotEquals(usernameGenerated, "Test.TestLast");
    }


}