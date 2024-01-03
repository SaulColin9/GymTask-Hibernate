package org.example.controller.dto;

import org.example.model.Trainee;
import org.example.model.Trainer;
import org.example.model.User;

import java.util.Date;
import java.util.List;

public class TraineeDTO {
    private Date dateOfBirth;
    private String address;
    private User user;
    private List<Trainer> trainers;

    public TraineeDTO(){}
    public TraineeDTO(Trainee trainee, List<Trainer> trainers) {
        this.dateOfBirth = trainee.getDateOfBirth();
        this.address = trainee.getAddress();
        this.user = trainee.getUser();
        this.trainers = trainers;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Trainer> getTrainers() {
        return trainers;
    }

    public void setTrainers(List<Trainer> trainers) {
        this.trainers = trainers;
    }
}
