package org.example.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.example.configuration.security.JwtIssuer;
import org.example.controller.dto.*;
import org.example.exception.ErrorResponse;
import org.example.model.Trainee;
import org.example.model.Trainer;
import org.example.service.authentication.Credentials;
import org.example.service.authentication.CredentialsAuthenticator;
import org.example.service.serviceimpl.jpa.JpaTraineeService;
import org.example.service.serviceimpl.jpa.JpaTrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/trainee")
@Api(produces = "application/json", value = "Operations for creating, updating, retrieving and deleting Trainees")
public class TraineeController {
    private JpaTraineeService traineeService;
    private JpaTrainingService trainingService;
    private CredentialsAuthenticator credentialsAuthenticator;
    @Autowired
    private JwtIssuer jwtIssuer;

    @GetMapping("/{username}")
    @ApiOperation(value = "Retrieve Trainee information")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TraineeDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
            @ApiResponse(code = 400, message = "Given entity was not found", response = ErrorResponse.class),
    })
    public TraineeDTO getTrainee(@PathVariable("username") String username) {
        Trainee t = traineeService.selectTraineeProfileByUsername(username);
        List<Trainer> trainers = traineeService.selectTraineeTrainersList(t.getId());
        return new TraineeDTO(t, trainers);
    }

    @PostMapping
    @ApiOperation(value = "Add new Trainee to storage")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Credentials.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class)
    })
    public CredentialsResponseDTO addTrainee(@RequestBody AddTraineeRequestDTO req) {
        Trainee trainee = traineeService.
                createTraineeProfile(req.firstName(), req.lastName(), req.dateOfBirth(), req.address());
        String token = jwtIssuer.issue(trainee.getUser().getUsername(), trainee.getUser().getPassword());

        return new CredentialsResponseDTO(trainee.getUser().getUsername(), trainee.getUser().getPassword(), token);
    }

    @PatchMapping
    @ApiOperation(value = "Update Trainee is active status")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ResponseEntity.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
            @ApiResponse(code = 400, message = "Given entity was not found", response = ErrorResponse.class),
    })
    public ResponseEntity<String> updateIsActiveStatus(@RequestBody UpdateIsActiveTraineeRequestDTO req,
                                                       @RequestHeader Map<String, String> headers) {
        Trainee t = traineeService.selectTraineeProfileByUsername(req.username());
        authorize(headers, t);
        traineeService.updateTraineeActiveStatus(req.username(), req.isActive());
        return ResponseEntity.ok("OK");
    }

    @PutMapping
    @ApiOperation(value = "Update Trainee information")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TraineeDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
            @ApiResponse(code = 400, message = "Given entity was not found", response = ErrorResponse.class),
    })
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
    @ApiOperation(value = "Delete a Trainee (hard delete)")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ResponseEntity.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
            @ApiResponse(code = 400, message = "Given entity was not found", response = ErrorResponse.class),
    })
    public ResponseEntity<String> deleteTrainee(@RequestBody UsernameDTO usernameDTO, @RequestHeader Map<String, String> headers) {
        Trainee t = traineeService.selectTraineeProfileByUsername(usernameDTO.username());
        authorize(headers, t);
        traineeService.deleteTraineeProfileByUsername(usernameDTO.username());
        return ResponseEntity.ok("OK");
    }


    @GetMapping("/availableTrainers")
    @ApiOperation(value = "Get trainers not associated with specific trainee")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = List.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
            @ApiResponse(code = 400, message = "Given entity was not found", response = ErrorResponse.class),
    })
    public List<Trainer> getNotAssignedOnTraineeActiveTrainers(@RequestBody UsernameDTO req,
                                                               @RequestHeader Map<String, String> headers) {
        Trainee t = traineeService.selectTraineeProfileByUsername(req.username());
        authorize(headers, t);
        return traineeService.selectNotAssignedOnTraineeTrainersList(t.getId());
    }

    @GetMapping("/trainings")
    @ApiOperation(value = "Get trainee trainings")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = List.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
            @ApiResponse(code = 400, message = "Given entity was not found", response = ErrorResponse.class),
    })
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
