package org.example.dao.entities;

import org.example.configuration.Storage;
import org.example.dao.DaoImpl;
import org.example.model.Trainee;

public class TraineeDao extends DaoImpl<Trainee> {
    @Override
    public void setStorage(Storage storage) {
        setStorageEntities(storage.getTrainees());
    }
}
