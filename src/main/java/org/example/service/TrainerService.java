package org.example.service;

import org.example.model.Trainer;

public interface TrainerService {
    Trainer createTrainerProfile(String firstName, String lastName, int specialization);

    boolean updateTrainerProfile(int id, String firstName, String lastName, boolean isActive, int specialization);

    Trainer selectTrainerProfile(int id);

}
