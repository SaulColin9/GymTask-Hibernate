package org.example.service.utils;

import org.example.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {


    Validator<User> validator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        validator = new Validator<>(User.class);
    }

    @Test
    void givenValidEntity_NoExceptionThrown() {
        // arrange
        Optional<User> user = Optional.of(createNewUser());

        // assert
        assertThatCode(() -> {
            validator.validateEntityNotNull(1, user);
        }).doesNotThrowAnyException();
    }

    @Test
    void givenInvalidEntity_ExceptionThrown() {
        assertThatThrownBy(() -> validator.validateEntityNotNull(1, Optional.empty()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Provided id of 1 for User entities was not found");
    }

    @Test
    void givenValidEntity_WithUsername_NoExceptionThrown() {
        // arrange
        Optional<User> user = Optional.of(createNewUser());

        // assert
        assertThatCode(() -> {
            validator.validateEntityNotNull("John.Doe", user);
        }).doesNotThrowAnyException();
    }

    @Test
    void givenInvalidEntity_WithUsername_ExceptionThrown() {
        assertThatThrownBy(() -> validator.validateEntityNotNull("John.Doe", Optional.empty()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Provided username of John.Doe for User entities was not found");
    }

    @Test
    void givenValidEntities_NoExceptionThrown() {
        Map<String, Object> entities = new HashMap<>();
        entities.put("user", createNewUser());
        entities.put("user2", createNewUser());
        assertThatCode(() -> validator.validateEntitiesNotNull(entities)).doesNotThrowAnyException();
    }

    @Test
    void givenInvalidEntities_ExceptionThrown() {
        Map<String, Object> entities = new HashMap<>();
        entities.put("user", null);
        entities.put("user2", null);
        assertThatThrownBy(() -> validator.validateEntitiesNotNull(entities))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("user2 entity was not found");
    }

    @Test
    void givenValidFields_NoExceptionThrown() {
        Map<String, Object> params = new HashMap<>();
        params.put("firstName", "Saul");
        params.put("lastName", "Colin");
        assertThatCode(() -> validator.validateFieldsNotNull(params)).doesNotThrowAnyException();
    }

    @Test
    void givenValidNumField_NoExceptionThrown() {
        assertThatCode(() -> validator.validatePositiveField("specialization", 1))
                .doesNotThrowAnyException();
    }

    @Test
    void givenInvalidNumField_ExceptionThrown(){
        assertThatThrownBy(()->validator.validatePositiveField("specialization", -1))
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