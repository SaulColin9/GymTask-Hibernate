package org.example.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Date;

import static org.assertj.core.api.Assertions.*;


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
        assertThat(trainee.getId()).isEqualTo(1);
    }

    @Test
    void setId() {
        int newId = 2;
        trainee.setId(newId);
        assertThat(newId).isEqualTo(trainee.getId());
    }

    @Test
    void getUser() {
        assertThat(trainee.getUser()).
                isNotNull().
                isInstanceOf(User.class);
    }

    @Test
    void getDateOfBirth() {
        assertThat(trainee.getDateOfBirth()).
                isNotNull().
                isInstanceOf(Date.class);
    }

    @Test
    void setDateOfBirth() {
        Date newDate = new Date();
        trainee.setDateOfBirth(newDate);
        assertThat(trainee.getDateOfBirth()).isEqualTo(newDate);
    }

    @Test
    void getAddress() {
        assertThat(trainee.getAddress()).
                isEqualTo("Test Address").
                isInstanceOf(String.class);
    }

    @Test
    void setAddress() {
        String newAddress = "New Address";
        trainee.setAddress(newAddress);
        assertThat(trainee.getAddress()).
                isEqualTo(newAddress);
    }

    @Test
    void getUserId() {
        int userId = 1;
        trainee.getUser().setId(userId);
        assertThat(trainee.getUser().getId()).isEqualTo(1);
    }

    @Test
    void setUser() {
        User newUser = new User("TestName", "TestLast");
        trainee.setUser(newUser);
        assertThat(trainee.getUser()).isEqualTo(newUser);
    }

    @Test
    void testToString() {
        assertThat(trainee.toString().contains("Trainee")).isTrue();
    }
}