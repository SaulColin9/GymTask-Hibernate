package org.example.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.example.model.TrainingType;
import org.example.service.TrainingTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class TrainingTypeControllerTest {

    @Mock
    private TrainingTypeService trainingTypeService;
    @Mock
    private MeterRegistry registry;
    @Mock
    private Counter requestCounter;
    @InjectMocks
    private TrainingTypeController controller;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(Counter.builder("request_counter")
                .description("Number of requests to retrieve training types")
                .register(registry)).thenReturn(requestCounter);
        TrainingTypeController trainingTypeController = new TrainingTypeController(registry);
        trainingTypeController.setTrainingTypeService(trainingTypeService);
        mockMvc = MockMvcBuilders.standaloneSetup(trainingTypeController).build();
    }

    @Test
    void givenValidRequest_TrainingTypes_ShouldBeReturned() throws Exception {
        mockMvc.perform(get("/trainingType"))
                .andExpect(status().isOk())
                .andExpect(content().json(convertObjectToJsonString(new ArrayList<>())));
    }

    private String convertObjectToJsonString(List<TrainingType> list) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}