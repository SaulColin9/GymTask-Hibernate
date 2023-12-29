package org.example.controller.dto;

public record ChangeLoginRequestDTO(String username, String oldPassword, String newPassword) {
}
