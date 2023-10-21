package org.example.dao.daoImpl;

import com.fasterxml.jackson.core.type.TypeReference;
import org.example.dao.Dao;
import org.example.dao.DaoConnectionImpl;
import org.example.model.User;
import org.springframework.beans.factory.InitializingBean;
import java.util.*;


public class UserDao extends DaoConnectionImpl<User> implements Dao<User>,InitializingBean {
     int nextId;
     public String filePath;

    private List<User> users;
    public List<User> getUsers(){
        return users;
    }

     public void setFilePath(String filePath){
         this.filePath = filePath;
     }
     public String getFilePath(){
         return filePath;
     }
    public UserDao() {
    }

    @Override
    public Optional<User> get(int id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public List<User> getAll() {
        return users;
    }

    @Override
    public void save(User user) {
        user.setId(nextId);
        users.add(user);
        writeEntities();
        nextId++;
    }

    @Override
    public void update(int id, User user) {
        users.set(id, user);
        writeEntities();
    }

    @Override
    public void delete(int id) {
        users.remove(id);
        writeEntities();
    }

    @Override
    public List<User> getEntities(){
        return getEntities(getFilePath(), new TypeReference<List<User>>() {});
    }



    @Override
    public void writeEntities() {
        writeEntities(getFilePath(), users);
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        users = getEntities();
        nextId = users.get(users.size() - 1).getId() + 1;
    }
}
