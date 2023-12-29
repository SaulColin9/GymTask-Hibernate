package org.example.controller.dto;

import java.util.Date;

public record AddTraineeRequestDTO(String firstName, String lastName, Date dateOfBirth, String address) {
}
