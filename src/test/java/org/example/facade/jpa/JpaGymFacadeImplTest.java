package org.example.facade.jpa;

import org.example.model.*;
import org.example.service.authentication.Credentials;
import org.example.service.authentication.CredentialsAuthenticator;
import org.example.service.authentication.CredentialsAuthenticatorImpl;
import org.example.service.serviceImpl.jpa.JpaTraineeService;
import org.example.service.serviceImpl.jpa.JpaTrainerService;
import org.example.service.serviceImpl.jpa.JpaTrainingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.naming.AuthenticationException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JpaGymFacadeImplTest {
    @InjectMocks
    private JpaGymFacadeImpl jpaGymFacade;
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
        jpaGymFacade.setCredentialsAuthenticator(credentialsAuthenticator);
    }

    @Test
    void givenValidRequest_TraineeShouldBeCreated() {
        // arrange
        String firstName = "John";
        String lastName = "Doe";
        Date dateOfBirth = new Date();
        String address = "Address";
        Trainee trainee = createNewTrainee();
        when(traineeService.createTraineeProfile(firstName, lastName, dateOfBirth, address)).thenReturn(1);
        when(traineeService.selectTraineeProfile(1)).thenReturn(trainee);

        // act
        Trainee actualResponse = jpaGymFacade.addTrainee(firstName, lastName, dateOfBirth, address);

        // assert
        assertThat(actualResponse).isEqualTo(trainee);
        verify(traineeService, times(1)).createTraineeProfile(firstName, lastName, dateOfBirth, address);
        verify(traineeService, times(1)).selectTraineeProfile(1);
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
        boolean actualResponse = jpaGymFacade.updateTrainee(credentials, traineeId, firstName, lastName, isActive, dateOfBirth, address);
        // assert

        assertThat(actualResponse).isTrue();
        verify(traineeService, times(1)).updateTraineeProfile(traineeId, firstName, lastName, isActive, dateOfBirth, address);
        try {
            verify(credentialsAuthenticator, times(1)).authorize(credentials, trainee.getUser());
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }
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
        boolean actualResponse = jpaGymFacade.deleteTrainee(credentials, id);
        // assert
        assertThat(actualResponse).isTrue();
        verify(traineeService, times(1)).deleteTraineeProfile(id);
        try {
            verify(credentialsAuthenticator, times(1)).authorize(credentials, trainee.getUser());
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void givenId_TraineeShouldBeReturned() {
        // arrange
        int id = 1;
        Credentials credentials = new Credentials("John.Doe", "password");
        Trainee trainee = createNewTrainee();
        when(traineeService.selectTraineeProfile(id)).thenReturn(trainee);
        // act
        Trainee actualResponse = jpaGymFacade.getTrainee(credentials, id);
        // assert
        assertThat(actualResponse).isNotNull();
        verify(traineeService, times(1)).selectTraineeProfile(id);
        try {
            verify(credentialsAuthenticator, times(1)).authorize(credentials, trainee.getUser());
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void givenUsername_TraineeShouldBeReturned() {
        // arrange
        String username = "John.Doe";
        Credentials credentials = new Credentials("John.Doe", "password");
        Trainee trainee = createNewTrainee();
        when(traineeService.selectTraineeProfileByUsername(username)).thenReturn(trainee);
        // act
        Trainee actualResponse = jpaGymFacade.getTraineeByUsername(credentials, username);
        // assert
        assertThat(actualResponse).isNotNull();
        verify(traineeService, times(1)).selectTraineeProfileByUsername(username);
        try {
            verify(credentialsAuthenticator, times(1)).authorize(credentials, trainee.getUser());
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }
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
        boolean actualResponse = jpaGymFacade.updateTraineePassword(credentials, id, newPassword);
        // assert
        assertThat(actualResponse).isTrue();
        verify(traineeService, times(1)).updateTraineePassword(id, newPassword);
        try {
            verify(credentialsAuthenticator, times(1)).authorize(credentials, trainee.getUser());
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void givenValidRequest_TraineeStatusUpdated() {
        // arrange
        int id = 1;
        boolean isActive = false;
        Credentials credentials = new Credentials("John.Doe", "password");
        Trainee trainee = createNewTrainee();
        when(traineeService.selectTraineeProfile(id)).thenReturn(trainee);
        when(traineeService.updateTraineeTraineeStatus(id, isActive)).thenReturn(true);
        // act
        boolean actualResponse = jpaGymFacade.updateActiveTraineeStatus(credentials, id, isActive);
        // assert
        assertThat(actualResponse).isTrue();
        verify(traineeService, times(1)).updateTraineeTraineeStatus(id, isActive);
        try {
            verify(credentialsAuthenticator, times(1)).authorize(credentials, trainee.getUser());
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }

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
        boolean actualResponse = jpaGymFacade.deleteTraineeByUsername(credentials, username);
        // assert
        assertThat(actualResponse).isTrue();
        verify(traineeService, times(1)).deleteTraineeProfileByUsername(username);
        try {
            verify(credentialsAuthenticator, times(1)).authorize(credentials, trainee.getUser());
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void givenValidRequest_TrainerShouldBeCreated() {
        // arrange
        String firstName = "John";
        String lastName = "Doe";
        int specialization = 1;
        Trainer trainer = createNewTrainer();
        when(trainerService.createTrainerProfile(firstName, lastName, 1)).thenReturn(1);
        when(trainerService.selectTrainerProfile(1)).thenReturn(trainer);

        // act
        Trainer actualResponse = jpaGymFacade.addTrainer(firstName, lastName, specialization);

        // assert
        assertThat(actualResponse).isEqualTo(trainer);
        verify(trainerService, times(1)).createTrainerProfile(firstName, lastName, specialization);
        verify(trainerService, times(1)).selectTrainerProfile(1);
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
        boolean actualResponse = jpaGymFacade.updateTrainer(credentials, trainerId, firstName, lastName, isActive, specialization);
        // assert

        assertThat(actualResponse).isTrue();
        verify(trainerService, times(1)).updateTrainerProfile(trainerId, firstName, lastName, isActive, specialization);
        try {
            verify(credentialsAuthenticator, times(1)).authorize(credentials, trainer.getUser());
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void givenId_TrainerShouldBeReturned() {
        // arrange
        int id = 1;
        Credentials credentials = new Credentials("John.Doe", "password");
        Trainer trainer = createNewTrainer();
        when(trainerService.selectTrainerProfile(id)).thenReturn(trainer);
        // act
        Trainer actualResponse = jpaGymFacade.getTrainer(credentials, id);
        // assert
        assertThat(actualResponse).isNotNull();
        verify(trainerService, times(1)).selectTrainerProfile(id);
        try {
            verify(credentialsAuthenticator, times(1)).authorize(credentials, trainer.getUser());
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void givenUsername_TrainerShouldBeReturned() {
        // arrange
        String username = "John.Doe";
        Credentials credentials = new Credentials("John.Doe", "password");
        Trainer trainer = createNewTrainer();
        when(trainerService.selectTrainerProfileByUsername(username)).thenReturn(trainer);
        // act
        Trainer actualResponse = jpaGymFacade.getTrainerByUsername(credentials, username);
        // assert
        assertThat(actualResponse).isNotNull();
        verify(trainerService, times(1)).selectTrainerProfileByUsername(username);
        try {
            verify(credentialsAuthenticator, times(1)).authorize(credentials, trainer.getUser());
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }
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
        boolean actualResponse = jpaGymFacade.updateTrainerPassword(credentials, id, newPassword);
        // assert
        assertThat(actualResponse).isTrue();
        verify(trainerService, times(1)).updateTrainerPassword(id, newPassword);
        try {
            verify(credentialsAuthenticator, times(1)).authorize(credentials, trainer.getUser());
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void givenValidRequest_TrainerStatusUpdated() {
        // arrange
        int id = 1;
        boolean isActive = false;
        Credentials credentials = new Credentials("John.Doe", "password");
        Trainer trainer = createNewTrainer();
        when(trainerService.selectTrainerProfile(id)).thenReturn(trainer);
        when(trainerService.updateTrainerTraineeStatus(id, isActive)).thenReturn(true);
        // act
        boolean actualResponse = jpaGymFacade.updateActiveTrainerStatus(credentials, id, isActive);
        // assert
        assertThat(actualResponse).isTrue();
        verify(trainerService, times(1)).updateTrainerTraineeStatus(id, isActive);
        try {
            verify(credentialsAuthenticator, times(1)).authorize(credentials, trainer.getUser());
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }
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

        when(trainingService.createTrainingProfile(traineeId, trainerId, trainingName, trainingTypeId, trainingDate, trainingDuration)).thenReturn(1);
        // act
        int actualResponse = jpaGymFacade.addTraining(traineeId, trainerId, trainingName, trainingTypeId, trainingDate, trainingDuration);
        // assert
        assertThat(actualResponse).isEqualTo(1);
        verify(trainingService, times(1)).createTrainingProfile(traineeId, trainerId, trainingName, trainingTypeId, trainingDate, trainingDuration);
    }

    @Test
    void givenTrainingId_TrainingShouldBeReturned() {
        // arrange
        Training testTraining = createNewTraining();
        int id = 1;

        when(trainingService.selectTrainingProfile(1)).thenReturn(testTraining);
        // act
        Training actualResponse = jpaGymFacade.getTraining(id);
        // assert
        assertThat(actualResponse).isEqualTo(testTraining);
        verify(trainingService, times(1)).selectTrainingProfile(1);
    }

    @Test
    void givenValidTraineeUsername_TrainingsListShouldBeReturned() {
        // arrange
        String username = "John.Doe";
        String trainingName = null;
        Double trainingDuration = null;
        when(trainingService.selectTraineeTrainingsByUsername(username, trainingName, trainingDuration))
                .thenReturn(List.of(createNewTraining(), createNewTraining()));
        // act
        List<Training> actualResponse = jpaGymFacade.getTraineeTrainings(username);
        // assert
        assertThat(actualResponse).isNotNull();
        verify(trainingService, times(1))
                .selectTraineeTrainingsByUsername(username, trainingName, trainingDuration);
    }

    @Test
    void givenValidTraineeUsername_And_TrainingName_TrainingsListShouldBeReturned() {
        // arrange
        String username = "John.Doe";
        String trainingName = "Elite";
        Double trainingDuration = null;
        when(trainingService.selectTraineeTrainingsByUsername(username, trainingName, trainingDuration))
                .thenReturn(List.of(createNewTraining(), createNewTraining()));
        // act
        List<Training> actualResponse = jpaGymFacade.getTraineeTrainingsByTrainingName(username, trainingName);
        // assert
        assertThat(actualResponse).isNotNull();
        verify(trainingService, times(1))
                .selectTraineeTrainingsByUsername(username, trainingName, trainingDuration);
    }

    @Test
    void givenValidTraineeUsername_And_TrainingDuration_TrainingsListShouldBeReturned() {
        // arrange
        String username = "John.Doe";
        String trainingName = null;
        Double trainingDuration = 1.0;
        when(trainingService.selectTraineeTrainingsByUsername(username, trainingName, trainingDuration))
                .thenReturn(List.of(createNewTraining(), createNewTraining()));
        // act
        List<Training> actualResponse = jpaGymFacade.getTraineeTrainingsByTrainingDuration(username, trainingDuration);
        // assert
        assertThat(actualResponse).isNotNull();
        verify(trainingService, times(1))
                .selectTraineeTrainingsByUsername(username, trainingName, trainingDuration);
    }

    @Test
    void givenValidTrainerUsername_TrainingsListShouldBeReturned() {
        // arrange
        String username = "John.Doe";
        String trainingName = null;
        Double trainingDuration = null;
        when(trainingService.selectTrainerTrainingsByUsername(username, trainingName, trainingDuration))
                .thenReturn(List.of(createNewTraining(), createNewTraining()));
        // act
        List<Training> actualResponse = jpaGymFacade.getTrainerTrainingsByUsername(username);
        // assert
        assertThat(actualResponse).isNotNull();
        verify(trainingService, times(1))
                .selectTrainerTrainingsByUsername(username, trainingName, trainingDuration);
    }

    @Test
    void givenValidTrainerUsername_And_TrainingName_TrainingsListShouldBeReturned() {
        // arrange
        String username = "John.Doe";
        String trainingName = "Elite";
        Double trainingDuration = null;
        when(trainingService.selectTrainerTrainingsByUsername(username, trainingName, trainingDuration))
                .thenReturn(List.of(createNewTraining(), createNewTraining()));
        // act
        List<Training> actualResponse = jpaGymFacade.getTrainerTrainingsByTrainingName(username, trainingName);
        // assert
        assertThat(actualResponse).isNotNull();
        verify(trainingService, times(1))
                .selectTrainerTrainingsByUsername(username, trainingName, trainingDuration);
    }

    @Test
    void givenValidTrainerUsername_And_TrainingDuration_TrainingsListShouldBeReturned() {
        // arrange
        String username = "John.Doe";
        String trainingName = null;
        Double trainingDuration = 1.0;
        when(trainingService.selectTrainerTrainingsByUsername(username, trainingName, trainingDuration))
                .thenReturn(List.of(createNewTraining(), createNewTraining()));
        // act
        List<Training> actualResponse = jpaGymFacade.getTrainerTrainingsByTrainingDuration(username, trainingDuration);
        // assert
        assertThat(actualResponse).isNotNull();
        verify(trainingService, times(1))
                .selectTrainerTrainingsByUsername(username, trainingName, trainingDuration);
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
        List<Trainer> actualResponse = jpaGymFacade.updateTraineeTrainersList(credentials, traineeId, trainerId);

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
        List<Trainer> actualResponse = jpaGymFacade.getNotAssignedOnTraineeTrainersList(credentials, traineeId);
        // assert
        assertThat(actualResponse).isNotNull();
        verify(traineeService, times(1)).selectTraineeProfile(traineeId);
        verify(traineeService, times(1)).selectNotAssignedOnTraineeTrainersList(traineeId);

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