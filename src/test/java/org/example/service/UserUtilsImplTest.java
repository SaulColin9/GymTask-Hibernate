package org.example.service;

import org.example.dao.Dao;
import org.example.model.User;
import org.example.service.utils.PasswordGenerator;
import org.example.service.utils.user.InMemoryUserUtils;
import org.example.service.utils.UsernameGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;


class UserUtilsImplTest {
    @InjectMocks
    InMemoryUserUtils userUtils;
    @Mock
    Dao<User> userDao;
    @Mock
    PasswordGenerator passwordGenerator;
    @Mock
    UsernameGenerator usernameGenerator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void givenNames_UserShouldBeCreated() {
        // arrange
        String firstName = "John";
        String lastName = "Doe";

        when(usernameGenerator.generateUserName("John", "Doe")).thenReturn("John.Doe");
        when(passwordGenerator.generatePassword(10)).thenReturn("randomPassword");

        // act
        User actualResponse = userUtils.createUser(firstName, lastName);

        // assert
        assertThat(actualResponse).isNotNull();
        assertThat(actualResponse.getUsername()).isEqualTo("John.Doe");
        verify(usernameGenerator, times(1)).generateUserName("John", "Doe");
        verify(passwordGenerator, times(1)).generatePassword(10);

    }


    @Test
    void givenValidInput_UserShouldBeUpdated() {
        // arrange
        int userId = 1;
        String newFirstName = "Jane";
        String newLastName = "Foo";
        boolean isActive = false;

        when(userDao.get(1)).thenReturn(Optional.of(createNewUser()));

        // act
        User updatedUser = userUtils.updateUser(userId, newFirstName, newLastName, isActive);

        // assert
        assertThat(updatedUser).isNotNull();
        assertThat(updatedUser.getUsername()).isEqualTo("John.Doe");
        assertThat(updatedUser.getPassword()).isEqualTo("password");
        verify(userDao, times(1)).get(1);

    }


    @Test
    void givenUserId_UserShouldBeDeleted() {
        // arrange
        User userToBeDeleted = createNewUser();
        when(userDao.delete(1)).thenReturn(Optional.of(userToBeDeleted));

        // act
        Optional<User> userDeleted = userUtils.deleteUser(1);

        // assert
        assertThat(userDeleted.orElse(null)).isEqualTo(userToBeDeleted);
        verify(userDao, times(1)).delete(1);

    }

    User createNewUser() {
        User newUser = new User();
        newUser.setIsActive(true);
        newUser.setFirstName("John");
        newUser.setLastName("Doe");
        newUser.setPassword("password");
        newUser.setUsername("John.Doe");
        newUser.setId(1);

        return newUser;
    }
}