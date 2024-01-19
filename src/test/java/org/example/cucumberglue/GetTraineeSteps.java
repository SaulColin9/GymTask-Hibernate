package org.example.cucumberglue;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.controller.dto.LoginResponseDTO;
import org.example.controller.dto.TraineeDTO;
import org.example.controller.dto.UsernameDTO;
import org.example.service.authentication.Credentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GetTraineeSteps {
    @LocalServerPort
    private String port;
    private ResponseEntity<TraineeDTO> lastResponse;

    @Autowired
    private String token;

    @When("the client calls GET \\/trainee")
    public void theClientCallsTrainee() {
        String url = "/trainee";

        String username = "Alejandro.Colin";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        httpHeaders.add("authorization", token);

        HttpEntity<String> entity = new HttpEntity<>("body", httpHeaders);

        lastResponse = new RestTemplate().exchange("http://localhost:" + port + url + "/" + username, HttpMethod.GET, entity, TraineeDTO.class);
    }

    @Then("the client receives a status code of {int} after calling \\/trainee")
    public void theClientReceivesAStatusCodeOfAfterCallingTrainee(int statusCode) {
        assertThat(lastResponse.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(statusCode));
    }

    @And("the trainee information is returned")
    public void theTraineeInformationIsReturned() {
        assertThat(lastResponse.getBody()).isNotNull();
    }
}
