package org.example.dao.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.example.configuration.Storage;
import org.example.dao.Dao;
import org.example.model.EntityModel;
import org.example.model.User;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public abstract class JpaDaoImpl<T extends EntityModel> implements Dao<T> {
    private EntityManager entityManager;

    @Override
    public abstract Optional<T> get(int id);


    @Override
    public abstract List<T> getAll();


    @Override
    public abstract T save(T t);

    @Override
    public abstract T update(int id, T t);

    @Override
    public abstract Optional<T> delete(int id);


    public void executeTransaction(Consumer<EntityManager> action) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            action.accept(entityManager);
            transaction.commit();
        } catch (RuntimeException e) {
            transaction.rollback();
            throw e;
        }
    }

    @Override
    public void setStorage(Storage storage) {

    }

    @Override
    public int getNextId() {
        return 0;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}