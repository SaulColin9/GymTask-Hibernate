package org.example.cucumberglue;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.controller.dto.AddTrainerRequestDTO;
import org.example.dao.jpa.JpaDaoTrainerImpl;
import org.example.service.authentication.Credentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AddTrainerSteps {
    @LocalServerPort
    private String port;
    private ResponseEntity<Credentials> lastResponse;
    @Autowired
    private JpaDaoTrainerImpl daoTrainer;

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
        assertThat(lastResponse.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(statusCode));
    }

    @And("the trainer credentials are returned")
    public void theTrainerCredentialsAreReturned() {
        assertThat(lastResponse.getBody()).isNotNull();
        daoTrainer.delete(daoTrainer.getByUsername(lastResponse.getBody().username()).get().getId());
    }

}
