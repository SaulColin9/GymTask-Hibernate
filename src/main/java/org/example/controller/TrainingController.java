package org.example.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.example.controller.dto.AddTrainingRequestDTO;
import org.example.exception.ErrorResponse;
import org.example.service.serviceimpl.jpa.JpaTrainingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/training")
@Api(produces = "application/json", value = "Adding new Trainings")
public class TrainingController {
    private JpaTrainingService trainingService;

    @PostMapping
    @ApiOperation(value = "Add new training to storage")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ResponseEntity.class),
            @ApiResponse(code = 400, message = "Given entity was not found", response = ErrorResponse.class),
    })
    public ResponseEntity<String> addTraining(@RequestBody AddTrainingRequestDTO req) {
        trainingService.createTrainingProfile(req.traineeUsername(), req.trainerUsername(), req.trainingName(),
                req.trainingTypeId(), req.trainingDate(), req.duration());
        return ResponseEntity.ok("OK");
    }

    public void setTrainingService(JpaTrainingService trainingService) {
        this.trainingService = trainingService;
    }
}
