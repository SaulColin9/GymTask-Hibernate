package org.example.controller;

import org.example.controller.dto.AddTraineeRequestDTO;
import org.example.controller.dto.TraineeDTO;
import org.example.controller.dto.UpdateIsActiveTraineeRequestDTO;
import org.example.controller.dto.UsernameDTO;
import org.example.model.Trainee;
import org.example.model.Trainer;
import org.example.service.authentication.Credentials;
import org.example.service.authentication.CredentialsAuthenticator;
import org.example.service.serviceimpl.jpa.JpaTraineeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/trainee")
public class TraineeController {
    private JpaTraineeService traineeService;

    private CredentialsAuthenticator credentialsAuthenticator;

    @GetMapping
    public TraineeDTO getTrainee(@RequestBody UsernameDTO req, @RequestHeader Map<String, String> headers) {
        Trainee t = traineeService.selectTraineeProfileByUsername(req.username());
        authorize(headers, t);
        List<Trainer> trainers = traineeService.selectTraineeTrainersList(t.getId());
        return new TraineeDTO(t, trainers);
    }

    @PostMapping
    public Credentials addTrainee(@RequestBody AddTraineeRequestDTO req) {
        Trainee trainee = traineeService.
                createTraineeProfile(req.firstName(), req.lastName(), req.dateOfBirth(), req.address());
        return new Credentials(trainee.getUser().getUsername(), trainee.getUser().getPassword());
    }

    @PatchMapping
    public ResponseEntity<String> updateIsActiveStatus(@RequestBody UpdateIsActiveTraineeRequestDTO req,
                                                       @RequestHeader Map<String, String> headers) {
        Trainee t = traineeService.selectTraineeProfileByUsername(req.username());
        authorize(headers, t);
        traineeService.updateTraineeActiveStatus(req.username(), req.isActive());
        return ResponseEntity.ok("OK");
    }

    private void authorize(Map<String, String> headers, Trainee t) {
        credentialsAuthenticator.authorize(new Credentials(headers.get("username"), headers.get("password")), t.getUser());
    }

    public void setTraineeService(JpaTraineeService traineeService) {
        this.traineeService = traineeService;
    }

    public void setCredentialsAuthenticator(CredentialsAuthenticator credentialsAuthenticator) {
        this.credentialsAuthenticator = credentialsAuthenticator;
    }
}
