package org.example.facade.impl;

import org.example.entitiesFactory.EntitiesFactory;
import org.example.model.Trainee;
import org.example.model.Trainer;
import org.example.model.Training;
import org.example.service.authentication.Credentials;
import org.example.service.authentication.CredentialsAuthenticator;
import org.example.service.serviceimpl.jpa.JpaTraineeService;
import org.example.service.serviceimpl.jpa.JpaTrainerService;
import org.example.service.serviceimpl.jpa.JpaTrainingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class GymFacadeImplTest {
    EntitiesFactory entitiesFactory;
    @InjectMocks
    private GymFacadeImpl gymFacade;
    @Mock
    private JpaTraineeService traineeService;
    @Mock
    private JpaTrainerService trainerService;
    @Mock
    private JpaTrainingService trainingService;
    @Mock
    private CredentialsAuthenticator credentialsAuthenticator;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        entitiesFactory = new EntitiesFactory();
        gymFacade.setCredentialsAuthenticator(credentialsAuthenticator);
    }

    @Test
    void givenValidRequest_TraineeShouldBeCreated() {
        // arrange
        String firstName = "John";
        String lastName = "Doe";
        Date dateOfBirth = new Date();
        String address = "Address";
        Trainee trainee = createNewTrainee();
        when(traineeService.createTraineeProfile(firstName, lastName, dateOfBirth, address)).thenReturn(createNewTrainee());

        // act
        Trainee actualResponse = gymFacade.addTrainee(firstName, lastName, dateOfBirth, address);

        // assert
        assertThat(actualResponse).isEqualTo(trainee);
        verify(traineeService, times(1)).createTraineeProfile(firstName, lastName, dateOfBirth, address);
    }

    @Test
    void givenValidRequest_TraineeShouldBeUpdated() {
        // arrange
        int traineeId = 1;
        String firstName = "John";
        String lastName = "Doe";
        Date dateOfBirth = new Date();
        String address = "Address";
        boolean isActive = false;
        Credentials credentials = new Credentials("John.Doe", "password");
        Trainee trainee = createNewTrainee();

        when(traineeService.selectTraineeProfile(traineeId)).thenReturn(trainee);
        when(traineeService.updateTraineeProfile(traineeId, firstName, lastName, isActive, dateOfBirth, address)).thenReturn(true);

        // act
        boolean actualResponse = gymFacade.updateTrainee(credentials, traineeId, firstName, lastName, isActive, dateOfBirth, address);

        // assert
        assertThat(actualResponse).isTrue();
        verify(traineeService, times(1)).updateTraineeProfile(traineeId, firstName, lastName, isActive, dateOfBirth, address);
        verify(credentialsAuthenticator, times(1)).authorize(credentials, trainee.getUser());

    }

    @Test
    void givenTraineeId_TraineeShouldBeDeleted() {
        // arrange
        int id = 1;
        Credentials credentials = new Credentials("John.Doe", "password");
        Trainee trainee = createNewTrainee();
        when(traineeService.selectTraineeProfile(id)).thenReturn(trainee);
        when(traineeService.deleteTraineeProfile(id)).thenReturn(true);

        // act
        boolean actualResponse = gymFacade.deleteTrainee(credentials, id);

        // assert
        assertThat(actualResponse).isTrue();
        verify(traineeService, times(1)).deleteTraineeProfile(id);
        verify(credentialsAuthenticator, times(1)).authorize(credentials, trainee.getUser());


    }

    @Test
    void givenId_TraineeShouldBeReturned() {
        // arrange
        int id = 1;
        Credentials credentials = new Credentials("John.Doe", "password");
        Trainee trainee = createNewTrainee();
        when(traineeService.selectTraineeProfile(id)).thenReturn(trainee);

        // act
        Trainee actualResponse = gymFacade.getTrainee(credentials, id);

        // assert
        assertThat(actualResponse).isNotNull();
        verify(traineeService, times(1)).selectTraineeProfile(id);
        verify(credentialsAuthenticator, times(1)).authorize(credentials, trainee.getUser());


    }

    @Test
    void givenUsername_TraineeShouldBeReturned() {
        // arrange
        String username = "John.Doe";
        Credentials credentials = new Credentials("John.Doe", "password");
        Trainee trainee = createNewTrainee();
        when(traineeService.selectTraineeProfileByUsername(username)).thenReturn(trainee);

        // act
        Trainee actualResponse = gymFacade.getTraineeByUsername(credentials, username);

        // assert
        assertThat(actualResponse).isNotNull();
        verify(traineeService, times(1)).selectTraineeProfileByUsername(username);
        verify(credentialsAuthenticator, times(1)).authorize(credentials, trainee.getUser());


    }

    @Test
    void givenValidRequest_TraineePasswordUpdated() {
        // arrange
        int id = 1;
        Credentials credentials = new Credentials("John.Doe", "password");
        Trainee trainee = createNewTrainee();
        String newPassword = "newPassword";
        when(traineeService.selectTraineeProfile(id)).thenReturn(trainee);
        when(traineeService.updateTraineePassword(id, newPassword)).thenReturn(true);

        // act
        boolean actualResponse = gymFacade.updateTraineePassword(credentials, id, newPassword);

        // assert
        assertThat(actualResponse).isTrue();
        verify(traineeService, times(1)).updateTraineePassword(id, newPassword);
        verify(credentialsAuthenticator, times(1)).authorize(credentials, trainee.getUser());


    }

    @Test
    void givenValidRequest_TraineeStatusUpdated() {
        // arrange
        int id = 1;
        boolean isActive = false;
        Credentials credentials = new Credentials("John.Doe", "password");
        Trainee trainee = createNewTrainee();
        when(traineeService.selectTraineeProfile(id)).thenReturn(trainee);
        when(traineeService.updateTraineeActiveStatus(id, isActive)).thenReturn(true);

        // act
        boolean actualResponse = gymFacade.updateActiveTraineeStatus(credentials, id, isActive);

        // assert
        assertThat(actualResponse).isTrue();
        verify(traineeService, times(1)).updateTraineeActiveStatus(id, isActive);
        verify(credentialsAuthenticator, times(1)).authorize(credentials, trainee.getUser());


    }

    @Test
    void givenValidUsername_TraineeShouldBeDeleted() {
        // arrange
        String username = "John.Doe";
        Credentials credentials = new Credentials("John.Doe", "password");
        Trainee trainee = createNewTrainee();
        when(traineeService.selectTraineeProfileByUsername(username)).thenReturn(trainee);
        when(traineeService.deleteTraineeProfileByUsername(username)).thenReturn(true);

        // act
        boolean actualResponse = gymFacade.deleteTraineeByUsername(credentials, username);

        // assert
        assertThat(actualResponse).isTrue();
        verify(traineeService, times(1)).deleteTraineeProfileByUsername(username);
        verify(credentialsAuthenticator, times(1)).authorize(credentials, trainee.getUser());


    }

    @Test
    void givenValidRequest_TrainerShouldBeCreated() {
        // arrange
        String firstName = "John";
        String lastName = "Doe";
        int specialization = 1;
        Trainer trainer = createNewTrainer();
        when(trainerService.createTrainerProfile(firstName, lastName, 1)).thenReturn(trainer);

        // act
        Trainer actualResponse = gymFacade.addTrainer(firstName, lastName, specialization);

        // assert
        assertThat(actualResponse).isEqualTo(trainer);
        verify(trainerService, times(1)).createTrainerProfile(firstName, lastName, specialization);

    }

    @Test
    void givenValidRequest_TrainerShouldBeUpdated() {
        // arrange
        int trainerId = 1;
        String firstName = "John";
        String lastName = "Doe";
        int specialization = 2;
        boolean isActive = false;
        Credentials credentials = new Credentials("John.Doe", "password");
        Trainer trainer = createNewTrainer();

        when(trainerService.selectTrainerProfile(trainerId)).thenReturn(trainer);
        when(trainerService.updateTrainerProfile(trainerId, firstName, lastName, isActive, specialization)).thenReturn(true);

        // act
        boolean actualResponse = gymFacade.updateTrainer(credentials, trainerId, firstName, lastName, isActive, specialization);

        // assert
        assertThat(actualResponse).isTrue();
        verify(trainerService, times(1)).updateTrainerProfile(trainerId, firstName, lastName, isActive, specialization);
        verify(credentialsAuthenticator, times(1)).authorize(credentials, trainer.getUser());

    }

    @Test
    void givenId_TrainerShouldBeReturned() {
        // arrange
        int id = 1;
        Credentials credentials = new Credentials("John.Doe", "password");
        Trainer trainer = createNewTrainer();
        when(trainerService.selectTrainerProfile(id)).thenReturn(trainer);

        // act
        Trainer actualResponse = gymFacade.getTrainer(credentials, id);

        // assert
        assertThat(actualResponse).isNotNull();
        verify(trainerService, times(1)).selectTrainerProfile(id);
        verify(credentialsAuthenticator, times(1)).authorize(credentials, trainer.getUser());


    }

    @Test
    void givenUsername_TrainerShouldBeReturned() {
        // arrange
        String username = "John.Doe";
        Credentials credentials = new Credentials("John.Doe", "password");
        Trainer trainer = createNewTrainer();
        when(trainerService.selectTrainerProfileByUsername(username)).thenReturn(trainer);

        // act
        Trainer actualResponse = gymFacade.getTrainerByUsername(credentials, username);

        // assert
        assertThat(actualResponse).isNotNull();
        verify(trainerService, times(1)).selectTrainerProfileByUsername(username);
        verify(credentialsAuthenticator, times(1)).authorize(credentials, trainer.getUser());


    }

    @Test
    void givenValidRequest_TrainerPasswordUpdated() {
        // arrange
        int id = 1;
        Credentials credentials = new Credentials("John.Doe", "password");
        Trainer trainer = createNewTrainer();
        String newPassword = "newPassword";
        when(trainerService.selectTrainerProfile(id)).thenReturn(trainer);
        when(trainerService.updateTrainerPassword(id, newPassword)).thenReturn(true);

        // act
        boolean actualResponse = gymFacade.updateTrainerPassword(credentials, id, newPassword);

        // assert
        assertThat(actualResponse).isTrue();
        verify(trainerService, times(1)).updateTrainerPassword(id, newPassword);
        verify(credentialsAuthenticator, times(1)).authorize(credentials, trainer.getUser());
    }

    @Test
    void givenValidRequest_TrainerStatusUpdated() {
        // arrange
        int id = 1;
        boolean isActive = false;
        Credentials credentials = new Credentials("John.Doe", "password");
        Trainer trainer = createNewTrainer();
        when(trainerService.selectTrainerProfile(id)).thenReturn(trainer);
        when(trainerService.updateTrainerActiveStatus(id, isActive)).thenReturn(true);

        // act
        boolean actualResponse = gymFacade.updateActiveTrainerStatus(credentials, id, isActive);

        // assert
        assertThat(actualResponse).isTrue();
        verify(trainerService, times(1)).updateTrainerActiveStatus(id, isActive);
        verify(credentialsAuthenticator, times(1)).authorize(credentials, trainer.getUser());


    }

    @Test
    void givenValidRequest_TrainingShouldBeCreated() {
        // arrange
        int traineeId = 1;
        int trainerId = 1;
        String trainingName = "Elite";
        int trainingTypeId = 1;
        Date trainingDate = new Date();
        double trainingDuration = 1.0;
        Training createdTraining = createNewTraining();

        when(trainingService.createTrainingProfile(traineeId, trainerId, trainingName, trainingTypeId, trainingDate, trainingDuration)).thenReturn(createdTraining);

        // act
        Training actualResponse = gymFacade.addTraining(traineeId, trainerId, trainingName, trainingTypeId, trainingDate, trainingDuration);

        // assert
        assertThat(actualResponse).isEqualTo(createdTraining);
        verify(trainingService, times(1)).createTrainingProfile(traineeId, trainerId, trainingName, trainingTypeId, trainingDate, trainingDuration);

    }

    @Test
    void givenTrainingId_TrainingShouldBeReturned() {
        // arrange
        Training testTraining = createNewTraining();
        int id = 1;

        when(trainingService.selectTrainingProfile(1)).thenReturn(testTraining);

        // act
        Training actualResponse = gymFacade.getTraining(id);

        // assert
        assertThat(actualResponse).isEqualTo(testTraining);
        verify(trainingService, times(1)).selectTrainingProfile(1);

    }

    @Test
    void givenValidTraineeUsername_TrainingsListShouldBeReturned() {
        // arrange
        String username = "John.Doe";
        when(trainingService.selectTraineeTrainings(username, null, null, null))
                .thenReturn(List.of(createNewTraining(), createNewTraining()));

        // act
        List<Training> actualResponse = gymFacade.getTraineeTrainings(username, null, null, null);

        // assert
        assertThat(actualResponse).isNotNull();
        verify(trainingService, times(1))
                .selectTraineeTrainings(username, null, null, null);

    }

    @Test
    void givenValidTraineeUsername_And_TrainingName_TrainingsListShouldBeReturned() {
        // arrange
        String username = "John.Doe";
        String trainingName = "Cardio";
        when(trainingService.selectTraineeTrainings(username, trainingName, null, null))
                .thenReturn(List.of(createNewTraining(), createNewTraining()));

        // act
        List<Training> actualResponse = gymFacade.getTraineeTrainings(username, trainingName, null, null);

        // assert
        assertThat(actualResponse).isNotNull();
        verify(trainingService, times(1))
                .selectTraineeTrainings(username, trainingName, null, null);

    }


    @Test
    void givenValidTrainerUsername_TrainingsListShouldBeReturned() {
        // arrange
        String username = "John.Doe";
        when(trainingService.selectTrainerTrainings(username, null, null))
                .thenReturn(List.of(createNewTraining(), createNewTraining()));

        // act
        List<Training> actualResponse = gymFacade.getTrainerTrainings(username, null, null);

        // assert
        assertThat(actualResponse).isNotNull();
        verify(trainingService, times(1))
                .selectTrainerTrainings(username, null, null);

    }

    @Test
    void givenValidTrainerUsername_And_TrainingName_TrainingsListShouldBeReturned() {
        // arrange
        String username = "John.Doe";
        Boolean isCompleted = true;
        when(trainingService.selectTrainerTrainings(username, isCompleted, null))
                .thenReturn(List.of(createNewTraining(), createNewTraining()));

        // act
        List<Training> actualResponse = gymFacade.getTrainerTrainings(username, isCompleted, null);

        // assert
        assertThat(actualResponse).isNotNull();
        verify(trainingService, times(1))
                .selectTrainerTrainings(username, isCompleted, null);

    }


    @Test
    void givenValidRequest_TraineeTrainersListShouldBeUpdated() {
        // arrange
        Credentials credentials = new Credentials("John.Doe", "password");
        int traineeId = 1;
        int trainerId = 1;
        Trainee trainee = createNewTrainee();
        when(traineeService.selectTraineeProfile(traineeId)).thenReturn(trainee);
        when(trainerService.updateTraineeTrainersList(traineeId, trainerId))
                .thenReturn(List.of(createNewTrainer(), createNewTrainer()));

        // act
        List<Trainer> actualResponse = gymFacade.updateTraineeTrainersList(credentials, traineeId, trainerId);

        // assert
        assertThat(actualResponse).isNotNull();
        verify(traineeService, times(1)).selectTraineeProfile(traineeId);
        verify(trainerService, times(1)).updateTraineeTrainersList(traineeId, trainerId);

    }

    @Test
    void givenValidRequest_TrainersListShouldBeReturned() {
        // arrange
        Credentials credentials = new Credentials("John.Doe", "password");
        int traineeId = 1;
        Trainee trainee = createNewTrainee();
        when(traineeService.selectTraineeProfile(traineeId)).thenReturn(trainee);
        when(traineeService.selectNotAssignedOnTraineeTrainersList(traineeId))
                .thenReturn(List.of(createNewTrainer(), createNewTrainer()));

        // act
        List<Trainer> actualResponse = gymFacade.getNotAssignedOnTraineeTrainersList(credentials, traineeId);

        // assert
        assertThat(actualResponse).isNotNull();
        verify(traineeService, times(1)).selectTraineeProfile(traineeId);
        verify(traineeService, times(1)).selectNotAssignedOnTraineeTrainersList(traineeId);

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