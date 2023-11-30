package org.example.service.serviceImpl.jpa;

import org.example.dao.jpa.JpaDaoTrainingImpl;
import org.example.entitiesFactory.EntitiesFactory;
import org.example.model.*;
import org.example.service.utils.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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
        String trainingName = "Elite";
        Double trainingDuration = 1.0;

        when(daoTraining.getTrainingsByTraineeUsername(username, trainingName, trainingDuration))
                .thenReturn(List.of(createNewTraining()));
        // act
        List<Training> actualResponse = jpaTrainingService
                .selectTraineeTrainingsByUsername(username, trainingName, trainingDuration);
        // assert
        assertThat(actualResponse).isNotNull();
        verify(daoTraining, times(1)).getTrainingsByTraineeUsername(username, trainingName, trainingDuration);

    }

    @Test
    void givenValidRequest_TrainerTrainingsShouldBeReturned() {
        // arrange
        String username = "John.Doe";
        String trainingName = "Elite";
        Double trainingDuration = 1.0;

        when(daoTraining.getTrainingsByTrainerUsername(username, trainingName, trainingDuration))
                .thenReturn(List.of(createNewTraining()));
        // act
        List<Training> actualResponse = jpaTrainingService
                .selectTrainerTrainingsByUsername(username, trainingName, trainingDuration);
        // assert
        assertThat(actualResponse).isNotNull();
        verify(daoTraining, times(1)).getTrainingsByTrainerUsername(username, trainingName, trainingDuration);

    }

    Training createNewTraining() {
        return entitiesFactory.createNewTraining();
    }

    Trainee createNewTrainee() {
        return entitiesFactory.createNewTrainee();
    }

    Trainer createNewTrainer() {
        return entitiesFactory.createNewTrainer();
    }
}