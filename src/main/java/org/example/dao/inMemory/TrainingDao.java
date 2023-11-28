package org.example.dao.inMemory;

import org.example.configuration.inMemory.storage.Storage;
import org.example.model.Training;

public class TrainingDao extends DaoImpl<Training> {
    @Override
    public void setStorage(Storage storage) {
        setStorageEntities(storage.getTrainings());
    }
}
