package org.example.dao.entities;

import org.example.configuration.Storage;
import org.example.dao.DaoImpl;
import org.example.model.User;

public class UserDao extends DaoImpl<User> {
    @Override
    public void setStorage(Storage storage) {
        setStorageEntities(storage.getUsers());
    }
}
