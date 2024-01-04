package org.example.service.authentication;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JpaCredentialsAuthenticator extends AbstractCredentialsAuthenticator {
    @Autowired
    private EntityManager entityManager;

    @Override
    protected Optional<User> getUserByCredentials(Credentials credentials) {
        TypedQuery<User> query = entityManager
                .createQuery("FROM User WHERE username = :username AND password = :password", User.class);
        query.setParameter("username", credentials.username());
        query.setParameter("password", credentials.password());

        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}

