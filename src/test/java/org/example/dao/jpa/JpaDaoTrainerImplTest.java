package org.example.dao.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import org.example.matchers.TraineeMatcher;
import org.example.matchers.TrainerMatcher;
import org.example.model.Trainee;
import org.example.model.Trainer;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JpaDaoTrainerImplTest {

    @Mock
    EntityManager entityManager;
    @Mock
    EntityTransaction entityTransaction;
    @Mock
    Query query;
    @InjectMocks
    JpaDaoTrainerImpl jpaDaoTrainer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void givenTrainerId_TrainerShouldBeReturned() {
        // arrange
        int id = 1;
        when(entityManager.find(Trainer.class, 1)).thenReturn(createNewTrainer());
        // act
        Optional<Trainer> actualResponse = jpaDaoTrainer.get(id);

        // assert
        assertThat(actualResponse.get()).isNotNull();
        verify(entityManager, times(1)).find(Trainer.class, 1);
    }

    @Test
    void givenInvalidTrainerId_OptionalEmptyShouldBeReturned() {
        // arrange
        int id = 77;
        when(entityManager.find(Trainer.class, 77)).thenThrow(NullPointerException.class);
        // act
        Optional<Trainer> actualResponse = jpaDaoTrainer.get(id);

        // assert
        assertThat(actualResponse.isEmpty()).isTrue();
        verify(entityManager, times(1)).find(Trainer.class, 77);
    }

    @Test
    void shouldReturnListOfTrainers() {
        // arrange
        when(entityManager.createQuery("FROM Trainer")).thenReturn(query);
        when(query.getResultList())
                .thenReturn(List.of(createNewTrainer(), createNewTrainer()));
        // act
        List<Trainer> actualResponse = jpaDaoTrainer.getAll();
        // assert
        assertThat(actualResponse).isNotNull();
        verify(entityManager, times(1)).createQuery("FROM Trainer");
        verify(query, times(1)).getResultList();
    }

    @Test
    void givenValidRequest_TrainerShouldBeSaved() {
        // arrange
        Trainer trainer = createNewTrainer();
        when(entityManager.getTransaction()).thenReturn(entityTransaction);
        // act
        // assert
        assertThatCode(() -> jpaDaoTrainer.save(trainer)).doesNotThrowAnyException();
        verify(entityManager, times(1)).persist(argThat(new TrainerMatcher(trainer)));
    }

    @Test
    void givenValidRequest_TrainerShouldBeUpdated() {
        // arrange
        Trainer trainer = createNewTrainer();
        int id = 1;
        when(entityManager.getTransaction()).thenReturn(entityTransaction);
        // act
        // assert
        assertThatCode(() -> jpaDaoTrainer.update(id, trainer)).doesNotThrowAnyException();
        verify(entityManager, times(1)).merge(argThat(new TrainerMatcher(trainer)));
    }

    @Test
    void givenValidRequest_TrainerShouldBeDeleted() {
        // arrange
        Trainer trainer = createNewTrainer();
        int id = 1;
        when(entityManager.getTransaction()).thenReturn(entityTransaction);
        when(entityManager.find(Trainer.class, 1)).thenReturn(trainer);
        when(entityManager.createQuery("DELETE Training tr WHERE tr.trainer.id = :trainer_id")).thenReturn(query);
        // act
        // assert
        assertThatCode(() -> jpaDaoTrainer.delete(id)).doesNotThrowAnyException();
        verify(entityManager, times(1)).remove(argThat(new TrainerMatcher(trainer)));
    }

    @Test
    void givenValidUsername_TrainerShouldBeReturned() {
        // arrange
        String username = "John.Doe";
        Trainer trainer = createNewTrainer();
        when(entityManager.createQuery("FROM Trainer t WHERE t.user.username = :username")).thenReturn(query);
        when(query.getSingleResult()).thenReturn(trainer);
        // act
        Optional<Trainer> actualResponse = jpaDaoTrainer.getByUsername(username);
        // assert
        assertThat(actualResponse.get()).isNotNull();
        verify(entityManager, times(1)).createQuery("FROM Trainer t WHERE t.user.username = :username");
        verify(query, times(1)).getSingleResult();
    }


    @Test
    void givenInvalidUserName_OptionalEmptyShouldBeReturned() {
        // arrange
        String username = "JohnDe";
        when(entityManager.createQuery("FROM Trainer t WHERE t.user.username = :username")).thenReturn(query);
        when(query.getSingleResult()).thenThrow(NoResultException.class);
        // act
        Optional<Trainer> actualResponse = jpaDaoTrainer.getByUsername(username);
        // assert
        assertThat(actualResponse.isEmpty()).isTrue();
        verify(entityManager, times(1)).createQuery("FROM Trainer t WHERE t.user.username = :username");
        verify(query, times(1)).getSingleResult();
    }

    @Test
    void givenValidRequest_TraineeTrainersListShouldBeUpdated() {
        // arrange
        String updateQuery = "UPDATE Training SET trainer = :newTrainer" +
                " WHERE trainee.id = :trainee_id";
        String getListQuery = "FROM Trainer trainer LEFT JOIN Training training" +
                " ON trainer.id = training.trainer.id" +
                " WHERE training.trainee.id = :trainee_id";
        Trainer trainer = createNewTrainer();
        int trainee_id = 1;

        when(entityManager.getTransaction()).thenReturn(entityTransaction);
        when(entityManager.createQuery(updateQuery)).thenReturn(query);
        when(entityManager.createQuery(getListQuery)).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(createNewTrainer(), createNewTrainer()));

        // act
        List<Trainer> actualResponse = jpaDaoTrainer.updateTraineeTrainersList(trainee_id, trainer);
        // assert
        assertThat(actualResponse).isNotNull();
        verify(entityManager, times(1)).getTransaction();
        verify(entityManager, times(1)).createQuery(updateQuery);
        verify(entityManager, times(1)).createQuery(getListQuery);
        verify(query, times(1)).getResultList();
    }

    Trainer createNewTrainer() {
        User newUser = new User();

        newUser.setIsActive(true);
        newUser.setFirstName("John");
        newUser.setLastName("Doe");
        newUser.setPassword("password");
        newUser.setUsername("John.Doe");
        newUser.setId(1);

        Trainer newTrainer = new Trainer();
        newTrainer.setSpecialization(1);
        newTrainer.setId(1);
        newTrainer.setUser(newUser);

        return newTrainer;
    }
}