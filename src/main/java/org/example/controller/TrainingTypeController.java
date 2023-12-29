package org.example.controller;

import org.example.model.TrainingType;
import org.example.service.TrainingTypeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/trainingType")
public class TrainingTypeController {
    private TrainingTypeService trainingTypeService;

    @GetMapping
    public List<TrainingType> getTrainingTypes() {
        return trainingTypeService.selectTrainingTypeList();
    }

    public void setTrainingTypeService(TrainingTypeService trainingTypeService) {
        this.trainingTypeService = trainingTypeService;
    }
}
