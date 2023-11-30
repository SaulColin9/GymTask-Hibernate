package org.example.dao.inmemory;

import org.example.configuration.inmemory.storage.Storage;
import org.example.model.TrainingType;

public class TrainingTypeDao extends DaoImpl<TrainingType> {
    @Override
    public void setStorage(Storage storage) {
        setStorageEntities(storage.getTrainingTypes());
    }
}
