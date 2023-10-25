package org.example.dao.entities;

import org.example.configuration.Storage;
import org.example.dao.DaoImpl;
import org.example.model.TrainingType;

public class TrainingTypeDao extends DaoImpl<TrainingType> {
    @Override
    public void setStorage(Storage storage) {
        setStorageEntities(storage.getTrainingTypes());
    }
}
