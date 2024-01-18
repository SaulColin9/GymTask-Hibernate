package org.example.cucumberglue;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.controller.dto.*;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GetTrainerSteps {
    @LocalServerPort
    private String port;
    private ResponseEntity<TrainerDTO> lastResponse;

    private String token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJHZW9yZ2UuRm9yZCIsImV4cCI6MTcwNTY4NjY2MCwicCI6InNlY3JldCJ9.1pHQ65Zc-VL30H-hN4opAal-ng8F_7Bw4woTmvoBo3s";

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
