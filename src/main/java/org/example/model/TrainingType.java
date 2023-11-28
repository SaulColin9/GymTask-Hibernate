package org.example.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class TrainingType implements EntityModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String trainingTypeName;

    public TrainingType() {

    }

    public TrainingType(String trainingTypeName) {
        this.trainingTypeName = trainingTypeName;
    }

    @Override
    public int getId() {
        return id;
    }

    public TrainingType setId(int id) {
        this.id = id;
        return this;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrainingType that = (TrainingType) o;
        return getId() == that.getId() && Objects.equals(getTrainingTypeName(), that.getTrainingTypeName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTrainingTypeName());
    }
}
