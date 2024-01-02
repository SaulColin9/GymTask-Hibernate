package org.example.controller.dto;

import org.example.model.Training;
import org.example.model.TrainingType;

import java.util.Date;

public class GetTrainerTrainingsResponseDTO {
    private String trainingName;
    private Date trainingDate;
    private TrainingType trainingType;
    private double trainingDuration;
    private String traineeName;

    public GetTrainerTrainingsResponseDTO() {
    }

    public GetTrainerTrainingsResponseDTO(Training training) {
        this.trainingName = training.getTrainingName();
        this.trainingDate = training.getTrainingDate();
        this.trainingType = training.getTrainingType();
        this.trainingDuration = training.getTrainingDuration();
        this.traineeName = training.getTrainee().getUser().getUsername();
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

    public TrainingType getTrainingType() {
        return trainingType;
    }

    public void setTrainingType(TrainingType trainingType) {
        this.trainingType = trainingType;
    }

    public double getTrainingDuration() {
        return trainingDuration;
    }

    public void setTrainingDuration(double trainingDuration) {
        this.trainingDuration = trainingDuration;
    }

    public String getTraineeName() {
        return traineeName;
    }

    public void setTraineeName(String traineeName) {
        this.traineeName = traineeName;
    }
}
