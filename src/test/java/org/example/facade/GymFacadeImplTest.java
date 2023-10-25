package org.example.facade;

import org.example.model.Trainee;
import org.example.model.Trainer;
import org.example.model.Training;
import org.example.model.User;
import org.example.service.serviceImpl.TraineeServiceImpl;
import org.example.service.serviceImpl.TrainerServiceImpl;
import org.example.service.serviceImpl.TrainingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class GymFacadeImplTest {

    @InjectMocks
    private GymFacadeImpl gymFacade;
    @Mock
    private TrainingServiceImpl trainingService;
    @Mock
    private TraineeServiceImpl traineeService;
    @Mock
    private TrainerServiceImpl trainerService;
    private Trainee traineeTest;
    private Date date;
    private Training trainingTest;
    private Trainer trainerTest;
    private static final String TEST_ADDRESS = "Test Address";
    private static final String TEST_UPDATED_NAME = "Updated Name";
    private static final String TEST_UPDATED_LAST = "Updated Last";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        gymFacade = new GymFacadeImpl(traineeService, trainerService, trainingService);


        date = new Date();

        User userTest = new User("User Test", "User Test", ".");
        userTest.setId(1);

        User userTest2 = new User("User Test 2", "User Test 2", ".");
        userTest2.setId(2);


        traineeTest = new Trainee(new Date(), TEST_ADDRESS, userTest);
        traineeTest = traineeTest.setId(1);
        traineeTest.setUser(userTest);


        trainerTest = new Trainer(2, userTest2);
        trainerTest = trainerTest.setId(1);
        trainerTest.setUser(userTest2);

        trainingTest = new Training();
        trainingTest.setTrainingName("Test Training");
        trainingTest.setId(1);
        trainingTest.setTrainingDate(new Date());
        trainingTest.setTrainingDuration(1);
        trainingTest.setTrainee(traineeTest);
        trainingTest.setTrainer(trainerTest);

        when(traineeService.selectTraineeProfile(anyInt())).thenReturn(traineeTest);
    }

    @Test
    void addTrainee() {
        when(traineeService.createTraineeProfile("New Test Trainee", "Test Last", date, TEST_ADDRESS))
                .thenReturn(1);
        int newTraineeId = gymFacade.addTrainee("New Test Trainee", "Test Last", date, TEST_ADDRESS);
        assertTrue(newTraineeId > 0);
    }

    @Test
    void updateTrainee() {
        Trainee oldTrainee = traineeTest;
        traineeTest.setUser(new User(TEST_UPDATED_NAME, TEST_UPDATED_LAST, "."));
        when(traineeService.updateTraineeProfile(1, TEST_UPDATED_NAME, TEST_UPDATED_LAST, false, date, TEST_ADDRESS))
                .thenReturn(true);
        boolean updatedTrainee = gymFacade.updateTrainee(1, TEST_UPDATED_NAME, TEST_UPDATED_LAST,
                false, date, TEST_ADDRESS);

        assertTrue(updatedTrainee);
    }

    @Test
    void deleteTrainee() {
        when(traineeService.deleteTraineeProfile(anyInt())).thenReturn(true);
        assertNotNull(gymFacade.getTrainee(1));
        assertTrue(gymFacade.deleteTrainee(1));
    }

    @Test
    void getTrainee() {
        Trainee trainee = gymFacade.getTrainee(1);
        assertNotNull(trainee);
    }


    @Test
    void addTrainer() {
        when(trainerService.createTrainerProfile(anyString(), anyString(), anyInt())).thenReturn(1);
        int newTrainer = gymFacade.addTrainer("New Trainer", "New Trainer Last", 1);
        assertTrue(newTrainer > 0);
    }

    @Test
    void updateTrainer() {
        Trainer oldTrainer = trainerTest;
        oldTrainer.setUser(new User(TEST_UPDATED_NAME, TEST_UPDATED_LAST, "."));
        when(trainerService.updateTrainerProfile(1, TEST_UPDATED_NAME, TEST_UPDATED_LAST, false, 2))
                .thenReturn(true);
        boolean updatedTrainer = gymFacade
                .updateTrainer(1, TEST_UPDATED_NAME, TEST_UPDATED_LAST, false, 2);

        assertTrue(updatedTrainer);
    }

    @Test
    void getTrainer() {
        when(trainerService.selectTrainerProfile(anyInt())).thenReturn(trainerTest);
        Trainer trainer = gymFacade.getTrainer(1);
        assertNotNull(trainer);
    }


    @Test
    void addTraining() {
        when(trainingService
                .createTrainingProfile(1, 1, "New Training", 1, date, 1))
                .thenReturn(1);
        int newTraining = gymFacade
                .addTraining(1, 1, "New Training", 1, date, 1);
        assertTrue(newTraining > 0);
    }

    @Test
    void getTraining() {
        when(trainingService.selectTrainingProfile(anyInt())).thenReturn(trainingTest);
        Training training = gymFacade.getTraining(1);
        assertNotNull(training);
    }


}