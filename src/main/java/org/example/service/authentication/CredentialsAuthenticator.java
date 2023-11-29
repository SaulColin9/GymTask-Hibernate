package org.example.service.authentication;

import org.example.model.User;

import javax.naming.AuthenticationException;
import java.util.Optional;

public interface CredentialsAuthenticator {
    void authorize(Credentials credentials, User user) throws AuthenticationException;
}
