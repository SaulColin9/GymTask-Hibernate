package org.example.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Training implements EntityModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String trainingName;
    Date trainingDate;
    double trainingDuration;
    @ManyToOne
    Trainee trainee;
    @ManyToOne
    Trainer trainer;
    @ManyToOne
    @JoinColumn(name = "trainingType_id", referencedColumnName = "id")
    TrainingType trainingType;

    public Training() {
    }

    public Training(Trainee trainee, Trainer trainer, String trainingName, TrainingType trainingType, Date trainingDate, double trainingDuration) {
        this.trainee = trainee;
        this.trainer = trainer;
        this.trainingName = trainingName;
        this.trainingType = trainingType;
        this.trainingDate = trainingDate;
        this.trainingDuration = trainingDuration;
    }

    @Override
    public int getId() {
        return id;
    }

    public Training setId(int id) {
        this.id = id;
        return this;
    }

    public String getTrainingName() {
        return trainingName;
    }

    public void setTrainingName(String trainingName) {
        this.trainingName = trainingName;
    }





    public Date getTrainingDate() {
        return trainingDate;
    }

    public void setTrainingDate(Date trainingDate) {
        this.trainingDate = trainingDate;
    }

    public double getTrainingDuration() {
        return trainingDuration;
    }

    public void setTrainingDuration(double trainingDuration) {
        this.trainingDuration = trainingDuration;
    }

    public void setTrainee(Trainee trainee) {
        this.trainee = trainee;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public void setTrainingType(TrainingType trainingType) {
        this.trainingType = trainingType;
    }

    public Trainee getTrainee() {
        return trainee;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public TrainingType getTrainingType() {
        return trainingType;
    }

    @Override
    public String toString() {
        return "Training{" +
                "id=" + id +
                ", trainingName='" + trainingName + '\'' +
                ", trainingDate=" + trainingDate +
                ", trainingDuration=" + trainingDuration +
                ", trainee=" + trainee +
                ", trainer=" + trainer +
                ", trainingType=" + trainingType +
                '}';
    }
}
