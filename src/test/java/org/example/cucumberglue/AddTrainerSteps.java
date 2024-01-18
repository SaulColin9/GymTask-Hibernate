package org.example.cucumberglue;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.controller.dto.AddTrainerRequestDTO;
import org.example.service.authentication.Credentials;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

public class AddTrainerSteps {
    @LocalServerPort
    private String port;
    private ResponseEntity<Credentials> lastResponse;
    private String token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJHZW9yZ2UuRm9yZCIsImV4cCI6MTcwNTYwOTAxMiwicCI6InNlY3JldCJ9.UtjikX52f-rLgqRZH6XX1w_PXrsKf7CiAA904mR8mOE";

    @When("the client calls \\/trainer")
    public void theClientCallsTrainer() {
        String url = "/trainer";

        AddTrainerRequestDTO req = new AddTrainerRequestDTO("Jane", "Doe", 1);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<AddTrainerRequestDTO> entity = new HttpEntity<>(req, headers);

        lastResponse = new RestTemplate().exchange("http://localhost:" + port + url, HttpMethod.POST, entity, Credentials.class);
    }

    @Then("the client receives a status code of {int}")
    public void theClientReceivesAStatusCodeOf(int statusCode) {
        System.out.println(statusCode);
    }

    @And("the trainer credentials are returned")
    public void theTrainerCredentialsAreReturned() {
        System.out.println(lastResponse.getBody().username());
    }

}
