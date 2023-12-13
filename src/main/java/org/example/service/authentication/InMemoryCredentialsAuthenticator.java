package org.example.service.authentication;

import org.example.model.User;

import java.util.Map;
import java.util.Optional;

public class InMemoryCredentialsAuthenticator extends AbstractCredentialsAuthenticator {
    private Map<Integer, User> storageEntities;

    @Override
    protected Optional<User> getUserByCredentials(Credentials credentials) {
        User foundUser = null;
        for (User user : storageEntities.values()) {
            if (user.getUsername().equals(credentials.username()) && user.getPassword().equals(credentials.password()))
                foundUser = user;
        }
        return Optional.ofNullable(foundUser);
    }

    public void setStorageEntities(Map<Integer, User> storageEntities) {
        this.storageEntities = storageEntities;
    }
}
