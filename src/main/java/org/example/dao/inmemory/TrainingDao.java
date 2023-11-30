package org.example.dao.inmemory;

import org.example.configuration.inmemory.storage.Storage;
import org.example.model.Training;

public class TrainingDao extends DaoImpl<Training> {
    @Override
    public void setStorage(Storage storage) {
        setStorageEntities(storage.getTrainings());
    }
}
