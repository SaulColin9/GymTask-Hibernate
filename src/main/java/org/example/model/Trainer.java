package org.example.model;

public class Trainer {
    int id;
    int specialization;
    int userId;

    public Trainer(){}
    public Trainer(int specialization, int userId) {
        this.specialization = specialization;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSpecialization() {
        return specialization;
    }

    public void setSpecialization(int specialization) {
        this.specialization = specialization;
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
