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
    private static final String TEST_NAME = "Test";
    private static final String TEST_LASTNAME = "TestLast";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        List<User> users;
        userDao = mock(DaoImpl.class);
        users = new ArrayList<>();
        users.add(new User(TEST_NAME, TEST_LASTNAME));
        users.add(new User(TEST_NAME, TEST_LASTNAME));
        when(userDao.getAll()).thenReturn(users);
    }

    @Test
    void userNameExists() {
        UsernameGenerator usernameGenerator = new UsernameGeneratorImpl();
        int serial = usernameGenerator.userNameExists(TEST_NAME, TEST_LASTNAME, userDao);
        assertTrue(serial > 0);

    }

    @Test
    void generateUserName() {
        UsernameGenerator usernameGenerator = new UsernameGeneratorImpl();
        String usernameGenerated = usernameGenerator.generateUserName(TEST_NAME, TEST_LASTNAME, userDao);
        assertNotEquals(usernameGenerated, "Test.TestLast");
    }


}