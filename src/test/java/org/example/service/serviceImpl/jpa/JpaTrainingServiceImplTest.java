package org.example.service.serviceImpl.jpa;

import org.example.dao.jpa.JpaDaoTrainingImpl;
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
    @Mock
    Validator<Training> validator;
    @Mock
    JpaDaoTrainingImpl daoTraining;
    @InjectMocks
    JpaTrainingServiceImpl jpaTrainingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
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