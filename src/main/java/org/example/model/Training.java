package org.example.model;

import java.util.Date;

public class Training implements Entity{
    int id;
    int traineeId;
    int trainerId;
    String trainingName;
    int trainingTypeId;
    Date  trainingDate;
    double trainingDuration;
    Trainee trainee;
    Trainer trainer;
    TrainingType trainingType;

    public Training(){}
    public Training(int traineeId, int trainerId, String trainingName, int trainingTypeId, Date trainingDate, double trainingDuration) {
        this.traineeId = traineeId;
        this.trainerId = trainerId;
        this.trainingName = trainingName;
        this.trainingTypeId = trainingTypeId;
        this.trainingDate = trainingDate;
        this.trainingDuration = trainingDuration;
    }

    public Training(Trainee trainee, Trainer trainer, String trainingName, TrainingType trainingType, Date trainingDate, double trainingDuration) {
        this.traineeId = trainee.getId();
        this.trainee = trainee;
        this.trainerId = trainer.getId();
        this.trainer = trainer;
        this.trainingName = trainingName;
        this.trainingTypeId = trainingType.getId();
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

    public int getTraineeId() {
        return traineeId;
    }

    public void setTraineeId(int traineeId) {
        this.traineeId = traineeId;
    }

    public int getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(int trainerId) {
        this.trainerId = trainerId;
    }

    public String getTrainingName() {
        return trainingName;
    }

    public void setTrainingName(String trainingName) {
        this.trainingName = trainingName;
    }

    public int getTrainingTypeId() {
        return trainingTypeId;
    }

    public void setTrainingTypeId(int trainingTypeId) {
        this.trainingTypeId = trainingTypeId;
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


}
