package org.example.service.serviceimpl;


import org.example.dao.Dao;
import org.example.entitiesFactory.EntitiesFactory;
import org.example.matchers.TrainingMatcher;
import org.example.model.Trainee;
import org.example.model.Trainer;
import org.example.model.Training;
import org.example.model.TrainingType;
import org.example.service.utils.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;


class TrainingServiceImplTest {
    EntitiesFactory entitiesFactory;
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
        entitiesFactory = new EntitiesFactory();
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
        doThrow(new IllegalArgumentException()).when(validator).validateEntityNotNull(99, null);
        when(trainingDao.get(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> trainingService.selectTrainingProfile(99)).
                isInstanceOf(IllegalArgumentException.class);
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