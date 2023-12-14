package org.example.service.authentication;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.example.entitiesFactory.EntitiesFactory;
import org.example.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

class JpaCredentialsAuthenticatorTest {
    @Mock
    EntityManager entityManager;
    @Mock
    TypedQuery<User> query;
    @InjectMocks
    JpaCredentialsAuthenticator credentialsAuthenticator;

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
        when(entityManager.createQuery("FROM User WHERE username = :username AND password = :password", User.class)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(user);

        // act

        // assert
        assertThatCode(() -> credentialsAuthenticator.authorize(credentials, user)).doesNotThrowAnyException();

    }

    @Test
    void givenInvalidCredentials_ExceptionThrown() {
        // arrange
        Credentials credentials = new Credentials("John.Doe", "password");
        EntitiesFactory entitiesFactory = new EntitiesFactory();
        User user = entitiesFactory.createNewUser();
        when(entityManager.createQuery("FROM User WHERE username = :username AND password = :password", User.class)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(null);

        // act

        // assert
        assertThatThrownBy(() -> credentialsAuthenticator.authorize(credentials, user));

    }
}