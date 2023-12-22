package org.example.dao.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import org.example.matchers.TrainingTypeMatcher;
import org.example.model.TrainingType;
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

class JpaDaoTrainingTypeImplTest {

    @Mock
    EntityManager entityManager;
    @Mock
    EntityTransaction entityTransaction;
    @Mock
    TypedQuery<TrainingType> query;
    @InjectMocks
    JpaDaoTrainingTypeImpl jpaDaoTrainingType;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void givenTrainingTypeId_TrainingTypeShouldBeReturned() {
        // arrange
        int id = 1;
        when(entityManager.find(TrainingType.class, 1)).thenReturn(createNewTrainingType());

        // act
        Optional<TrainingType> actualResponse = jpaDaoTrainingType.get(id);

        // assert
        assertThat(actualResponse.orElse(null)).isNotNull();
        verify(entityManager, times(1)).find(TrainingType.class, 1);

    }



    @Test
    void shouldReturnListOfTrainingTypes() {
        // arrange
        when(entityManager.createQuery("FROM TrainingType", TrainingType.class)).thenReturn(query);
        when(query.getResultList())
                .thenReturn(List.of(createNewTrainingType(), createNewTrainingType()));

        // act
        List<TrainingType> actualResponse = jpaDaoTrainingType.getAll();

        // assert
        assertThat(actualResponse).isNotNull();
        verify(entityManager, times(1)).createQuery("FROM TrainingType", TrainingType.class);
        verify(query, times(1)).getResultList();

    }

    @Test
    void givenValidRequest_TrainingTypeShouldBeSaved() {
        // arrange
        TrainingType trainingType = createNewTrainingType();
        when(entityManager.getTransaction()).thenReturn(entityTransaction);

        // act

        // assert
        assertThatCode(() -> jpaDaoTrainingType.save(trainingType)).doesNotThrowAnyException();
        verify(entityManager, times(1)).persist(argThat(new TrainingTypeMatcher(trainingType)));

    }

    @Test
    void givenValidRequest_TrainingTypeShouldBeUpdated() {
        // arrange
        TrainingType trainingType = createNewTrainingType();
        int id = 1;
        when(entityManager.getTransaction()).thenReturn(entityTransaction);

        // act

        // assert
        assertThatCode(() -> jpaDaoTrainingType.update(id, trainingType)).doesNotThrowAnyException();
        verify(entityManager, times(1)).merge(argThat(new TrainingTypeMatcher(trainingType)));

    }

    @Test
    void givenValidRequest_TrainingTypeShouldBeDeleted() {
        // arrange
        TrainingType trainingType = createNewTrainingType();
        int id = 1;
        when(entityManager.getTransaction()).thenReturn(entityTransaction);
        when(entityManager.find(TrainingType.class, 1)).thenReturn(trainingType);

        // act

        // assert
        assertThatCode(() -> jpaDaoTrainingType.delete(id)).doesNotThrowAnyException();
        verify(entityManager, times(1)).remove(argThat(new TrainingTypeMatcher(trainingType)));

    }

    TrainingType createNewTrainingType() {
        TrainingType trainingType = new TrainingType("TestTrainingType");
        trainingType.setId(1);
        return trainingType;
    }
}