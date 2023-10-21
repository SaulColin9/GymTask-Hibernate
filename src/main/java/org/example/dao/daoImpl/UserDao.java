package org.example.dao.daoImpl;
import org.example.dao.DaoImpl;
import org.example.model.User;
import java.util.*;

public class UserDao extends DaoImpl<User> {
    private String filePath;
    @Override
    public Optional<User> get(int id) {
        User foundUser = null;
        for (User user : entities) {
            if (user.getId() == id) {
                foundUser = user;
                break;
            }
        }
        return Optional.ofNullable(foundUser);
    }

    @Override
    public void save(User user) {

    }

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
}
