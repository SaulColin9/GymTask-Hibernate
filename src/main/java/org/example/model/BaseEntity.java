package org.example.model;

import jakarta.persistence.*;

@MappedSuperclass
public class BaseEntity implements EntityModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public EntityModel setId(int id) {
        this.id = id;
        return this;
    }
}
