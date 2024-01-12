package org.example.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.example.controller.dto.AddTrainingRequestDTO;
import org.example.controller.dto.DeleteTrainingRequestDTO;
import org.example.controller.dto.DeleteTrainingResponseDTO;
import org.example.controller.dto.TrainingWorkloadResponseDTO;
import org.example.exception.ErrorResponse;
import org.example.service.serviceimpl.jpa.JpaTrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/training")
@Api(produces = "application/json", value = "Adding new Trainings")
public class TrainingController {
    private JpaTrainingService trainingService;
    private static final String MICROSERVICE_URL = "http://localhost:8085";
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private CircuitBreakerFactory circuitBreakerFactory;

    @PostMapping
    @ApiOperation(value = "Add new training to storage")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ResponseEntity.class),
            @ApiResponse(code = 400, message = "Given entity was not found", response = ErrorResponse.class),
    })
    public TrainingWorkloadResponseDTO addTraining(@RequestBody AddTrainingRequestDTO req,
                                                   @RequestHeader Map<String, String> headers) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");

        trainingService.createTrainingProfile(req.traineeUsername(), req.trainerUsername(), req.trainingName(),
                req.trainingTypeId(), req.trainingDate(), req.duration());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        httpHeaders.add("authorization", headers.get("authorization"));

        HttpEntity<String> entity = new HttpEntity<>("body", httpHeaders);
        return circuitBreaker.run(
                () ->
                        restTemplate.exchange(MICROSERVICE_URL + "/training/workload/" + req.trainerUsername(),
                                HttpMethod.POST, entity, TrainingWorkloadResponseDTO.class).getBody()
                ,
                throwable ->
                {
                    // or call fallback microservice
                    throw new IllegalArgumentException();
                });
    }

    @DeleteMapping
    @ApiOperation(value = "Delete training")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = DeleteTrainingResponseDTO.class),
            @ApiResponse(code = 400, message = "Given entity was not found", response = ErrorResponse.class),
    })
    public DeleteTrainingResponseDTO deleteTraining(@RequestBody DeleteTrainingRequestDTO req,
                                                    @RequestHeader Map<String, String> headers) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        httpHeaders.add("authorization", headers.get("authorization"));

        HttpEntity<String> entity = new HttpEntity<>("body", httpHeaders);

        return circuitBreaker.run(
                () ->
                        restTemplate.exchange(MICROSERVICE_URL + "/training/workload/" + req.trainingId(),
                                HttpMethod.DELETE, entity, DeleteTrainingResponseDTO.class).getBody()
                ,
                throwable ->
                {
                    // or call fallback microservice
                    throw new IllegalArgumentException();
                });
    }

    public void setTrainingService(JpaTrainingService trainingService) {
        this.trainingService = trainingService;
    }
}
