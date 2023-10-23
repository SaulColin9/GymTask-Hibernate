package org.example.service;

import org.example.model.Trainer;

import java.util.List;

public interface TrainerService {
    Trainer createTrainerProfile(String firstName, String lastName, int specialization);
    Trainer updateTrainerProfile(int id, String firstName, String lastName, boolean isActive, int specialization);
    Trainer selectTrainerProfile(int id);
    List<Trainer> selectAll();
}
