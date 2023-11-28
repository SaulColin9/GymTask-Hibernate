package org.example.dao.inMemory;

import org.example.configuration.inMemory.storage.Storage;
import org.example.model.Trainer;

public class TrainerDao extends DaoImpl<Trainer> {

    @Override
    public void setStorage(Storage storage) {
        setStorageEntities(storage.getTrainers());
    }
}
