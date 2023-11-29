package org.example.service.serviceImpl;


import org.example.dao.Dao;
import org.example.matchers.TraineeMatcher;
import org.example.matchers.TrainerMatcher;
import org.example.matchers.TrainingMatcher;
import org.example.matchers.TrainingTypeMatcher;
import org.example.model.*;
import org.example.service.utils.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.assertj.core.api.Assertions.*;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class TrainingServiceImplTest {
    @Mock
    private Dao<Trainee> traineeDao;
    @Mock
    private Dao<Trainer> trainerDao;
    @Mock
    private Dao<TrainingType> trainingTypeDao;
    @Mock
    private Dao<Training> trainingDao;
    @Mock
    private Validator<Training> validator;
    @InjectMocks
    private TrainingServiceImpl trainingService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void givenValidRequest_TrainingShouldBeCreated() {
        // arrange
        Training testTraining = createNewTraining();


        when(traineeDao.get(1)).thenReturn(Optional.of(testTraining.getTrainee()));
        when(trainerDao.get(1)).thenReturn(Optional.of(testTraining.getTrainer()));
        when(trainingTypeDao.get(1)).thenReturn(Optional.of(testTraining.getTrainingType()));
        when(trainingDao.save(argThat(new TrainingMatcher(testTraining)))).thenReturn(testTraining);

        // act

        int actualResponse = trainingService.
                createTrainingProfile(1, 1, "Elite", 1,
                        new Date(1054789200000L), 1.0);

        // assert
        assertThat(actualResponse).isEqualTo(1);
        verify(traineeDao, times(1)).get(1);
        verify(trainerDao, times(1)).get(1);
        verify(trainingTypeDao, times(1)).get(1);
        verify(trainingDao, times(1)).save(argThat(new TrainingMatcher(testTraining)));


    }

    @Test
    void givenExistingId_TrainingShouldBeReturned() {
        // arrange
        Training testTraining = createNewTraining();

        when(trainingDao.get(1)).thenReturn(Optional.of(testTraining));

        // act
        Training actualResponse = trainingService.selectTrainingProfile(1);

        // assert
        assertThat(actualResponse).isNotNull();
        verify(trainingDao, times(1)).get(1);
    }

    @Test
    void givenInvalidRequest_ThrowsException() {
        Map<String, Object> params = new HashMap<>();
        params.put("trainingName", null);
        params.put("trainingDate", null);

        doThrow(new IllegalArgumentException()).when(validator).validateFieldsNotNull(params);
        assertThatThrownBy(() -> trainingService.
                createTrainingProfile(1, 1, null, 0, null, -1)).
                isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void givenNonExistingIds_ThrowsException() {
        Map<String, Object> entities = new HashMap<>();

        entities.put("trainee", null);
        entities.put("trainer", null);
        entities.put("trainingType", null);

        doThrow(new IllegalArgumentException()).when(validator).validateEntitiesNotNull(entities);
        when(traineeDao.get(88)).thenReturn(Optional.empty());
        when(trainerDao.get(99)).thenReturn(Optional.empty());
        when(trainingTypeDao.get(4)).thenReturn(Optional.empty());
        assertThatThrownBy(
                () ->
                        trainingService.createTrainingProfile(88, 99, "Elite", 4, new Date(), 1.0)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void givenNonExistingTrainingIdSelect_ThrowsException() {
        doThrow(new IllegalArgumentException()).when(validator).validateEntityNotNull(99, Optional.empty());
        when(trainingDao.get(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> trainingService.selectTrainingProfile(99)).
                isInstanceOf(IllegalArgumentException.class);
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