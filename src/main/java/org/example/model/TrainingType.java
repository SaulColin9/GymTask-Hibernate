package org.example.model;

public class TrainingType {
    int id;
    String trainingTypeName;

    public TrainingType(){

    }
    public TrainingType(String trainingTypeName) {
        this.trainingTypeName = trainingTypeName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTrainingTypeName() {
        return trainingTypeName;
    }

    public void setTrainingTypeName(String trainingTypeName) {
        this.trainingTypeName = trainingTypeName;
    }

    @Override
    public String toString() {
        return "TrainingType{" +
                "id=" + id +
                ", trainingTypeName='" + trainingTypeName + '\'' +
                '}';
    }
}
