package org.example.service.authentication;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.slf4j.Logger;
import org.example.model.User;
import org.slf4j.LoggerFactory;

import javax.naming.AuthenticationException;
import java.util.Optional;

public class CredentialsAuthenticatorImpl implements CredentialsAuthenticator {
    EntityManager entityManager;
    private static final Logger logger = LoggerFactory.getLogger(CredentialsAuthenticatorImpl.class);
    private static final String USER_NOT_AUTHORIZED = "Provided user is not authorized to perform this action";
    private static final String USER_AUTHORIZED = "User authorized successfully";
    private static final String USER_AUTHENTICATED = "User authenticated successfully";
    private static final String FAILED_TO_AUTHENTICATE = "No user was found with the provided credentials";

    @Override
    public void authorize(Credentials credentials, User user) throws AuthenticationException {
        Optional<User> foundUser = getUserByCredentials(credentials);
        if (foundUser.isEmpty()) {

            logger.error(FAILED_TO_AUTHENTICATE);
            throw new AuthenticationException(FAILED_TO_AUTHENTICATE);
        }

        if (!foundUser.get().equals(user)) {
            logger.error(USER_NOT_AUTHORIZED);
            throw new AuthenticationException(USER_NOT_AUTHORIZED);
        }

        logger.info(USER_AUTHENTICATED);
        logger.info(USER_AUTHORIZED);
    }


    private Optional<User> getUserByCredentials(Credentials credentials) {
        Query query = entityManager.createQuery("FROM User WHERE username = :username AND password = :password");
        query.setParameter("username", credentials.getUsername());
        query.setParameter("password", credentials.getPassword());
        return query.getResultList().stream().findFirst();
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
