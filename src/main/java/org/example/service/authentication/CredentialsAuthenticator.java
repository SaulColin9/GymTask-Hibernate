package org.example.service.authentication;

import org.example.model.User;

public interface CredentialsAuthenticator {
    void authorize(Credentials credentials, User user);

    boolean login(Credentials credentials);
}
