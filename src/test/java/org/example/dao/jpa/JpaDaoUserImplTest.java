package org.example.dao.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import org.example.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.*;

class JpaDaoUserImplTest {

    @Mock
    EntityManager entityManager;
    @Mock
    EntityTransaction entityTransaction;
    @Mock
    TypedQuery<User> query;
    @InjectMocks
    JpaDaoUserImpl jpaDaoUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void givenUserId_UserShouldBeReturned() {
        // arrange
        int id = 1;
        when(entityManager.find(User.class, 1)).thenReturn(createNewUser());
        // act
        Optional<User> actualResponse = jpaDaoUser.get(id);

        // assert
        assertThat(actualResponse.orElse(null)).isNotNull();
        verify(entityManager, times(1)).find(User.class, 1);
    }

    @Test
    void givenInvalidUserId_OptionalEmptyShouldBeReturned() {
        // arrange
        int id = 77;
        when(entityManager.find(User.class, 77)).thenThrow(NullPointerException.class);
        // act
        Optional<User> actualResponse = jpaDaoUser.get(id);

        // assert
        assertThat(actualResponse.isEmpty()).isTrue();
        verify(entityManager, times(1)).find(User.class, 77);
    }

    @Test
    void shouldReturnListOfUser() {
        // arrange
        when(entityManager.createQuery("FROM User", User.class)).thenReturn(query);
        when(query.getResultList())
                .thenReturn(List.of(createNewUser(), createNewUser()));
        // act
        List<User> actualResponse = jpaDaoUser.getAll();
        // assert
        assertThat(actualResponse).isNotNull();
        verify(entityManager, times(1)).createQuery("FROM User", User.class);
        verify(query, times(1)).getResultList();
    }

    @Test
    void givenValidRequest_UserShouldBeSaved() {
        // arrange
        User user = createNewUser();
        when(entityManager.getTransaction()).thenReturn(entityTransaction);
        // act
        // assert
        assertThatCode(() -> jpaDaoUser.save(user)).doesNotThrowAnyException();
        verify(entityManager, times(1)).persist(user);
    }

    @Test
    void givenValidRequest_UserShouldBeUpdated() {
        // arrange
        User user = createNewUser();
        int id = 1;
        when(entityManager.getTransaction()).thenReturn(entityTransaction);
        // act
        // assert
        assertThatCode(() -> jpaDaoUser.update(id, user)).doesNotThrowAnyException();
        verify(entityManager, times(1)).merge(user);
    }

    @Test
    void givenValidRequest_UserShouldBeDeleted() {
        // arrange
        User user = createNewUser();
        int id = 1;
        when(entityManager.getTransaction()).thenReturn(entityTransaction);
        when(entityManager.find(User.class, 1)).thenReturn(user);
        // act
        // assert
        assertThatCode(() -> jpaDaoUser.delete(id)).doesNotThrowAnyException();
        verify(entityManager, times(1)).remove(user);
    }

    User createNewUser() {
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