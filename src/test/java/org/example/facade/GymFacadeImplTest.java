//package org.example.facade;
//
//import org.example.entitiesFactory.EntitiesFactory;
//import org.example.facade.inmemory.SimpleGymFacadeImpl;
//import org.example.model.*;
//import org.example.service.serviceimpl.TraineeServiceImpl;
//import org.example.service.serviceimpl.TrainerServiceImpl;
//import org.example.service.serviceimpl.TrainingServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.Mockito.*;
//
//
//class GymFacadeImplTest {
//
//    EntitiesFactory entitiesFactory;
//    @InjectMocks
//    private SimpleGymFacadeImpl gymFacade;
//    @Mock
//    private TrainingServiceImpl trainingService;
//    @Mock
//    private TraineeServiceImpl traineeService;
//    @Mock
//    private TrainerServiceImpl trainerService;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//        entitiesFactory = new EntitiesFactory();
//    }
//
//    @Test
//    void givenValidRequest_TraineeShouldBeCreated() {
//        // arrange
//        String firstName = "John";
//        String lastName = "Doe";
//        Date dateOfBirth = new Date();
//        String address = "Address";
//        when(traineeService.createTraineeProfile(firstName, lastName, dateOfBirth, address)).thenReturn(1);
//
//        // act
//        int actualResponse = gymFacade.addTrainee(firstName, lastName, dateOfBirth, address);
//
//        // assert
//        assertThat(actualResponse).isEqualTo(1);
//        verify(traineeService, times(1)).createTraineeProfile(firstName, lastName, dateOfBirth, address);
//    }
//
//    @Test
//    void givenValidRequest_TraineeShouldBeUpdated() {
//        // arrange
//        int id = 1;
//        String firstName = "John";
//        String lastName = "Doe";
//        Date dateOfBirth = new Date();
//        String address = "Address";
//        boolean isActive = false;
//
//        when(traineeService.updateTraineeProfile(id, firstName, lastName, isActive, dateOfBirth, address)).thenReturn(true);
//        // act
//        boolean actualResponse = gymFacade.updateTrainee(id, firstName, lastName, isActive, dateOfBirth, address);
//
//        // assert
//        assertThat(actualResponse).isTrue();
//        verify(traineeService, times(1)).updateTraineeProfile(id, firstName, lastName, isActive, dateOfBirth, address);
//    }
//
//    @Test
//    void givenTraineeId_TraineeShouldBeDeleted() {
//        // arrange
//        int id = 1;
//        when(traineeService.deleteTraineeProfile(1)).thenReturn(true);
//        // act
//        boolean actualResponse = gymFacade.deleteTrainee(id);
//
//        // assert
//        assertThat(actualResponse).isTrue();
//        verify(traineeService, times(1)).deleteTraineeProfile(1);
//    }
//
//    @Test
//    void givenId_TraineeShouldBeReturned() {
//        // arrange
//        Trainee testTrainee = createNewTrainee();
//        int id = 1;
//        when(traineeService.selectTraineeProfile(1)).thenReturn(testTrainee);
//        // act
//        Trainee actualResponse = gymFacade.getTrainee(id);
//        // assert
//        assertThat(actualResponse).isEqualTo(testTrainee);
//        verify(traineeService, times(1)).selectTraineeProfile(1);
//    }
//
//    @Test
//    void givenValidRequest_TrainerShouldBeCreated() {
//        // arrange
//        String firstName = "John";
//        String lastName = "Doe";
//        int specialization = 1;
//
//        when(trainerService.createTrainerProfile(firstName, lastName, specialization)).thenReturn(1);
//        // act
//        int actualResponse = gymFacade.addTrainer(firstName, lastName, specialization);
//        // assert
//        assertThat(actualResponse).isEqualTo(1);
//        verify(trainerService, times(1)).createTrainerProfile(firstName, lastName, specialization);
//    }
//
//    @Test
//    void givenValidRequest_TrainerShouldBeUpdated() {
//        // arrange
//        int id = 1;
//        String firstName = "John";
//        String lastName = "Doe";
//        boolean isActive = false;
//        int specialization = 2;
//
//        when(trainerService.updateTrainerProfile(id, firstName, lastName, isActive, specialization)).thenReturn(true);
//        // act
//        boolean actualResponse = gymFacade.updateTrainer(id, firstName, lastName, isActive, specialization);
//
//        // assert
//        assertThat(actualResponse).isTrue();
//        verify(trainerService, times(1)).updateTrainerProfile(id, firstName, lastName, isActive, specialization);
//    }
//
//    @Test
//    void givenTrainerId_TrainerShouldBeReturned() {
//        // arrange
//        Trainer testTrainer = createNewTrainer();
//        int id = 1;
//
//        when(trainerService.selectTrainerProfile(1)).thenReturn(testTrainer);
//        // act
//        Trainer actualResponse = gymFacade.getTrainer(id);
//        // assert
//        assertThat(actualResponse).isEqualTo(testTrainer);
//        verify(trainerService, times(1)).selectTrainerProfile(1);
//    }
//
//
//    @Test
//    void givenValidRequest_TrainingShouldBeCreated() {
//        // arrange
//        int traineeId = 1;
//        int trainerId = 1;
//        String trainingName = "Elite";
//        int trainingTypeId = 1;
//        Date trainingDate = new Date();
//        double trainingDuration = 1.0;
//
//        when(trainingService.createTrainingProfile(traineeId, trainerId, trainingName, trainingTypeId, trainingDate, trainingDuration)).thenReturn(1);
//        // act
//        int actualResponse = gymFacade.addTraining(traineeId, trainerId, trainingName, trainingTypeId, trainingDate, trainingDuration);
//        // assert
//        assertThat(actualResponse).isEqualTo(1);
//        verify(trainingService, times(1)).createTrainingProfile(traineeId, trainerId, trainingName, trainingTypeId, trainingDate, trainingDuration);
//    }
//
//
//    @Test
//    void givenTrainingId_TrainingShouldBeReturned() {
//        // arrange
//        Training testTraining = createNewTraining();
//        int id = 1;
//
//        when(trainingService.selectTrainingProfile(1)).thenReturn(testTraining);
//        // act
//        Training actualResponse = gymFacade.getTraining(1);
//        // assert
//        assertThat(actualResponse).isEqualTo(testTraining);
//        verify(trainingService, times(1)).selectTrainingProfile(1);
//    }
//
//    Training createNewTraining() {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//
//        TrainingType newTrainingType = new TrainingType();
//        newTrainingType.setTrainingTypeName("Cardio");
//        newTrainingType.setId(1);
//
//
//        Training newTraining = new Training();
//        newTraining.setTrainingType(newTrainingType);
//        newTraining.setTrainingName("Elite");
//        try {
//            newTraining.setTrainingDate(sdf.parse("2003-06-05"));
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
//        newTraining.setTrainingDuration(1.0);
//        newTraining.setTrainee(createNewTrainee());
//        newTraining.setTrainer(createNewTrainer());
//        newTraining.setId(1);
//
//        return newTraining;
//    }
//
//    Trainee createNewTrainee() {
//        return entitiesFactory.createNewTrainee();
//    }
//
//    Trainer createNewTrainer() {
//        return entitiesFactory.createNewTrainer();
//    }
//
//}