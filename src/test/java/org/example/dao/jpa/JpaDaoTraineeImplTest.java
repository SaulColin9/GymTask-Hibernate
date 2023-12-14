package org.example.dao.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.example.matchers.TraineeMatcher;
import org.example.model.Trainee;
import org.example.model.Trainer;
import org.example.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.*;


class JpaDaoTraineeImplTest {

    @Mock
    EntityManager entityManager;
    @Mock
    EntityTransaction entityTransaction;
    @Mock
    TypedQuery<Trainee> query;
    @Mock
    TypedQuery<Trainer> queryTrainer;
    @InjectMocks
    JpaDaoTraineeImpl jpaDaoTrainee;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void givenTraineeId_TraineeShouldBeReturned() {
        // arrange
        int id = 1;
        when(entityManager.find(Trainee.class, 1)).thenReturn(createNewTrainee());

        // act
        Optional<Trainee> actualResponse = jpaDaoTrainee.get(id);

        // assert
        assertThat(actualResponse.orElse(null)).isNotNull();
        verify(entityManager, times(1)).find(Trainee.class, 1);

    }

    @Test
    void givenInvalidTraineeId_OptionalEmptyShouldBeReturned() {
        // arrange
        int id = 77;
        when(entityManager.find(Trainee.class, 77)).thenThrow(NullPointerException.class);

        // act
        Optional<Trainee> actualResponse = jpaDaoTrainee.get(id);

        // assert
        assertThat(actualResponse.isEmpty()).isTrue();
        verify(entityManager, times(1)).find(Trainee.class, 77);

    }

    @Test
    void shouldReturnListOfTrainees() {
        // arrange
        when(entityManager.createQuery("FROM Trainee", Trainee.class)).thenReturn(query);
        when(query.getResultList())
                .thenReturn(List.of(createNewTrainee(), createNewTrainee()));

        // act
        List<Trainee> actualResponse = jpaDaoTrainee.getAll();

        // assert
        assertThat(actualResponse).isNotNull();
        verify(entityManager, times(1)).createQuery("FROM Trainee", Trainee.class);
        verify(query, times(1)).getResultList();

    }

    @Test
    void givenValidRequest_TraineeShouldBeSaved() {
        // arrange
        Trainee trainee = createNewTrainee();
        when(entityManager.getTransaction()).thenReturn(entityTransaction);

        // act

        // assert
        assertThatCode(() -> jpaDaoTrainee.save(trainee)).doesNotThrowAnyException();
        verify(entityManager, times(1)).persist(argThat(new TraineeMatcher(trainee)));

    }

    @Test
    void givenValidRequest_TraineeShouldBeUpdated() {
        // arrange
        Trainee trainee = createNewTrainee();
        int id = 1;
        when(entityManager.getTransaction()).thenReturn(entityTransaction);

        // act

        // assert
        assertThatCode(() -> jpaDaoTrainee.update(id, trainee)).doesNotThrowAnyException();
        verify(entityManager, times(1)).merge(argThat(new TraineeMatcher(trainee)));

    }

    @Test
    void givenValidRequest_TraineeShouldBeDeleted() {
        // arrange
        Trainee trainee = createNewTrainee();
        int id = 1;
        when(entityManager.getTransaction()).thenReturn(entityTransaction);
        when(entityManager.find(Trainee.class, 1)).thenReturn(trainee);
        when(entityManager.createQuery("DELETE Training tr WHERE tr.trainee.id = :trainee_id")).thenReturn(query);

        // act

        // assert
        assertThatCode(() -> jpaDaoTrainee.delete(id)).doesNotThrowAnyException();
        verify(entityManager, times(1)).remove(argThat(new TraineeMatcher(trainee)));

    }

    @Test
    void givenValidUsername_TraineeShouldBeReturned() {
        // arrange
        String username = "John.Doe";
        Trainee trainee = createNewTrainee();
        when(entityManager.createQuery("FROM Trainee t WHERE t.user.username = :username")).thenReturn(query);
        when(query.getSingleResult()).thenReturn(trainee);

        // act
        Optional<Trainee> actualResponse = jpaDaoTrainee.getByUsername(username);

        // assert
        assertThat(actualResponse.orElse(null)).isNotNull();
        verify(entityManager, times(1)).createQuery("FROM Trainee t WHERE t.user.username = :username");
        verify(query, times(1)).getSingleResult();

    }

    @Test
    void givenInvalidUserName_OptionalEmptyShouldBeReturned() {
        // arrange
        String username = "JohnDe";
        when(entityManager.createQuery("FROM Trainee t WHERE t.user.username = :username")).thenReturn(query);
        when(query.getSingleResult()).thenThrow(NoResultException.class);

        // act
        Optional<Trainee> actualResponse = jpaDaoTrainee.getByUsername(username);

        // assert
        assertThat(actualResponse.isEmpty()).isTrue();
        verify(entityManager, times(1)).createQuery("FROM Trainee t WHERE t.user.username = :username");
        verify(query, times(1)).getSingleResult();

    }

    @Test
    void givenValidUsername_TraineeShouldBeDeleted() {
        // arrange
        String username = "John.Doe";
        Trainee trainee = createNewTrainee();

        when(entityManager.createQuery("FROM Trainee t WHERE t.user.username = :username")).thenReturn(query);
        when(entityManager.createQuery("DELETE Training tr WHERE tr.trainee.id = :trainee_id")).thenReturn(query);
        when(query.getSingleResult()).thenReturn(trainee);
        when(entityManager.getTransaction()).thenReturn(entityTransaction);

        // act
        Optional<Trainee> actualResponse = jpaDaoTrainee.deleteByUsername(username);

        // assert
        assertThat(actualResponse.orElse(null)).isNotNull();
        verify(entityManager, times(1)).createQuery("FROM Trainee t WHERE t.user.username = :username");
        verify(entityManager, times(1)).createQuery("DELETE Training tr WHERE tr.trainee.id = :trainee_id");
        verify(query, times(1)).executeUpdate();

    }

    @Test
    void givenValidRequest_TrainerListShouldBeReturned() {
        // arrange
        String selectQuery = "FROM Trainer trainer LEFT JOIN Training training" +
                " ON trainer.id = training.trainer.id " +
                "WHERE trainer.user.isActive = true AND (training.trainee.id != :trainee_id OR training.trainee.id IS NULL)";
        Trainee trainee = createNewTrainee();
        when(entityManager.createQuery(selectQuery, Trainer.class)).thenReturn(queryTrainer);
        when(query.getResultList()).thenReturn(List.of(createNewTrainee(), createNewTrainee()));

        // act
        List<Trainer> actualResponse = jpaDaoTrainee.getNotAssignedOnTraineeTrainersList(trainee);

        // assert
        assertThat(actualResponse).isNotNull();
        verify(entityManager, times(1)).createQuery(selectQuery, Trainer.class);
        verify(queryTrainer, times(1)).getResultList();

    }


    Trainee createNewTrainee() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        User newUser = new User();

        newUser.setIsActive(true);
        newUser.setFirstName("John");
        newUser.setLastName("Doe");
        newUser.setPassword("password");
        newUser.setUsername("John.Doe");
        newUser.setId(1);

        Trainee newTrainee = new Trainee();
        newTrainee.setAddress("Test Address");
        try {
            newTrainee.setDateOfBirth(sdf.parse("2003-06-05"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        newTrainee.setUser(newUser);
        newTrainee.setId(1);

        return newTrainee;
    }


}