package org.example.service.authentication;

import org.example.exception.UserAuthenticationException;
import org.example.exception.UserAuthorizationException;
import org.example.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.AuthenticationException;
import java.util.Optional;

public abstract class AbstractCredentialsAuthenticator implements CredentialsAuthenticator {
    private static final Logger logger = LoggerFactory.getLogger(AbstractCredentialsAuthenticator.class);
    private static final String USER_NOT_AUTHORIZED = "Provided user is not authorized to perform this action";
    private static final String USER_AUTHORIZED = "User authorized successfully";
    private static final String USER_AUTHENTICATED = "User authenticated successfully";
    private static final String FAILED_TO_AUTHENTICATE = "No user was found with the provided credentials";

    @Override
    public void authorize(Credentials credentials, User user) {
        Optional<User> foundUser = getUserByCredentials(credentials);
        foundUser.ifPresentOrElse(
                (existentUser) -> {
                    if (!existentUser.equals(user)) {
                        logger.error(USER_NOT_AUTHORIZED);
                        throw new UserAuthorizationException(USER_NOT_AUTHORIZED);
                    }
                },
                () -> {
                    logger.error(FAILED_TO_AUTHENTICATE);
                    throw new UserAuthenticationException(FAILED_TO_AUTHENTICATE);
                }
        );
        logger.info(USER_AUTHENTICATED);
        logger.info(USER_AUTHORIZED);
    }


    protected abstract Optional<User> getUserByCredentials(Credentials credentials);

}
