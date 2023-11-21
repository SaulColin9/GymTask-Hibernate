package org.example.service;

import org.example.dao.Dao;
import org.example.model.User;

import java.util.Optional;

public class UserUtilsImpl implements UserUtils {
    private UsernameGenerator usernameGenerator;
    private PasswordGenerator passwordGenerator;
    private Dao<User> userDao;


    @Override
    public User createUser(String firstName, String lastName) {
        String username = usernameGenerator.generateUserName(firstName, lastName);
        String password = passwordGenerator.generatePassword(10);
        User newUser = new User(firstName, lastName, username, password, true);
        userDao.save(newUser);
        return newUser;
    }

    @Override
    public User updateUser(int userId, String newFirstName, String newLastName, boolean isActive) {
        String username = usernameGenerator.generateUserName(newFirstName, newLastName);
        String password = userDao.get(userId).map(User::getPassword).orElse("");
        User updatedUser = new User(newFirstName, newLastName, username, password, true);

        updatedUser = updatedUser.setId(userId);
        updatedUser.setIsActive(isActive);
        userDao.update(userId, updatedUser);

        return updatedUser;
    }

    @Override
    public Optional<User> deleteUser(int userId) {
        return userDao.delete(userId);
    }

    public void setUsernameGenerator(UsernameGenerator usernameGenerator) {
        this.usernameGenerator = usernameGenerator;
    }

    public void setPasswordGenerator(PasswordGenerator passwordGenerator) {
        this.passwordGenerator = passwordGenerator;
    }

    public void setUserDao(Dao<User> userDao) {
        this.userDao = userDao;
    }
}
