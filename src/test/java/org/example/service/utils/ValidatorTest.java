package org.example.service.utils;

import org.example.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ValidatorTest {
    @InjectMocks
    Validator validator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void givenValidEntities_NoExceptionThrown() {
        // arrange
        Map<String, Object> entities = new HashMap<>();
        entities.put("user", createNewUser());
        entities.put("user2", createNewUser());
        // act

        // assert
        assertThatCode(() -> validator.validateEntitiesNotNull(entities)).doesNotThrowAnyException();

    }

    @Test
    void givenInvalidEntities_ExceptionThrown() {
        // arrange
        Map<String, Object> entities = new HashMap<>();
        entities.put("user", null);
        entities.put("user2", null);

        // act

        // assert
        assertThatThrownBy(() -> validator.validateEntitiesNotNull(entities))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("user2 entity was not found");

    }

    @Test
    void givenValidFields_NoExceptionThrown() {
        // arrange
        Map<String, Object> params = new HashMap<>();
        params.put("firstName", "Saul");
        params.put("lastName", "Colin");

        // act

        // assert
        assertThatCode(() -> validator.validateFieldsNotNull(params)).doesNotThrowAnyException();

    }

    @Test
    void givenValidNumField_NoExceptionThrown() {
        // arrange

        // act

        // assert
        assertThatCode(() -> validator.validatePositiveField("specialization", 1))
                .doesNotThrowAnyException();
    }

    @Test
    void givenInvalidNumField_ExceptionThrown() {
        // arrange

        // act

        // assert
        assertThatThrownBy(() -> validator.validatePositiveField("specialization", -1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("specialization argument cannot be lower or equal to zero");

    }

    private User createNewUser() {
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