package org.example.service.authentication;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.example.model.User;

import java.util.Optional;

public class CredentialsAuthenticatorImpl implements CredentialsAuthenticator {
    EntityManager entityManager;

    @Override
    public Optional<User> authenticate(Credentials credentials) {
        Query query = entityManager.createQuery("FROM User WHERE username = :username AND password = :password");
        query.setParameter("username", credentials.getUsername());
        query.setParameter("password", credentials.getPassword());
        return query.getResultList().stream().findFirst();
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
