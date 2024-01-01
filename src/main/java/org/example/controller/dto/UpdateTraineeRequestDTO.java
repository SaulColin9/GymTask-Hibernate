package org.example.controller.dto;

import java.util.Date;

public record UpdateTraineeRequestDTO(String username, String firstName, String lastName, Date dateOfBirth,
                                      String address, boolean isActive) {
}
