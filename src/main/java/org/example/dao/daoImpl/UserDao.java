package org.example.dao.daoImpl;
import org.example.dao.DaoImpl;
import org.example.model.User;
import java.util.*;

public class UserDao extends DaoImpl<User> {
    private String filePath;

    @Override
    public void setFilePath(String filePath) {
       this.filePath = filePath;
    }

    @Override
    public String getFilePath() {
        return filePath;
    }

    @Override
    public void afterPropertiesSet() {
        setEntities(getEntities(User.class));
    }

    @Override
    public int getNextId() {
        return entities.get(entities.size() - 1).getId() + 1;
    }

    @Override
    public User setId(User user, int id) {
        user.setId(id);
        return user;
    }

    @Override
    public int getId(User user) {
        return user.getId();
    }
}
