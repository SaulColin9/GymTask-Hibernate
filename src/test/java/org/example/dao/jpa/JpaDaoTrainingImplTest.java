package org.example.dao.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.assertj.core.internal.Predicates;
import org.example.matchers.TraineeMatcher;
import org.example.matchers.TrainingMatcher;
import org.example.model.*;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JpaDaoTrainingImplTest {
    @Mock
    EntityManager entityManager;
    @Mock
    EntityTransaction entityTransaction;
    @Mock
    Query query;
    @Mock
    CriteriaBuilder criteriaBuilder;
    @Mock
    CriteriaQuery<Training> criteriaQuery;
    @Mock
    Root<Training> root;
    @InjectMocks
    JpaDaoTrainingImpl jpaDaoTraining;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void givenTrainingId_TrainingShouldBeReturned() {
        // arrange
        int id = 1;
        when(entityManager.find(Training.class, 1)).thenReturn(createNewTraining());
        // act
        Optional<Training> actualResponse = jpaDaoTraining.get(id);

        // assert
        assertThat(actualResponse.get()).isNotNull();
        verify(entityManager, times(1)).find(Training.class, 1);
    }

    @Test
    void givenInvalidTrainingId_OptionalEmptyShouldBeReturned() {
        // arrange
        int id = 77;
        when(entityManager.find(Training.class, 77)).thenThrow(NullPointerException.class);
        // act
        Optional<Training> actualResponse = jpaDaoTraining.get(id);

        // assert
        assertThat(actualResponse.isEmpty()).isTrue();
        verify(entityManager, times(1)).find(Training.class, 77);
    }


    @Test
    void shouldReturnListOfTrainings() {
        // arrange
        when(entityManager.createQuery("FROM Training")).thenReturn(query);
        when(query.getResultList())
                .thenReturn(List.of(createNewTraining(), createNewTraining()));
        // act
        List<Training> actualResponse = jpaDaoTraining.getAll();
        // assert
        assertThat(actualResponse).isNotNull();
        verify(entityManager, times(1)).createQuery("FROM Training");
        verify(query, times(1)).getResultList();
    }

    @Test
    void givenValidRequest_TrainingShouldBeSaved() {
        // arrange
        Training training = createNewTraining();
        when(entityManager.getTransaction()).thenReturn(entityTransaction);
        // act
        // assert
        assertThatCode(() -> jpaDaoTraining.save(training)).doesNotThrowAnyException();
        verify(entityManager, times(1)).persist(argThat(new TrainingMatcher(training)));
    }


    @Test
    void givenValidRequest_TrainingShouldBeUpdated() {
        // arrange
        Training training = createNewTraining();
        int id = 1;
        when(entityManager.getTransaction()).thenReturn(entityTransaction);
        // act
        // assert
        assertThatCode(() -> jpaDaoTraining.update(id, training)).doesNotThrowAnyException();
        verify(entityManager, times(1)).merge(argThat(new TrainingMatcher(training)));
    }

    @Test
    void givenValidRequest_TrainingShouldBeDeleted() {
        // arrange
        Training training = createNewTraining();
        int id = 1;
        when(entityManager.getTransaction()).thenReturn(entityTransaction);
        when(entityManager.find(Training.class, 1)).thenReturn(training);
        // act
        // assert
        assertThatCode(() -> jpaDaoTraining.delete(id)).doesNotThrowAnyException();
        verify(entityManager, times(1)).remove(argThat(new TrainingMatcher(training)));
    }


    Training createNewTraining() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        TrainingType newTrainingType = new TrainingType();
        newTrainingType.setTrainingTypeName("Cardio");
        newTrainingType.setId(1);

        Training newTraining = new Training();
        newTraining.setTrainingType(newTrainingType);
        newTraining.setTrainingName("Elite");
        try {
            newTraining.setTrainingDate(sdf.parse("2003-06-05"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        newTraining.setTrainingDuration(1.0);
        newTraining.setTrainee(createNewTrainee());
        newTraining.setTrainer(createNewTrainer());
        newTraining.setId(1);

        return newTraining;
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

    Trainer createNewTrainer() {
        User newUser = new User();

        newUser.setIsActive(true);
        newUser.setFirstName("Jane");
        newUser.setLastName("Doe");
        newUser.setPassword("password");
        newUser.setUsername("Jane.Doe");
        newUser.setId(2);

        Trainer newTrainer = new Trainer();
        newTrainer.setSpecialization(1);
        newTrainer.setId(1);
        newTrainer.setUser(newUser);

        return newTrainer;
    }
}