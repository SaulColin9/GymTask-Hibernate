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
import org.example.service.serviceimpl.jpa.JpaTrainerService;
import org.example.service.serviceimpl.jpa.JpaTrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/trainer")
@Api(produces = "application/json", value = "Operations for creating, updating and retrieving Trainers")
public class TrainerController {
    private JpaTrainerService trainerService;
    private JpaTrainingService trainingService;
    private CredentialsAuthenticator credentialsAuthenticator;
    @Autowired
    private JwtIssuer jwtIssuer;

    @PostMapping
    @ApiOperation(value = "Add new Trainer to storage")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Credentials.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class)
    })
    public CredentialsResponseDTO addTrainer(@RequestBody AddTrainerRequestDTO req) {
        Trainer trainer = trainerService.createTrainerProfile(req.firstName(), req.lastName(), req.specialization());
        String token = jwtIssuer.issue(trainer.getUser().getUsername(), trainer.getUser().getPassword());

        return new CredentialsResponseDTO(trainer.getUser().getUsername(), trainer.getUser().getPassword(), token);
    }

    @GetMapping("/{username}")
    @ApiOperation(value = "Retrieve Trainer information")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TrainerDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
            @ApiResponse(code = 400, message = "Given entity was not found", response = ErrorResponse.class),
    })
    public TrainerDTO getTrainer(@PathVariable("username") String username) {
        Trainer trainer = trainerService.selectTrainerProfileByUsername(username);
        List<Trainee> trainees = trainerService.selectTrainerTraineeList(trainer.getId());
        return new TrainerDTO(trainer, trainees);
    }

    @PutMapping("/status")
    @ApiOperation(value = "Update Trainer is active status")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ResponseEntity.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
            @ApiResponse(code = 400, message = "Given entity was not found", response = ErrorResponse.class),
    })
    public ResponseEntity<String> updateIsActiveStatus(@RequestBody UpdateIsActiveTrainerRequestDTO req) {
        Trainer trainer = trainerService.selectTrainerProfileByUsername(req.username());
        trainerService.updateTrainerActiveStatus(req.username(), req.isActive());
        return ResponseEntity.ok("OK");
    }

    @PutMapping
    @ApiOperation(value = "Update Trainer information")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TrainerDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
            @ApiResponse(code = 400, message = "Given entity was not found", response = ErrorResponse.class),
    })
    public TrainerDTO updateTrainer(@RequestBody UpdateTrainerRequestDTO req, @RequestHeader Map<String, String> headers) {
        Trainer t = trainerService.selectTrainerProfileByUsername(req.username());
        authorize(headers, t);
        trainerService.
                updateTrainerProfile(t.getId(), req.firstName(), req.lastName(), req.isActive(), req.specialization());

        List<Trainee> trainers = trainerService.selectTrainerTraineeList(t.getId());
        return new TrainerDTO(t, trainers);
    }

    @GetMapping("/trainings")
    @ApiOperation(value = "Get trainer trainings")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = List.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
            @ApiResponse(code = 400, message = "Given entity was not found", response = ErrorResponse.class),
    })
    public List<GetTrainerTrainingsResponseDTO> getTrainerTrainings(@RequestBody GetTrainerTrainingsRequestDTO req,
                                                                    @RequestHeader Map<String, String> headers) {
        Trainer t = trainerService.selectTrainerProfileByUsername(req.username());
        authorize(headers, t);
        return trainingService.
                selectTrainerTrainings(req.username(), req.periodFrom(),
                        req.periodTo(), req.traineeName(), req.trainingType()).stream().
                map(GetTrainerTrainingsResponseDTO::new).toList();
    }


    private void authorize(Map<String, String> headers, Trainer t) {
        credentialsAuthenticator.authorize(new Credentials(headers.get("username"), headers.get("password")), t.getUser());
    }

    public void setTrainingService(JpaTrainingService trainingService) {
        this.trainingService = trainingService;
    }

    public void setTrainerService(JpaTrainerService trainerService) {
        this.trainerService = trainerService;
    }

    public void setCredentialsAuthenticator(CredentialsAuthenticator credentialsAuthenticator) {
        this.credentialsAuthenticator = credentialsAuthenticator;
    }
}
