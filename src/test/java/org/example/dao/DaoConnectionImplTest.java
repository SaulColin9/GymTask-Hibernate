package org.example.dao;


import org.example.model.User;
import org.example.service.serviceImpl.TestConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
class DaoConnectionImplTest {
    private DaoConnectionImpl<User> daoConnectionUsers = new DaoConnectionImpl<>(User.class);

    @Test
    void getEntities() {
        assertThrows(RuntimeException.class,()->daoConnectionUsers.getEntities(""));
    }

    @Test
    void writeEntities() {
        assertThrows(RuntimeException.class,()->daoConnectionUsers.writeEntities("", new ArrayList<>()));
    }
}