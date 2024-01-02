package org.example.controller.dto;

import java.util.Date;

public record GetTrainerTrainingsRequestDTO(String username, Date periodFrom, Date periodTo, String traineeName,
                                            Integer trainingType) {
}
