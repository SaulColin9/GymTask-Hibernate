package org.example.service.utils.user;

import org.example.model.User;

import java.util.Optional;

public interface UserUtils {
    User createUser(String firstName, String lastName);

    User updateUser(int userId, String newFirstName, String newLastName, boolean isActive);

    Optional<User> deleteUser(int userId);

}