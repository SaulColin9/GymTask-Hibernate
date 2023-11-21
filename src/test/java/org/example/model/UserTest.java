package org.example.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;


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
        assertThat(user.getLastName()).isEqualTo("LastName");
    }

    @Test
    void getPassword() {
        assertThat(user.getPassword()).isEqualTo("Pswd");
    }

    @Test
    void getIsActive() {
        assertThat(user.getIsActive()).isTrue();
    }
}