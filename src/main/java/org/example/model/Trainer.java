package org.example.model;

public class Trainer implements Entity {
    int id;
    int specialization;
    int userId;
    private User user;


    public Trainer() {
    }

    public Trainer(int specialization, int userId) {
        this.specialization = specialization;
        this.userId = userId;
    }

    public Trainer(int specialization, int userId, User user) {
        this.specialization = specialization;
        this.userId = userId;
        this.user = user;
    }

    @Override
    public int getId() {
        return id;
    }

    public Trainer setId(int id) {
        this.id = id;
        return this;
    }

    public User getUser() {
        return this.user;
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

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Trainer{" +
                "id=" + id +
                ", specialization=" + specialization +
                ", userId=" + userId +
                ", user=" + user +
                '}';
    }
}
