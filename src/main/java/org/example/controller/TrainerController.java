package org.example.controller;

import org.example.controller.dto.*;
import org.example.model.Trainee;
import org.example.model.Trainer;
import org.example.model.Training;
import org.example.service.TrainerService;
import org.example.service.authentication.Credentials;
import org.example.service.authentication.CredentialsAuthenticator;
import org.example.service.serviceimpl.jpa.JpaTrainerService;
import org.example.service.serviceimpl.jpa.JpaTrainingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/trainer")
public class TrainerController {
    private JpaTrainerService trainerService;
    private JpaTrainingService trainingService;
    private CredentialsAuthenticator credentialsAuthenticator;

    @PostMapping
    public Credentials addTrainer(@RequestBody AddTrainerRequestDTO req) {
        Trainer trainer = trainerService.createTrainerProfile(req.firstName(), req.lastName(), req.specialization());
        return new Credentials(trainer.getUser().getUsername(), trainer.getUser().getPassword());
    }

    @GetMapping
    public TrainerDTO getTrainer(@RequestBody UsernameDTO req, @RequestHeader Map<String, String> headers) {
        Trainer trainer = trainerService.selectTrainerProfileByUsername(req.username());
        authorize(headers, trainer);
        List<Trainee> trainees = trainerService.selectTrainerTraineeList(trainer.getId());
        return new TrainerDTO(trainer, trainees);
    }

    @PatchMapping
    public ResponseEntity<String> updateIsActiveStatus(@RequestBody UpdateIsActiveTrainerRequestDTO req,
                                                       @RequestHeader Map<String, String> headers) {

        Trainer trainer = trainerService.selectTrainerProfileByUsername(req.username());
        authorize(headers, trainer);
        trainerService.updateTrainerActiveStatus(req.username(), req.isActive());
        return ResponseEntity.ok("OK");
    }

    @PutMapping
    public TrainerDTO updateTrainer(@RequestBody UpdateTrainerRequestDTO req, @RequestHeader Map<String, String> headers) {
        Trainer t = trainerService.selectTrainerProfileByUsername(req.username());
        authorize(headers, t);
        trainerService.
                updateTrainerProfile(t.getId(), req.firstName(), req.lastName(), req.isActive(), req.specialization());

        List<Trainee> trainers = trainerService.selectTrainerTraineeList(t.getId());
        return new TrainerDTO(t, trainers);
    }

    @GetMapping("/trainings")
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
