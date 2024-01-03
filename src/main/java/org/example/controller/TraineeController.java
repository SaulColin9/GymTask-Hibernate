package org.example.controller;

import io.swagger.annotations.Api;
import org.example.controller.dto.*;
import org.example.model.Trainee;
import org.example.model.Trainer;
import org.example.service.authentication.Credentials;
import org.example.service.authentication.CredentialsAuthenticator;
import org.example.service.serviceimpl.jpa.JpaTraineeService;
import org.example.service.serviceimpl.jpa.JpaTrainingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/trainee")
@Api(produces = "application/json", value = "Operations for creating, updating, retrieving and deleting Trainees in the application")
public class TraineeController {
    private JpaTraineeService traineeService;
    private JpaTrainingService trainingService;

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

    @PutMapping
    public TraineeDTO updateTrainee(@RequestBody UpdateTraineeRequestDTO req, @RequestHeader Map<String, String> headers) {
        Trainee t = traineeService.selectTraineeProfileByUsername(req.username());
        authorize(headers, t);
        traineeService.
                updateTraineeProfile(t.getId(), req.firstName(), req.lastName(),
                        req.isActive(), req.dateOfBirth(), req.address());

        List<Trainer> trainers = traineeService.selectTraineeTrainersList(t.getId());
        return new TraineeDTO(t, trainers);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteTrainee(@RequestBody UsernameDTO usernameDTO, @RequestHeader Map<String, String> headers) {
        Trainee t = traineeService.selectTraineeProfileByUsername(usernameDTO.username());
        authorize(headers, t);
        traineeService.deleteTraineeProfileByUsername(usernameDTO.username());
        return ResponseEntity.ok("OK");
    }


    @GetMapping("/availableTrainers")
    public List<Trainer> getNotAssignedOnTraineeActiveTrainers(@RequestBody UsernameDTO req,
                                                               @RequestHeader Map<String, String> headers) {
        Trainee t = traineeService.selectTraineeProfileByUsername(req.username());
        authorize(headers, t);
        return traineeService.selectNotAssignedOnTraineeTrainersList(t.getId());
    }

    @GetMapping("/trainings")
    public List<GetTraineeTrainingsResponseDTO> getTraineeTrainings(@RequestBody GetTraineeTrainingsRequestDTO req,
                                                                    @RequestHeader Map<String, String> headers) {
        Trainee t = traineeService.selectTraineeProfileByUsername(req.username());
        authorize(headers, t);
        return trainingService.
                selectTraineeTrainings(req.username(), req.periodFrom(),
                        req.periodTo(), req.trainerName(), req.trainingType()).stream().
                map(GetTraineeTrainingsResponseDTO::new).toList();
    }

    private void authorize(Map<String, String> headers, Trainee t) {
        credentialsAuthenticator.authorize(new Credentials(headers.get("username"), headers.get("password")), t.getUser());
    }

    public void setTrainingService(JpaTrainingService trainingService) {
        this.trainingService = trainingService;
    }

    public void setTraineeService(JpaTraineeService traineeService) {
        this.traineeService = traineeService;
    }

    public void setCredentialsAuthenticator(CredentialsAuthenticator credentialsAuthenticator) {
        this.credentialsAuthenticator = credentialsAuthenticator;
    }
}
