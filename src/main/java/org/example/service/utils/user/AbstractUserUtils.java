package org.example.service.utils.user;

import org.example.dao.Dao;
import org.example.model.User;
import org.example.service.utils.PasswordGenerator;
import org.example.service.utils.UsernameGenerator;

import java.util.Optional;

public abstract class AbstractUserUtils implements UserUtils {
    private UsernameGenerator usernameGenerator;
    private PasswordGenerator passwordGenerator;
    protected Dao<User> userDao;

    @Override
    public User createUser(String firstName, String lastName) {
        String username = usernameGenerator.generateUserName(firstName, lastName);
        String password = passwordGenerator.generatePassword(10);
        User newUser = new User(firstName, lastName, username, password, true);

        createUserStrategy(newUser);

        return newUser;
    }

    @Override
    public User updateUser(int userId, String newFirstName, String newLastName, boolean isActive) {
        String username = usernameGenerator.generateUserName(newFirstName, newLastName);
        String password = userDao.get(userId).map(User::getPassword).orElse("");
        User updatedUser = new User(newFirstName, newLastName, username, password, true);

        updatedUser = updatedUser.setId(userId);
        updatedUser.setIsActive(isActive);

        updateUserStrategy(userId, updatedUser);

        return updatedUser;
    }

    @Override
    public Optional<User> deleteUser(int userId) {
        return userDao.delete(userId);
    }

    protected abstract void createUserStrategy(User user);

    protected abstract void updateUserStrategy(int id, User user);

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
