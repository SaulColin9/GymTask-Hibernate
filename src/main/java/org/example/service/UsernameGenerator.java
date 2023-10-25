package org.example.service;

import org.example.dao.Dao;
import org.example.model.User;

public interface UsernameGenerator {
    int userNameExists(String firstName, String lastName, String separator, Dao<User> userDao);
    String generateUserName(String firstName, String lastName, String separator, Dao<User> userDao);
}
