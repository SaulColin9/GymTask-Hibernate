package org.example.dao;

import org.example.configuration.GymStorageImpl;
import org.example.dao.entities.UserDao;
import org.example.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class DaoImplUserTest {
    @InjectMocks
    DaoImpl<User> daoUser;
    @Mock
    Map<Integer, User> storageEntities;
    GymStorageImpl storage;
    User user;
    User user2;

    @BeforeEach
    void setUp() {
        user = new User("User Test", "User Test");
        user.setId(1);
        user2 = new User("User Test2", "User Test2");
        user2.setId(2);
        Map<Integer, User> users = new HashMap<>();
        users.put(1, user);
        users.put(2, user2);

        storage = new GymStorageImpl();
        storage.setUsers(users);
        daoUser = new UserDao();
        daoUser.setStorage(storage);

    }


    @Test
    void getNextId() {
        assertEquals(storage.getUsers().values().size() + 1, daoUser.getNextId());
    }

    @Test
    void get() {
        assertEquals(daoUser.get(1).orElse(null), user);
    }

    @Test
    void getAll() {
        assertEquals(daoUser.getAll().size(), storage.getUsers().values().size());
    }

    @Test
    void save() {
        User newUser = new User();
        newUser.setId(daoUser.getNextId());
        assertEquals(newUser.getId(), daoUser.save(new User()).getId());
    }

    @Test
    void update() {
        User oldUser = user;
        User updatedUser = daoUser.update(1, user2);
        assertNotEquals(oldUser.getFirstName(), updatedUser.getFirstName());
        assertEquals(oldUser.getId(), updatedUser.getId());

    }

    @Test
    void delete() {
        Optional<User> deletedUser = daoUser.delete(1);
        assertNotNull(deletedUser);
    }

}