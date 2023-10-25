package org.example.service;

import org.example.dao.Dao;
import org.example.model.Entity;
import org.example.model.User;

public interface UserUtils {
    User createUser(String firstName, String lastName, Dao<User> userDao);
    User updateUser(int userId, String newFirstName, String newLastName, Dao<User> userDao);

}