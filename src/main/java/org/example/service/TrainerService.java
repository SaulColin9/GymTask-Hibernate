package org.example.service;

import org.example.model.Trainer;

import java.util.Optional;

public interface TrainerService {
    Trainer createTrainerProfile(String firstName, String lastName, int specialization);
    Trainer updateTrainerProfile(int id, Trainer trainer);
    Optional<Trainer> selectTrainerProfile(int id);
}
