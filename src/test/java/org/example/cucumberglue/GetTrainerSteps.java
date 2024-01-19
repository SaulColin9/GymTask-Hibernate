package org.example.cucumberglue;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.controller.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GetTrainerSteps {
    @LocalServerPort
    private String port;
    private ResponseEntity<TrainerDTO> lastResponse;

    @Autowired
    private String token;

    @When("the client calls GET \\/trainer")
    public void theClientCallsGETTrainer() {
        String url = "/trainer";

        String username = "George.Ford";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        httpHeaders.add("authorization", token);

        HttpEntity<String> entity = new HttpEntity<>("body", httpHeaders);

        lastResponse = new RestTemplate().exchange("http://localhost:" + port + url + "/" + username, HttpMethod.GET, entity, TrainerDTO.class);
    }

    @Then("the client receives a status code of {int} after calling \\/trainer")
    public void theClientReceivesAStatusCodeOfAfterCallingTrainer(int statusCode) {
        assertThat(lastResponse.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(statusCode));
    }

    @And("the trainer information is returned")
    public void theTrainerInformationIsReturned() {
        assertThat(lastResponse.getBody()).isNotNull();
    }
}
