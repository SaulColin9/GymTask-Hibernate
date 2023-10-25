package org.example.service;

import org.example.dao.Dao;
import org.example.model.Entity;
import org.example.model.User;

public class UserUtilsImpl implements UserUtils {
    private final UsernameGenerator usernameGenerator = new UsernameGeneratorImpl();
    private final PasswordGenerator passwordGenerator = new PasswordGeneratorImpl();

    @Override
    public User createUser(String firstName, String lastName, Dao<User> userDao) {
        String username = usernameGenerator.generateUserName(firstName, lastName, userDao);
        String password = passwordGenerator.generatePassword(10);
        return  new User(firstName, lastName, username, password, true);
    }
    @Override
    public User updateUser(int userId, String newFirstName, String newLastName, Dao<User> userDao){
        User updatedUser = createUser(newFirstName, newLastName, userDao);
        updatedUser = updatedUser.setId(userId);
        userDao.update(userId, updatedUser);
        return updatedUser;
    }

}
