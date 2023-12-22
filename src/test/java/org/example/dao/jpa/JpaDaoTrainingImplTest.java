package org.example.dao.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import org.example.entitiesFactory.EntitiesFactory;
import org.example.matchers.TrainingMatcher;
import org.example.model.Training;
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

class JpaDaoTrainingImplTest {
    EntitiesFactory entitiesFactory;
    @Mock
    EntityManager entityManager;
    @Mock
    EntityTransaction entityTransaction;
    @Mock
    TypedQuery<Training> query;
    @InjectMocks
    JpaDaoTrainingImpl jpaDaoTraining;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        entitiesFactory = new EntitiesFactory();
    }

    @Test
    void givenTrainingId_TrainingShouldBeReturned() {
        // arrange
        int id = 1;
        when(entityManager.find(Training.class, 1)).thenReturn(createNewTraining());

        // act
        Optional<Training> actualResponse = jpaDaoTraining.get(id);

        // assert
        assertThat(actualResponse.orElse(null)).isNotNull();
        verify(entityManager, times(1)).find(Training.class, 1);

    }


    @Test
    void shouldReturnListOfTrainings() {
        // arrange
        when(entityManager.createQuery("FROM Training", Training.class)).thenReturn(query);
        when(query.getResultList())
                .thenReturn(List.of(createNewTraining(), createNewTraining()));

        // act
        List<Training> actualResponse = jpaDaoTraining.getAll();

        // assert
        assertThat(actualResponse).isNotNull();
        verify(entityManager, times(1)).createQuery("FROM Training", Training.class);
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
        return entitiesFactory.createNewTraining();
    }


}