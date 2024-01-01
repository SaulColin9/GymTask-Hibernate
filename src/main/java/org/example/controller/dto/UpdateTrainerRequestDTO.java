package org.example.controller.dto;

import org.example.model.TrainingType;

public record UpdateTrainerRequestDTO(String username, String firstName, String lastName, int specialization,
                                      boolean isActive) {
}
