package org.example.dao;


import org.example.model.User;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

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