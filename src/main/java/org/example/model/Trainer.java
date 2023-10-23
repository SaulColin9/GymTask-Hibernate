package org.example.model;

public class Trainer implements Entity{
    int id;
    int specialization;
    int userId;

    public Trainer(){}
    public Trainer(int specialization, int userId) {
        this.specialization = specialization;
        this.userId = userId;
    }

    @Override
    public int getId() {
        return id;
    }

    public Trainer setId(int id) {
        this.id = id;
        return this;
    }

    public int getSpecialization() {
        return specialization;
    }

    public Trainer setSpecialization(int specialization) {
        this.specialization = specialization;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Trainer{" +
                "id=" + id +
                ", specialization=" + specialization +
                ", userId=" + userId +
                '}';
    }
}
