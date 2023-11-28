package org.example.dao.inMemory;

import org.example.configuration.inMemory.storage.Storage;
import org.example.model.Trainee;

public class TraineeDao extends DaoImpl<Trainee> {
    @Override
    public void setStorage(Storage storage) {
        setStorageEntities(storage.getTrainees());
    }
}
