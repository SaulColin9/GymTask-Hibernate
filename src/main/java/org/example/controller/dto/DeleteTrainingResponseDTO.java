package org.example.controller.dto;

import org.example.model.Training;

public class DeleteTrainingResponseDTO {
    public DeleteTrainingResponseDTO(String msg, Training training) {
        this.msg = msg;
        this.training = training;
    }
    public  DeleteTrainingResponseDTO(){}

    private String msg;
    private Training training;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Training getTraining() {
        return training;
    }

    public void setTraining(Training training) {
        this.training = training;
    }
}
