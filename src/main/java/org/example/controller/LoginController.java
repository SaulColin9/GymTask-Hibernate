package org.example.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.example.controller.dto.ChangeLoginRequestDTO;
import org.example.exception.UserAuthenticationException;
import org.example.model.Trainee;
import org.example.model.Trainer;
import org.example.model.User;
import org.example.service.TraineeService;
import org.example.service.TrainerService;
import org.example.service.authentication.Credentials;
import org.example.service.authentication.CredentialsAuthenticator;
import org.example.service.serviceimpl.jpa.JpaTraineeService;
import org.example.service.serviceimpl.jpa.JpaTrainerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/login")
@Api(produces = "application/json", value = "Operations for logging and changing users passwords")
public class LoginController {
    private CredentialsAuthenticator credentialsAuthenticator;
    private JpaTraineeService traineeService;
    private JpaTrainerService trainerService;

    @GetMapping
    @ApiOperation(value = "Validate provided credentials")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ResponseEntity.class),
            @ApiResponse(code = 403, message = "Invalid credentials")
    })
    public ResponseEntity<String> login(@RequestBody Credentials credentials) {
        credentialsAuthenticator.login(credentials);
        return ResponseEntity.ok("OK");
    }

    @PutMapping
    @ApiOperation(value = "Change user password")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Given entity was not found"),
            @ApiResponse(code = 403, message = "Provided user is not authorized to perform this action")
    })
    public ResponseEntity<String> changeLogin(@RequestBody ChangeLoginRequestDTO req) {
        Credentials credentials = new Credentials(req.username(), req.oldPassword());


        Trainee trainee;
        try {
            trainee = traineeService.selectTraineeProfileByUsername(req.username());
        } catch (IllegalArgumentException e) {
            trainee = null;
        }
        if (trainee != null) {
            credentialsAuthenticator.authorize(credentials, trainee.getUser());
            traineeService.updateTraineePassword(trainee.getId(), req.newPassword());
            return ResponseEntity.ok("OK");
        }
        Trainer trainer;
        try {
            trainer = trainerService.selectTrainerProfileByUsername(req.username());
        } catch (IllegalArgumentException e) {
            trainer = null;
        }

        if (trainer != null) {
            credentialsAuthenticator.authorize(credentials, trainer.getUser());
            trainerService.updateTrainerPassword(trainer.getId(), req.newPassword());
            return ResponseEntity.ok("OK");
        }

        throw new IllegalArgumentException();
    }

    public void setTraineeService(JpaTraineeService traineeService) {
        this.traineeService = traineeService;
    }

    public void setTrainerService(JpaTrainerService trainerService) {
        this.trainerService = trainerService;
    }

    public void setCredentialsAuthenticator(CredentialsAuthenticator credentialsAuthenticator) {
        this.credentialsAuthenticator = credentialsAuthenticator;
    }
}
