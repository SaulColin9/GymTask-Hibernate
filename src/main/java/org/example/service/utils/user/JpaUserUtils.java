package org.example.service.utils.user;

import org.example.model.User;

public class JpaUserUtils extends AbstractUserUtils {
    @Override
    protected void createUserStrategy(User user) {
        //No strategy is needed for jpa implementation
    }

    @Override
    protected void updateUserStrategy(int id, User user) {
        //No strategy is needed for jpa implementation
    }
}
