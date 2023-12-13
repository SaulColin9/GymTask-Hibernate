package org.example.service.authentication;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import org.example.model.User;

import java.util.Optional;

public class JpaCredentialsAuthenticator extends AbstractCredentialsAuthenticator {
    private EntityManager entityManager;

    @Override
    protected Optional<User> getUserByCredentials(Credentials credentials) {
        Query query = entityManager.createQuery("FROM User WHERE username = :username AND password = :password");
        query.setParameter("username", credentials.username());
        query.setParameter("password", credentials.password());

        try {
            return Optional.of((User) query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}

