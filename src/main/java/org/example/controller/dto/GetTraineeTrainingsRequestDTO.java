package org.example.controller.dto;

import java.util.Date;

public record GetTraineeTrainingsRequestDTO(String username, Date periodFrom, Date periodTo, Integer trainingType) {
}
