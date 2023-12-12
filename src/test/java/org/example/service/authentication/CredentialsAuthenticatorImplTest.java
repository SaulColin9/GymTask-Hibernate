package org.example.service.authentication;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.example.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.naming.AuthenticationException;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

import static org.mockito.Mockito.when;

class CredentialsAuthenticatorImplTest {

    @Mock
    EntityManager entityManager;
    @Mock
    Query query;
    @InjectMocks
    CredentialsAuthenticatorImpl credentialsAuthenticator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void givenValidCredentials_NoExceptionThrown() {
        // arrange
        Credentials credentials = new Credentials("John.Doe", "password");
        when(entityManager.createQuery("FROM User WHERE username = :username AND password = :password")).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(createNewUser()));
        // act

        // assert
        assertThatCode(() -> credentialsAuthenticator.authorize(credentials, createNewUser())).doesNotThrowAnyException();
    }

    @Test
    void givenInvalidCredentials_ExceptionThrown() {
        // arrange
        Credentials credentials = new Credentials("John.Doe", "password");
        when(entityManager.createQuery("FROM User WHERE username = :username AND password = :password")).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of());

        // assert
        assertThatThrownBy(() -> credentialsAuthenticator.authorize(credentials, createNewUser())).isInstanceOf(AuthenticationException.class);
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