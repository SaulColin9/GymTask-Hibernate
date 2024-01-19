package org.example.cucumberglue;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.controller.dto.DeleteTrainingRequestDTO;
import org.example.controller.dto.TraineeDTO;
import org.example.model.Training;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DeleteTrainingSteps {
    @LocalServerPort
    private String port;
    private ResponseEntity<String> lastResponse;
    @Autowired
    private String token;

    @When("the client calls DELETE \\/training")
    public void theClientCallsDELETETraining() {
        String url = "/training";

        DeleteTrainingRequestDTO req = new DeleteTrainingRequestDTO(5);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", token);

        HttpEntity<DeleteTrainingRequestDTO> entity = new HttpEntity<>(req, headers);

        lastResponse = new RestTemplate().exchange("http://localhost:" + port + url, HttpMethod.DELETE, entity, String.class);

    }

    @Then("the client receives a status code of {int} after training deletion")
    public void theClientReceivesAStatusCodeOfAfterTrainingDeletion(int statusCode) {
        assertThat(lastResponse.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(statusCode));
    }
}
