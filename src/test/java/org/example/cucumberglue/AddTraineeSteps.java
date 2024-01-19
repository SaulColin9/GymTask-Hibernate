package org.example.cucumberglue;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.controller.dto.AddTraineeRequestDTO;
import org.example.dao.jpa.JpaDaoTraineeImpl;
import org.example.service.authentication.Credentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AddTraineeSteps {
    @LocalServerPort
    private String port;
    private ResponseEntity<Credentials> lastResponse;
    @Autowired
    private JpaDaoTraineeImpl daoTrainee;

    @When("the client calls \\/trainee")
    public void theClientCallsTrainee() {
        String url = "/trainee";

        AddTraineeRequestDTO req = new AddTraineeRequestDTO("Chris", "Smith", new Date(), "Address");
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<AddTraineeRequestDTO> entity = new HttpEntity<>(req, headers);

        lastResponse = new RestTemplate().exchange("http://localhost:" + port + url, HttpMethod.POST, entity, Credentials.class);
    }

    @Then("the client receives a status code of {int} after creation")
    public void theClientReceivesAStatusCode(int statusCode) {
        assertThat(lastResponse.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(statusCode));
    }

    @And("the trainee credentials are returned")
    public void theTraineeCredentialsAreReturned() {
        assertThat(lastResponse.getBody()).isNotNull();
        daoTrainee.delete(daoTrainee.getByUsername(lastResponse.getBody().username()).get().getId());
    }
}
