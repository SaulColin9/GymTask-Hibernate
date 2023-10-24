package org.example.dao;

import org.example.configuration.GymStorageImpl;
import org.example.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.swing.text.html.Option;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DaoImplTest {
    @InjectMocks
    DaoImpl<User> daoUser;
    @Mock
    Map<Integer, User> storageEntities;
    GymStorageImpl storage;
    User user;
    User user2;

    @BeforeEach
    void setUp(){
        user = new User("User Test", "User Test", ".");
        user.setId(1);
        user2 = new User("User Test2", "User Test2", ".");
        user2.setId(2);
        Map<Integer,User> users = new HashMap<>();
        users.put(1,user);
        users.put(2,user2);

        storage = new GymStorageImpl();
        storage.setUsers(users);
        daoUser = new DaoImpl<>(User.class);
        daoUser.setStorage(storage);

    }
    @Test
    void setStorage() {
    }

    @Test
    void getNextId() {
        assertEquals(daoUser.getNextId(),storage.getUsers().values().size());
    }

    @Test
    void get() {
        assertEquals(daoUser.get(1).orElse(null), user);
    }

    @Test
    void getAll() {
        assertEquals(daoUser.getAll().size(),storage.getUsers().values().size());
    }

    @Test
    void save() {
        int previousSize = daoUser.storageEntities.size();
        User newUser = new User();
        newUser.setId(2);
        assertEquals(newUser.getId(),daoUser.save(new User()).getId());;
    }

    @Test
    void update() {
        User oldUser = user;
        User updatedUser = daoUser.update(1, user2);
        assertNotEquals(oldUser.getFirstName(), updatedUser.getFirstName());
        assertEquals(oldUser.getId(),updatedUser.getId());

    }

    @Test
    void delete() {
        Optional<User> deletedUser = daoUser.delete(1);
        assertNotNull(deletedUser);
    }

}