package org.example.service.utils.user;

import org.example.model.User;

public class InMemoryUserUtils extends AbstractUserUtils {

    @Override
    protected void createUserStrategy(User user) {
        userDao.save(user);
    }

    @Override
    protected void updateUserStrategy(int id, User user) {
        userDao.update(id, user);
    }
}
