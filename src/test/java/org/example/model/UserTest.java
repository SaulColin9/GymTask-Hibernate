package org.example.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Mock
    private User user;

    @BeforeEach
    void setUp() {
        user = new User("FirstName", "LastName",
                "FirstName.LastName", "Pswd", true);
        user.setId(1);
    }

    @Test
    void getLastName() {
        assertEquals(user.getLastName(), "LastName");
    }

    @Test
    void getPassword() {
        assertEquals(user.getPassword(), "Pswd");
    }

    @Test
    void getIsActive() {
        assertTrue(user.getIsActive());
    }
}