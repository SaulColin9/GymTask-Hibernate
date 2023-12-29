package org.example.controller.dto;

import org.example.model.Trainee;
import org.example.model.Trainer;
import org.example.model.TrainingType;
import org.example.model.User;

import java.util.List;

public class TrainerDTO {
    private TrainingType specialization;
    private User user;
    private List<Trainee> trainees;

    public TrainerDTO(Trainer trainer, List<Trainee> trainees) {
        this.specialization = trainer.getSpecialization();
        this.user = trainer.getUser();
        this.trainees = trainees;
    }

    public TrainingType getSpecialization() {
        return specialization;
    }

    public void setSpecialization(TrainingType specialization) {
        this.specialization = specialization;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Trainee> getTrainees() {
        return trainees;
    }

    public void setTrainees(List<Trainee> trainees) {
        this.trainees = trainees;
    }
}
