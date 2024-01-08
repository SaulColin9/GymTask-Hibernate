package org.example.dao.jpa;

import jakarta.persistence.EntityManager;
import org.example.dao.Dao;
import org.example.model.EntityModel;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Consumer;

public abstract class JpaDaoImpl<T extends EntityModel> implements Dao<T> {
    private EntityManager entityManager;

    @Transactional
    public void executeTransaction(Consumer<EntityManager> action) {
        action.accept(entityManager);
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
