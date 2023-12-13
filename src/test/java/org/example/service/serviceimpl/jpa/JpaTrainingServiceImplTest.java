package org.example.service.serviceimpl.jpa;

import org.example.dao.jpa.JpaDaoTrainingImpl;
import org.example.entitiesFactory.EntitiesFactory;
import org.example.model.*;
import org.example.service.utils.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;


class JpaTrainingServiceImplTest {
    EntitiesFactory entitiesFactory;
    @Mock
    Validator<Training> validator;
    @Mock
    JpaDaoTrainingImpl daoTraining;
    @InjectMocks
    JpaTrainingServiceImpl jpaTrainingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        entitiesFactory = new EntitiesFactory();
    }

    @Test
    void givenValidRequest_TraineeTrainingsShouldBeReturned() {
        // arrange
        String username = "John.Doe";
        Integer trainingTypeId = 1;

        when(daoTraining.getTrainingsByTraineeUsername(username, trainingTypeId))
                .thenReturn(List.of(createNewTraining()));
        // act
        List<Training> actualResponse = jpaTrainingService
                .selectTraineeTrainingsByUsername(username, trainingTypeId);
        // assert
        assertThat(actualResponse).isNotNull();
        verify(daoTraining, times(1)).getTrainingsByTraineeUsername(username, trainingTypeId);

    }

    @Test
    void givenValidRequest_TrainerTrainingsShouldBeReturned() {
        // arrange
        String username = "John.Doe";
        Boolean isCompleted = true;

        when(daoTraining.getTrainingsByTrainerUsername(username, isCompleted))
                .thenReturn(List.of(createNewTraining()));
        // act
        List<Training> actualResponse = jpaTrainingService
                .selectTrainerTrainingsByUsername(username, isCompleted);
        // assert
        assertThat(actualResponse).isNotNull();
        verify(daoTraining, times(1)).getTrainingsByTrainerUsername(username, isCompleted);

    }

    Training createNewTraining() {
        return entitiesFactory.createNewTraining();
    }

}