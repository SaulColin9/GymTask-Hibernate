package org.example.dao.entities;

import org.example.configuration.Storage;
import org.example.dao.DaoImpl;
import org.example.model.Trainer;

public class TrainerDao extends DaoImpl<Trainer> {

    @Override
    public void setStorage(Storage storage) {
        setStorageEntities(storage.getTrainers());
    }
}
