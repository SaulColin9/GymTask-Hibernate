package org.example.service.authentication;

import org.example.model.User;

import java.util.Optional;

public interface CredentialsAuthenticator {
    public Optional<User> authenticate(String username, String password);
}
