package org.example.controller;

import org.example.controller.dto.AddTrainingRequestDTO;
import org.example.service.serviceimpl.jpa.JpaTrainingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/training")
public class TrainingController {
    private JpaTrainingService trainingService;

    @PostMapping
    public ResponseEntity<String> addTraining(@RequestBody AddTrainingRequestDTO req) {
        trainingService.createTrainingProfile(req.traineeUsername(), req.trainerUsername(), req.trainingName(),
                req.trainingTypeId(), req.trainingDate(), req.duration());
        return ResponseEntity.ok("OK");
    }

    public void setTrainingService(JpaTrainingService trainingService) {
        this.trainingService = trainingService;
    }
}
