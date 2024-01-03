package org.example.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.example.model.TrainingType;
import org.example.service.TrainingTypeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/trainingType")
@Api(produces = "application/json", value = "Get training types operation")
public class TrainingTypeController {
    private TrainingTypeService trainingTypeService;

    @GetMapping
    @ApiOperation(value = "Retrieve Trainee information")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = List.class),
    })
    public List<TrainingType> getTrainingTypes() {
        return trainingTypeService.selectTrainingTypeList();
    }

    public void setTrainingTypeService(TrainingTypeService trainingTypeService) {
        this.trainingTypeService = trainingTypeService;
    }
}
