package org.example.dao.entities;

import org.example.configuration.Storage;
import org.example.dao.DaoImpl;
import org.example.model.Training;

public class TrainingDao extends DaoImpl<Training> {
    @Override
    public void setStorage(Storage storage) {
        setStorageEntities(storage.getTrainings());
    }
}
