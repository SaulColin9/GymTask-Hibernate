package org.example.model;

import java.util.Objects;

public class Trainer implements Entity {
    int id;
    int specialization;
    private User user;


    public Trainer() {
    }

    public Trainer(int specialization) {
        this.specialization = specialization;
    }

    public Trainer(int specialization, User user) {
        this.specialization = specialization;
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


    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Trainer{" +
                "id=" + id +
                ", specialization=" + specialization +
                ", user=" + user +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trainer trainer = (Trainer) o;
        return getId() == trainer.getId() && getSpecialization() == trainer.getSpecialization() && Objects.equals(getUser(), trainer.getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getSpecialization(), getUser());
    }
}
