package org.example.dao.inmemory;

import org.example.configuration.inmemory.storage.Storage;
import org.example.model.User;

public class UserDao extends DaoImpl<User> {
    @Override
    public void setStorage(Storage storage) {
        setStorageEntities(storage.getUsers());
    }
}
