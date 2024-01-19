package org.example.cucumberglue;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.controller.dto.UpdateIsActiveTraineeRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class UpdateTraineeStatusSteps {
    @LocalServerPort
    private String port;
    private ResponseEntity<String> lastResponse;
    @Autowired
    private String token;
    @When("the client calls PUT \\/trainee\\/status")
    public void theClientCallsPUTTraineeStatus() {
        String url = "/trainee/status";

        UpdateIsActiveTraineeRequestDTO req = new UpdateIsActiveTraineeRequestDTO("Alejandro.Colin", true);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<UpdateIsActiveTraineeRequestDTO> entity = new HttpEntity<>(req, headers);

        lastResponse = new RestTemplate().exchange("http://localhost:" + port + url, HttpMethod.PUT, entity, String.class);
    }

    @Then("the client receives a status code of {int} after update")
    public void theClientReceivesAStatusCodeOfAfterUpdate(int statusCode) {
        assertThat(lastResponse.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(statusCode));

    }


}
