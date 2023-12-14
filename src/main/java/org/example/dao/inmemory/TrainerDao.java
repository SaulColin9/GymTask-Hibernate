package org.example.dao.inmemory;

import org.example.configuration.inmemory.storage.Storage;
import org.example.model.Trainer;

public class TrainerDao extends DaoImpl<Trainer> {
    @Override
    public void setStorage(Storage storage) {
        setStorageEntities(storage.getTrainers());
    }
}
