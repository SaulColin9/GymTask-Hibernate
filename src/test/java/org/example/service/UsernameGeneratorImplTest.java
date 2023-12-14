package org.example.service;

import org.example.dao.Dao;
import org.example.model.User;
import org.example.service.utils.UsernameGeneratorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class UsernameGeneratorImplTest {
    @Mock
    private Dao<User> userDao;
    @InjectMocks
    private UsernameGeneratorImpl usernameGenerator;
    private static final String TEST_NAME = "Test";
    private static final String TEST_LASTNAME = "TestLast";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void givenExistingUserName_SerialNumberShouldBeGraterThanZero() {
        // arrange
        List<User> users = new ArrayList<>();
        users.add(new User(TEST_NAME, TEST_LASTNAME));
        users.add(new User(TEST_NAME, TEST_LASTNAME));

        when(userDao.getAll()).thenReturn(users);

        // act
        int serial = usernameGenerator.userNameExists(TEST_NAME, TEST_LASTNAME);

        // assert
        assertThat(serial).isEqualTo(2);
        verify(userDao, times(1)).getAll();

    }

    @Test
    void givenNonExistingUserName_SerialNumberShouldBeZero() {
        // arrange
        List<User> users = new ArrayList<>();
        users.add(new User(TEST_NAME, TEST_LASTNAME));
        users.add(new User(TEST_NAME, TEST_LASTNAME));

        when(userDao.getAll()).thenReturn(users);

        // act
        int serial = usernameGenerator.userNameExists("John", "Doe");

        // assert
        assertThat(serial).isEqualTo(0);
        verify(userDao, times(1)).getAll();

    }

    @Test
    void givenNonExistingUsername_NoSuffixShouldBeAdded() {
        // arrange
        List<User> users = new ArrayList<>();
        users.add(new User(TEST_NAME, TEST_LASTNAME));
        users.add(new User(TEST_NAME, TEST_LASTNAME));

        when(userDao.getAll()).thenReturn(users);

        // act
        String actualResponse = usernameGenerator.generateUserName("John", "Doe");

        // assert
        assertThat(actualResponse).isEqualTo("John.Doe");
        verify(userDao, times(1)).getAll();

    }


}