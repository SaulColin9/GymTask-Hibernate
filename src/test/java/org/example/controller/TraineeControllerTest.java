package org.example.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.controller.dto.*;
import org.example.entitiesFactory.EntitiesFactory;
import org.example.exception.ErrorResponse;
import org.example.model.Trainee;
import org.example.model.Trainer;
import org.example.model.Training;
import org.example.service.authentication.Credentials;
import org.example.service.authentication.CredentialsAuthenticator;
import org.example.service.serviceimpl.jpa.JpaTraineeService;
import org.example.service.serviceimpl.jpa.JpaTrainingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TraineeControllerTest {
    @Mock
    private JpaTraineeService traineeService;
    @Mock
    private JpaTrainingService trainingService;
    @Mock
    private CredentialsAuthenticator credentialsAuthenticator;
    @InjectMocks
    private TraineeController controller;

    private MockMvc mockMvc;
    private EntitiesFactory entitiesFactory;

    @BeforeEach
    void setUp() {
        entitiesFactory = new EntitiesFactory();
        MockitoAnnotations.openMocks(this);
        controller = new TraineeController();
        controller.setTrainingService(trainingService);
        controller.setTraineeService(traineeService);
        controller.setCredentialsAuthenticator(credentialsAuthenticator);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void givenValidRequest_TraineeShouldBeReturned() throws Exception {
        // arrange
        Trainee trainee = entitiesFactory.createNewTrainee();
        when(traineeService.selectTraineeProfileByUsername("John.Doe")).thenReturn(trainee);

        UsernameDTO req = new UsernameDTO("John.Doe");

        HttpHeaders headers = new HttpHeaders();
        headers.add("username", trainee.getUser().getUsername());
        headers.add("password", trainee.getUser().getPassword());

        // act
        mockMvc.perform(
                        get("/trainee")
                                .headers(headers)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertObjectToJsonString(req)))
                .andExpect(content().json(convertObjectToJsonString(new TraineeDTO(trainee, new ArrayList<>()))))
                .andExpect(status().isOk());

    }

    @Test
    void givenValidRequest_TraineeShouldBeCreated() throws Exception {
        // arrange
        Trainee trainee = entitiesFactory.createNewTrainee();
        AddTraineeRequestDTO req =
                new AddTraineeRequestDTO("John", "Doe", trainee.getDateOfBirth(), "Test Address");
        when(traineeService
                .createTraineeProfile("John", "Doe", trainee.getDateOfBirth(), "Test Address"))
                .thenReturn(trainee);

        // act
        mockMvc.perform(
                        post("/trainee")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertObjectToJsonString(req))
                ).andExpect(status().isOk())
                .andExpect(content().
                        json(convertObjectToJsonString(
                                new Credentials(
                                        trainee.getUser().getUsername(),
                                        trainee.getUser().getPassword()
                                ))));
    }

    @Test
    void givenValidRequest_TraineeStatusShouldBeUpdated() throws Exception {
        // arrange
        Trainee trainee = entitiesFactory.createNewTrainee();
        UpdateIsActiveTraineeRequestDTO req = new UpdateIsActiveTraineeRequestDTO(trainee.getUser().getUsername(), false);
        when(traineeService.selectTraineeProfileByUsername("John.Doe")).thenReturn(trainee);


        HttpHeaders headers = new HttpHeaders();
        headers.add("username", trainee.getUser().getUsername());
        headers.add("password", trainee.getUser().getPassword());

        // act
        mockMvc.perform(
                        patch("/trainee")
                                .headers(headers)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertObjectToJsonString(req)))
                .andExpect(content().string("OK"))
                .andExpect(status().isOk());

    }

    @Test
    void givenValidRequest_TraineeShouldBeUpdated() throws Exception {
        // arrange
        Trainee trainee = entitiesFactory.createNewTrainee();
        UpdateTraineeRequestDTO req =
                new UpdateTraineeRequestDTO(trainee.getUser().getUsername(), "newName",
                        "newLastName", new Date(), "address", false);
        when(traineeService.selectTraineeProfileByUsername("John.Doe")).thenReturn(trainee);

        HttpHeaders headers = new HttpHeaders();
        headers.add("username", trainee.getUser().getUsername());
        headers.add("password", trainee.getUser().getPassword());

        // act
        mockMvc.perform(
                        put("/trainee")
                                .headers(headers)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertObjectToJsonString(req))
                ).andExpect(status().isOk())
                .andExpect(content().
                        json(convertObjectToJsonString(new TraineeDTO(trainee, new ArrayList<>()))));
    }

    @Test
    void givenValidRequest_TraineeShouldBeDeleted() throws Exception {
        // arrange
        Trainee trainee = entitiesFactory.createNewTrainee();
        when(traineeService.selectTraineeProfileByUsername("John.Doe")).thenReturn(trainee);
        UsernameDTO req = new UsernameDTO(trainee.getUser().getUsername());

        HttpHeaders headers = new HttpHeaders();
        headers.add("username", trainee.getUser().getUsername());
        headers.add("password", trainee.getUser().getPassword());

        // act
        mockMvc.perform(
                        delete("/trainee")
                                .headers(headers)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertObjectToJsonString(req))
                ).andExpect(status().isOk())
                .andExpect(content().
                        string("OK"));

    }

    @Test
    void givenValidRequest_TrainersListShouldBeReturned() throws Exception {
        // arrange
        Trainee trainee = entitiesFactory.createNewTrainee();
        when(traineeService.selectTraineeProfileByUsername("John.Doe")).thenReturn(trainee);
        UsernameDTO req = new UsernameDTO(trainee.getUser().getUsername());


        List<Trainer> trainers = new ArrayList<>();
        trainers.add(entitiesFactory.createNewTrainer());
        trainers.add(entitiesFactory.createNewTrainer());
        when(traineeService.selectNotAssignedOnTraineeTrainersList(trainee.getId())).thenReturn(trainers);

        HttpHeaders headers = new HttpHeaders();
        headers.add("username", trainee.getUser().getUsername());
        headers.add("password", trainee.getUser().getPassword());

        // act
        mockMvc.perform(
                        get("/trainee/availableTrainers")
                                .headers(headers)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertObjectToJsonString(req))
                ).andExpect(status().isOk())
                .andExpect(content().
                        json(convertObjectToJsonString(trainers)));

    }

    @Test
    void givenValidRequest_TraineeTrainingsShouldBeReturned() throws Exception {
        // arrange
        Trainee trainee = entitiesFactory.createNewTrainee();
        when(traineeService.selectTraineeProfileByUsername("John.Doe")).thenReturn(trainee);
        GetTraineeTrainingsRequestDTO req =
                new GetTraineeTrainingsRequestDTO(trainee.getUser().getUsername(), new Date(), new Date(),
                        "Trainer", 2);
        List<Training> trainings = new ArrayList<>();
        trainings.add(entitiesFactory.createNewTraining());
        trainings.add(entitiesFactory.createNewTraining());
        when(trainingService
                .selectTraineeTrainings(req.username(), req.periodFrom(), req.periodTo(),
                        req.trainerName(), req.trainingType())).thenReturn(trainings);

        HttpHeaders headers = new HttpHeaders();
        headers.add("username", trainee.getUser().getUsername());
        headers.add("password", trainee.getUser().getPassword());

        // act
        mockMvc.perform(
                        get("/trainee/trainings")
                                .headers(headers)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertObjectToJsonString(req))
                ).andExpect(status().isOk())
                .andExpect(content().
                        json(convertObjectToJsonString(trainings.stream().map(GetTraineeTrainingsResponseDTO::new).toList())));


    }

    private String convertObjectToJsonString(Object object) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}