package org.example.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.controller.dto.AddTrainingRequestDTO;
import org.example.entitiesFactory.EntitiesFactory;
import org.example.model.Training;
import org.example.service.serviceimpl.jpa.JpaTrainingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.junit.jupiter.api.Assertions.*;

class TrainingControllerTest {
    @Mock
    private JpaTrainingService trainingService;
    @InjectMocks
    private TrainingController controller;
    private MockMvc mockMvc;
    private EntitiesFactory entitiesFactory;

    @BeforeEach
    void setUp() {
        entitiesFactory = new EntitiesFactory();
        MockitoAnnotations.openMocks(this);
        controller = new TrainingController();
        controller.setTrainingService(trainingService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void givenValidRequest_TrainingShouldBeCreated() throws Exception {
        // arrange
        Training training = entitiesFactory.createNewTraining();
        AddTrainingRequestDTO req =
                new AddTrainingRequestDTO("John.Doe", "Jane.Doe",
                        "Training Name", 1, new Date(), 2);

        // act
        mockMvc.perform(
                        post("/training")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertObjectToJsonString(req))
                ).andExpect(status().isOk())
                .andExpect(content().string("OK"));
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