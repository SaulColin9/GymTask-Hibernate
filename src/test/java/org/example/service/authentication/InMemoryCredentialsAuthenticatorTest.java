package org.example.service.authentication;

import org.example.entitiesFactory.EntitiesFactory;
import org.example.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class InMemoryCredentialsAuthenticatorTest {
    @Mock
    private Map<Integer, User> storageEntities;

    @InjectMocks
    private InMemoryCredentialsAuthenticator credentialsAuthenticator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void givenValidCredentials_NoExceptionThrown() {
        // arrange
        Credentials credentials = new Credentials("John.Doe", "password");
        EntitiesFactory entitiesFactory = new EntitiesFactory();
        User user = entitiesFactory.createNewUser();
        when(storageEntities.values()).thenReturn(List.of(entitiesFactory.createNewUser(), entitiesFactory.createNewUser()));

        // act
        // assert
        assertThatCode(() -> credentialsAuthenticator.authorize(credentials, user)).doesNotThrowAnyException();
    }

    @Test
    void givenInvalidCredentials_ExceptionThrown() {
        // arrange
        EntitiesFactory entitiesFactory = new EntitiesFactory();
        Credentials credentials = new Credentials("John.Doe", "wrong");
        User user = entitiesFactory.createNewUser();
        when(storageEntities.values()).thenReturn(List.of(entitiesFactory.createNewUser(), entitiesFactory.createNewUser()));

        // act
        // assert
        assertThatThrownBy(() -> credentialsAuthenticator.authorize(credentials, user));
    }
}