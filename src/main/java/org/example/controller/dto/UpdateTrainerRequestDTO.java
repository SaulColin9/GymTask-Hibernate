package org.example.controller.dto;

public record UpdateTrainerRequestDTO(String username, String firstName, String lastName, int specialization,
                                      boolean isActive) {
}
