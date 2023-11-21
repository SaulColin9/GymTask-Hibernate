package org.example.model;

import java.util.Date;
import java.util.Objects;

public class Trainee implements Entity {
    private int id;
    private Date dateOfBirth;
    private String address;
    private User user;

    public Trainee() {
    }

    public Trainee(Date dateOfBirth, String address) {
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }

    public Trainee(Date dateOfBirth, String address, User user) {
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.user = user;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Trainee setId(int id) {
        this.id = id;
        return this;
    }

    public User getUser() {
        return this.user;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public Trainee setAddress(String address) {
        this.address = address;
        return this;
    }


    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Trainee{" +
                "id=" + id +
                ", dateOfBirth=" + dateOfBirth +
                ", address='" + address + '\'' +
                ", user=" + user +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trainee trainee = (Trainee) o;
        return getId() == trainee.getId() && Objects.equals(getDateOfBirth(), trainee.getDateOfBirth()) && Objects.equals(getAddress(), trainee.getAddress()) && Objects.equals(getUser(), trainee.getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDateOfBirth(), getAddress(), getUser());
    }
}
