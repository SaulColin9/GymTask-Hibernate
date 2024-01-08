package org.example.controller;

import org.example.controller.dto.ChangeLoginRequestDTO;
import org.example.entitiesFactory.EntitiesFactory;
import org.example.model.Trainee;
import org.example.service.authentication.Credentials;
import org.example.service.authentication.CredentialsAuthenticator;
import org.example.service.serviceimpl.jpa.JpaTraineeService;
import org.example.service.serviceimpl.jpa.JpaTrainerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class LoginControllerTest {
    @Mock
    private JpaTraineeService traineeService;
    @Mock
    private JpaTrainerService trainerService;
    @Mock
    private CredentialsAuthenticator credentialsAuthenticator;
    @InjectMocks
    private LoginController controller;
    private MockMvc mockMvc;
    private EntitiesFactory entitiesFactory;

    @BeforeEach
    void setUp() {
        entitiesFactory = new EntitiesFactory();
        MockitoAnnotations.openMocks(this);
        controller = new LoginController();
        controller.setTrainerService(trainerService);
        controller.setTraineeService(traineeService);
        controller.setCredentialsAuthenticator(credentialsAuthenticator);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void givenValidRequest_UserShouldBeLogged() throws Exception {
        // arrange
        Credentials req = new Credentials("John.Doe", "password");

        // act
        mockMvc.perform(
                        get("/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertObjectToJsonString(req)))
                .andExpect(content().string("OK"))
                .andExpect(status().isOk());

    }

    @Test
    void givenValidRequest_UserPasswordShouldBeChanged() throws Exception {
        // arrange
        Trainee trainee = entitiesFactory.createNewTrainee();
        ChangeLoginRequestDTO req =
                new ChangeLoginRequestDTO("John.Doe", "password", "newPassword");

        when(traineeService.selectTraineeProfileByUsername(req.username())).thenReturn(trainee);

        // act
        mockMvc.perform(
                        put("/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertObjectToJsonString(req)))
                .andExpect(content().string("OK"))
                .andExpect(status().isOk());

    }

    private String convertObjectToJsonString(Object object) {
        JsonUtils utils = new JsonUtils();
        return utils.convertObjectToJsonString(object);
    }
}