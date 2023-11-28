package org.example.dao.inMemory;

import org.example.configuration.inMemory.storage.Storage;
import org.example.model.User;

public class UserDao extends DaoImpl<User> {
    @Override
    public void setStorage(Storage storage) {
        setStorageEntities(storage.getUsers());
    }
}
