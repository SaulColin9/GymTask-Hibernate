package org.example.cucumberglue;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.controller.dto.AddTrainerRequestDTO;
import org.example.controller.dto.LoginResponseDTO;
import org.example.service.authentication.Credentials;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LoginFeatureSteps {
    @LocalServerPort
    private String port;
    private ResponseEntity<LoginResponseDTO> lastResponse;


    @When("the client calls \\/login")
    public void theClientCallsLogin() {
        String url = "/login";

        Credentials req = new Credentials("George.Ford", "secret");
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<Credentials> entity = new HttpEntity<>(req, headers);

        lastResponse = new RestTemplate().exchange("http://localhost:" + port + url, HttpMethod.POST, entity, LoginResponseDTO.class);
    }
    @Then("the client receives a status code of {int} after login")
    public void theClientReceivesAStatusCodeOfAfterLogin(int statusCode) {
        assertThat(lastResponse.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(statusCode));
    }

    @And("the access token is returned")
    public void theAccessTokenIsReturned() {
        assertThat(lastResponse.getBody().accessToken()).isNotEmpty();
    }

}
