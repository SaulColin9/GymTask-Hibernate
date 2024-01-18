package org.example.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.example.configuration.security.JwtIssuer;
import org.example.controller.dto.ChangeLoginRequestDTO;
import org.example.controller.dto.LoginResponseDTO;
import org.example.exception.UserAuthenticationException;
import org.example.model.Trainee;
import org.example.model.Trainer;
import org.example.service.authentication.Credentials;
import org.example.service.authentication.CredentialsAuthenticator;
import org.example.service.serviceimpl.jpa.JpaTraineeService;
import org.example.service.serviceimpl.jpa.JpaTrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@Api(produces = "application/json", value = "Operations for logging and changing users passwords")
public class LoginController {
    private CredentialsAuthenticator credentialsAuthenticator;
    private JpaTraineeService traineeService;
    private JpaTrainerService trainerService;
    private JwtIssuer jwtIssuer;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping
    @ApiOperation(value = "Validate provided credentials")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ResponseEntity.class),
            @ApiResponse(code = 403, message = "Invalid credentials")
    })
    public LoginResponseDTO login(@RequestBody Credentials req) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.username(), req.password())
        );
        String token = jwtIssuer.issue(req.username(), req.password());
        return new LoginResponseDTO(token);
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

    public void setJwtIssuer(JwtIssuer jwtIssuer) {
        this.jwtIssuer = jwtIssuer;
    }
}
