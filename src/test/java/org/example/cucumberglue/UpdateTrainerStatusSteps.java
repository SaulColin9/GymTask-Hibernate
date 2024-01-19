package org.example.cucumberglue;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.controller.dto.UpdateIsActiveTraineeRequestDTO;
import org.example.controller.dto.UpdateIsActiveTrainerRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UpdateTrainerStatusSteps {
    @LocalServerPort
    private String port;
    private ResponseEntity<String> lastResponse;
    @Autowired
    private String token;
    @When("the client calls PUT \\/trainer\\/status")
    public void theClientCallsPUTTrainerStatus() {
        String url = "/trainer/status";

        UpdateIsActiveTrainerRequestDTO req = new UpdateIsActiveTrainerRequestDTO("George.Ford", true);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<UpdateIsActiveTrainerRequestDTO> entity = new HttpEntity<>(req, headers);

        lastResponse = new RestTemplate().exchange("http://localhost:" + port + url, HttpMethod.PUT, entity, String.class);
    }

    @Then("the client receives a status code of {int} after trainer update")
    public void theClientReceivesAStatusCodeOfAfterTrainerUpdate(int statusCode) {
        assertThat(lastResponse.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(statusCode));
    }
}
