package org.example.dao.inmemory;

import org.example.configuration.inmemory.storage.Storage;
import org.example.model.Trainee;

public class TraineeDao extends DaoImpl<Trainee> {
    @Override
    public void setStorage(Storage storage) {
        setStorageEntities(storage.getTrainees());
    }
}
