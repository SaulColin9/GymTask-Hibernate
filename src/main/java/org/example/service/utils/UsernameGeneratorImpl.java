package org.example.service.utils;

import org.example.dao.Dao;
import org.example.model.User;

public class UsernameGeneratorImpl implements UsernameGenerator {
    private static final String separator = ".";
    private Dao<User> userDao;


    public int userNameExists(String firstName, String lastName) {
        int serial = 0;
        for (User user : userDao.getAll()) {
            if (user.getUsername().contains(firstName + separator + lastName)) {
                serial++;
            }
        }
        return serial;
    }

    public String generateUserName(String firstName, String lastName) {
        int serial = userNameExists(firstName, lastName);
        return serial > 0 ? firstName + separator + lastName + serial : firstName + separator + lastName;
    }

    public void setUserDao(Dao<User> userDao) {
        this.userDao = userDao;
    }
}
