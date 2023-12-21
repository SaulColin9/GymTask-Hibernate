package org.example.service.authentication;

import org.example.model.User;

import javax.naming.AuthenticationException;

public interface CredentialsAuthenticator {
    void authorize(Credentials credentials, User user);
}
