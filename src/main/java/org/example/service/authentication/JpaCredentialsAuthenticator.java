package org.example.service.authentication;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JpaCredentialsAuthenticator extends AbstractCredentialsAuthenticator {
    @Autowired(required = false)
    private EntityManager entityManager;

    @Override
    protected Optional<User> getUserByCredentials(Credentials credentials) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        TypedQuery<User> query = entityManager
                .createQuery("FROM User WHERE username = :username", User.class);
        query.setParameter("username", credentials.username());

        try {
            Optional<User> user = Optional.of(query.getSingleResult());
            if (!passwordEncoder.matches(credentials.password(), user.get().getPassword())) {
                return Optional.empty();
            }
            return user;
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}

