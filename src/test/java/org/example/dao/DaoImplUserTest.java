package org.example.dao;

import org.example.configuration.inmemory.storage.GymStorageImpl;
import org.example.dao.inmemory.UserDao;
import org.example.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;


class DaoImplUserTest {
    @InjectMocks
    UserDao daoUser;
    @Mock
    Map<Integer, User> storageEntities;
    Map<Integer, User> users;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void givenEmptyStorageEntities_NextIdShouldBeOne() {
        // arrange
        storageEntities = new HashMap<>();

        GymStorageImpl storage = new GymStorageImpl();
        storage.setUsers(storageEntities);
        daoUser.setStorage(storage);

        // act
        int actualResponse = daoUser.getNextId();

        // assert
        assertThat(actualResponse).isEqualTo(1);

    }

    @Test
    void givenNonEmptyStorageEntities_NextIdShouldBeReturned() {
        // arrange
        users = createNewStorageEntities();

        GymStorageImpl storage = new GymStorageImpl();
        storage.setUsers(users);
        daoUser.setStorage(storage);

        // act
        int actualResponse = daoUser.getNextId();

        // assert
        assertThat(actualResponse).isEqualTo(3);

    }


    @Test
    void givenUserId_UserShouldBeReturned() {
        // arrange
        users = createNewStorageEntities();

        GymStorageImpl storage = new GymStorageImpl();
        storage.setUsers(users);
        daoUser.setStorage(storage);

        // act
        Optional<User> actualResponse = daoUser.get(1);

        // assert
        assertThat(actualResponse.orElse(null)).isEqualTo(users.get(1));

    }

    @Test
    void givenStorageEntitiesIsNotEmpty_ListOfUsersShouldBeReturned() {
        // arrange
        users = createNewStorageEntities();

        GymStorageImpl storage = new GymStorageImpl();
        storage.setUsers(users);
        daoUser.setStorage(storage);
        daoUser.setStorage(storage);

        // act
        List<User> actualResponse = daoUser.getAll();

        // assert
        assertThat(actualResponse).isEqualTo(new ArrayList<>(users.values()));

    }

    @Test
    void givenAUser_UserShouldBeSaved() {
        // arrange
        User newUser = new User();

        newUser.setIsActive(true);
        newUser.setFirstName("John");
        newUser.setLastName("Doe");
        newUser.setPassword("password");
        newUser.setUsername("John.Doe");

        users = createNewStorageEntities();

        GymStorageImpl storage = new GymStorageImpl();
        storage.setUsers(users);
        daoUser.setStorage(storage);

        // act
        User actualResponse = daoUser.save(newUser);

        // assert
        assertThat(actualResponse).isEqualTo(newUser);
        assertThat(actualResponse.getId()).isEqualTo(3);

    }

    @Test
    void givenNonExistingId_UserShouldBeUpdated() {
        // arrange
        User newUser = new User();

        newUser.setIsActive(false);
        newUser.setFirstName("John");
        newUser.setLastName("Doe");
        newUser.setPassword("password");
        newUser.setUsername("John.Doe");

        users = createNewStorageEntities();

        GymStorageImpl storage = new GymStorageImpl();
        storage.setUsers(users);
        daoUser.setStorage(storage);

        // act
        User actualResponse = daoUser.update(1, newUser);

        // assert
        assertThat(actualResponse).isNotNull();
        assertThat(actualResponse.getUsername()).isEqualTo("John.Doe");
        assertThat(actualResponse.getId()).isEqualTo(1);
        assertThat(actualResponse.getIsActive()).isFalse();

    }

    @Test
    void givenExistingUserId_UserShouldBeDeleted() {
        // arrange
        users = createNewStorageEntities();

        GymStorageImpl storage = new GymStorageImpl();
        storage.setUsers(users);
        daoUser.setStorage(storage);

        // act
        Optional<User> actualResponse = daoUser.delete(1);

        // assert
        assertThat(actualResponse.orElse(null)).isNotNull();
        assertThat(users.get(1)).isNull();

    }

    Map<Integer, User> createNewStorageEntities() {
        Map<Integer, User> users = new HashMap<>();
        User user = new User("User Test", "User Test");
        user.setId(1);
        User user2 = new User("User Test2", "User Test2");
        user2.setId(2);

        users.put(1, user);
        users.put(2, user2);
        return users;
    }

}