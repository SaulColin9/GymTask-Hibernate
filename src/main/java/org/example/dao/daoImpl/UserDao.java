package org.example.dao.daoImpl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dao.Dao;
import org.example.dao.DaoImpl;
import org.example.model.User;
import org.springframework.beans.factory.InitializingBean;
import java.io.*;
import java.util.*;


public class UserDao implements Dao<User>,InitializingBean{
     int nextId;
     public String filePath;

    private List<User> users;
    public List<User> getUsers(){
        return users;
    }

     @Override
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
        writeEntities(filePath, users);
        nextId++;
    }

    @Override
    public void update(User user) {
        int id = users.indexOf(user);
        users.set(id, user);
    }

    @Override
    public void delete(int id) {
        users.remove(id);
        writeEntities(filePath, users);
    }

    @Override
    public List<User> getEntities(String filePath, TypeReference<List<User>> typeReference){
        try{

            Dao<User> dao = new DaoImpl<>();
            return dao.getEntities(filePath, typeReference);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public void writeEntities(String path, List<User> entities) {
        try{
            Dao<User> dao = new DaoImpl<>();
            dao.writeEntities(path, entities);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        users = getEntities(filePath, new TypeReference<List<User>>() {});

        System.out.println(users);
        nextId = users.get(users.size() - 1).getId() + 1;
    }
}
