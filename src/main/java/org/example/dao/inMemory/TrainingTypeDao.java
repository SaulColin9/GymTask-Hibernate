package org.example.dao.inMemory;

import org.example.configuration.inMemory.storage.Storage;
import org.example.model.TrainingType;

public class TrainingTypeDao extends DaoImpl<TrainingType> {
    @Override
    public void setStorage(Storage storage) {
        setStorageEntities(storage.getTrainingTypes());
    }
}
