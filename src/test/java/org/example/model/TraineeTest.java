package org.example.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TraineeTest {
    @Mock
    private Trainee trainee;

    @BeforeEach
    void setUp() {
        trainee = new Trainee(new Date(), "Test Address", new User());
        trainee.setId(1);
    }


    @Test
    void getId() {
        assertEquals(trainee.getId(), 1);
    }

    @Test
    void setId() {
        int newId = 2;
        trainee.setId(newId);
        assertEquals(newId, trainee.getId());
    }

    @Test
    void getUser() {
        assertInstanceOf(User.class, trainee.getUser());
    }

    @Test
    void getDateOfBirth() {
        assertInstanceOf(Date.class, trainee.getDateOfBirth());
    }

    @Test
    void setDateOfBirth() {
        Date newDate = new Date();
        trainee.setDateOfBirth(newDate);
        assertEquals(newDate, trainee.getDateOfBirth());
    }

    @Test
    void getAddress() {
        assertInstanceOf(String.class, trainee.getAddress());
    }

    @Test
    void setAddress() {
        String newAddress = "New Address";
        trainee.setAddress(newAddress);
        assertEquals(newAddress, trainee.getAddress());
    }

    @Test
    void getUserId() {
        int userId = 1;
        trainee.getUser().setId(1);
        assertEquals(userId, trainee.getUser().getId());
    }

    @Test
    void setUser() {
        User newUser = new User("TestName", "TestLast");
        trainee.setUser(newUser);
        assertEquals(newUser, trainee.getUser());
    }

    @Test
    void testToString() {
        assertTrue(trainee.toString().contains("Trainee"));
    }
}