package org.example.controller.dto;

import java.util.Date;

public record AddTrainingRequestDTO(String traineeUsername, String trainerUsername, String trainingName,
                                    int trainingTypeId, Date trainingDate, double duration) {
}
