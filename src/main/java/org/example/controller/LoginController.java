package org.example.controller;

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
public class LoginController {
    private CredentialsAuthenticator credentialsAuthenticator;
    private JpaTraineeService traineeService;
    private JpaTrainerService trainerService;

    @GetMapping
    public ResponseEntity<String> login(@RequestBody Credentials credentials, HttpServletResponse res) {
        credentialsAuthenticator.login(credentials);
        return ResponseEntity.ok("OK");
    }

    @PutMapping
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
