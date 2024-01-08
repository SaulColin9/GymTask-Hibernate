package org.example.controller;

import org.example.controller.dto.*;
import org.example.entitiesFactory.EntitiesFactory;
import org.example.model.Trainer;
import org.example.model.Training;
import org.example.service.authentication.Credentials;
import org.example.service.authentication.CredentialsAuthenticator;
import org.example.service.serviceimpl.jpa.JpaTrainerService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TrainerControllerTest {
    @Mock
    private JpaTrainerService trainerService;
    @Mock
    private JpaTrainingService trainingService;
    @Mock
    private CredentialsAuthenticator credentialsAuthenticator;
    @InjectMocks
    private TrainerController controller;
    private MockMvc mockMvc;
    private EntitiesFactory entitiesFactory;

    @BeforeEach
    void setUp() {
        entitiesFactory = new EntitiesFactory();
        MockitoAnnotations.openMocks(this);
        controller = new TrainerController();
        controller.setTrainingService(trainingService);
        controller.setTrainerService(trainerService);
        controller.setCredentialsAuthenticator(credentialsAuthenticator);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void givenValidRequest_TrainerShouldBeReturned() throws Exception {
        // arrange
        Trainer trainer = entitiesFactory.createNewTrainer();
        when(trainerService.selectTrainerProfileByUsername(trainer.getUser().getUsername())).thenReturn(trainer);

        UsernameDTO req = new UsernameDTO("Jane.Doe");

        HttpHeaders headers = new HttpHeaders();
        headers.add("username", trainer.getUser().getUsername());
        headers.add("password", trainer.getUser().getPassword());

        // act
        mockMvc.perform(
                        get("/trainer")
                                .headers(headers)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertObjectToJsonString(req)))
                .andExpect(content().json(convertObjectToJsonString(new TrainerDTO(trainer, new ArrayList<>()))))
                .andExpect(status().isOk());
    }

    @Test
    void givenValidRequest_TrainerShouldBeCreated() throws Exception {
        // arrange
        Trainer trainer = entitiesFactory.createNewTrainer();
        AddTrainerRequestDTO req =
                new AddTrainerRequestDTO("Jane", "Doe", trainer.getSpecialization().getId());

        when(trainerService
                .createTrainerProfile("Jane", "Doe", trainer.getSpecialization().getId()))
                .thenReturn(trainer);

        // act
        mockMvc.perform(
                        post("/trainer")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertObjectToJsonString(req))
                ).andExpect(status().isOk())
                .andExpect(content().
                        json(convertObjectToJsonString(
                                new Credentials(
                                        trainer.getUser().getUsername(),
                                        trainer.getUser().getPassword()
                                ))));

    }


    @Test
    void givenValidRequest_TrainerStatusShouldBeUpdated() throws Exception {
        // arrange
        Trainer trainer = entitiesFactory.createNewTrainer();
        UpdateIsActiveTrainerRequestDTO req = new UpdateIsActiveTrainerRequestDTO(trainer.getUser().getUsername(), false);
        when(trainerService.selectTrainerProfileByUsername("Jane.Doe")).thenReturn(trainer);


        HttpHeaders headers = new HttpHeaders();
        headers.add("username", trainer.getUser().getUsername());
        headers.add("password", trainer.getUser().getPassword());

        // act
        mockMvc.perform(
                        patch("/trainer")
                                .headers(headers)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertObjectToJsonString(req)))
                .andExpect(content().string("OK"))
                .andExpect(status().isOk());
    }

    @Test
    void givenValidRequest_TrainerShouldBeUpdated() throws Exception {
        // arrange
        Trainer trainer = entitiesFactory.createNewTrainer();
        UpdateTrainerRequestDTO req =
                new UpdateTrainerRequestDTO(trainer.getUser().getUsername(), "newName",
                        "newLastName", 2, false);
        when(trainerService.selectTrainerProfileByUsername("Jane.Doe")).thenReturn(trainer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("username", trainer.getUser().getUsername());
        headers.add("password", trainer.getUser().getPassword());

        // act
        mockMvc.perform(
                        put("/trainer")
                                .headers(headers)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertObjectToJsonString(req))
                ).andExpect(status().isOk())
                .andExpect(content().
                        json(convertObjectToJsonString(new TrainerDTO(trainer, new ArrayList<>()))));
    }

    @Test
    void givenValidRequest_TrainerTrainingsShouldBeReturned() throws Exception {
        // arrange
        Trainer trainer = entitiesFactory.createNewTrainer();
        when(trainerService.selectTrainerProfileByUsername("Jane.Doe")).thenReturn(trainer);
        GetTrainerTrainingsRequestDTO req = new GetTrainerTrainingsRequestDTO(trainer.getUser().getUsername(),
                new Date(), new Date(), "Trainee", 2);

        List<Training> trainings = new ArrayList<>();
        trainings.add(entitiesFactory.createNewTraining());
        trainings.add(entitiesFactory.createNewTraining());
        when(trainingService
                .selectTrainerTrainings(req.username(), req.periodFrom(), req.periodTo(),
                        req.traineeName(), req.trainingType()))
                .thenReturn(trainings);

        HttpHeaders headers = new HttpHeaders();
        headers.add("username", trainer.getUser().getUsername());
        headers.add("password", trainer.getUser().getPassword());

        // act
        mockMvc.perform(
                        get("/trainer/trainings")
                                .headers(headers)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertObjectToJsonString(req))
                ).andExpect(status().isOk())
                .andExpect(content().
                        json(convertObjectToJsonString(trainings.stream().map(GetTrainerTrainingsResponseDTO::new).toList())));

    }


    private String convertObjectToJsonString(Object object) {
        JsonUtils utils = new JsonUtils();
        return utils.convertObjectToJsonString(object);
    }
}