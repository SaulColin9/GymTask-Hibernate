package org.example.service.utils;

import org.example.dao.Dao;
import org.example.model.User;

public class UsernameGeneratorImpl implements UsernameGenerator {
    private static final String SEPARATOR = ".";
    private Dao<User> userDao;


    public int userNameExists(String firstName, String lastName) {
        int serial = 0;
        for (User user : userDao.getAll()) {
            if (user.getUsername().contains(firstName + SEPARATOR + lastName)) {
                serial++;
            }
        }
        return serial;
    }

    @Override
    public String generateUserName(String firstName, String lastName) {
        int serial = userNameExists(firstName, lastName);
        return serial > 0 ? firstName + SEPARATOR + lastName + serial : firstName + SEPARATOR + lastName;
    }

    public void setUserDao(Dao<User> userDao) {
        this.userDao = userDao;
    }
}
