package org.example.service;

import org.example.dao.Dao;
import org.example.model.User;

public class UsernameGeneratorImpl implements UsernameGenerator{

    public int userNameExists(String firstName, String lastName, String separator, Dao<User> userDao) {
        int serial = 0;
        for (User user : userDao.getAll()) {
            if (user.getUsername().contains(firstName + separator + lastName)) {
                serial++;
            }
        }
        return serial;
    }

    public String generateUserName(String firstName, String lastName, String separator, Dao<User> userDao) {
        int serial = userNameExists(firstName, lastName, separator, userDao);
        return serial > 0 ? firstName + separator + lastName + serial : firstName + separator + lastName;
    }
}
