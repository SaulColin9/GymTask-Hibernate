package org.example.cucumberglue;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.controller.dto.AddTrainerRequestDTO;
import org.example.controller.dto.AddTrainingRequestDTO;
import org.example.controller.dto.TraineeDTO;
import org.example.model.Training;
import org.example.service.authentication.Credentials;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AddTrainingSteps {
    @LocalServerPort
    private String port;
    private ResponseEntity<Training> lastResponse;

    @When("the client calls \\/training")
    public void theClientCallsTraining() {
        String url = "/training";

        AddTrainingRequestDTO req = new AddTrainingRequestDTO(
                "Alejandro.Colin",
                "George.Ford",
                "Test",
                1,
                new Date(),
                1.4);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<AddTrainingRequestDTO> entity = new HttpEntity<>(req, headers);

        lastResponse =
                new RestTemplate().exchange("http://localhost:" + port + url, HttpMethod.POST, entity, Training.class);
    }

    @Then("the client receives a status code of {int} after training creation")
    public void theClientReceivesAStatusCodeOfAfterTrainingCreation(int statusCode) {
        assertThat(lastResponse.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(statusCode));
    }

    @And("the training is returned")
    public void theTrainingIsReturned() {
    }
}
