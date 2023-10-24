package org.example.service;

import org.example.dao.Dao;
import org.example.model.Trainer;
import org.example.model.User;

public interface TrainerService {
    Trainer createTrainerProfile(String firstName, String lastName, int specialization);
    Trainer updateTrainerProfile(int id, String firstName, String lastName, boolean isActive, int specialization);
    Trainer selectTrainerProfile(int id);
    void setTrainerDao(Dao<Trainer> trainerDao);
    void setUserDao(Dao<User> userDao);
}
