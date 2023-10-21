package org.example.dao.daoImpl;

import com.fasterxml.jackson.core.type.TypeReference;
import org.example.dao.Dao;
import org.example.dao.DaoImpl;
import org.example.model.TrainingType;
import org.springframework.beans.factory.InitializingBean;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class TrainingTypeDao extends DaoImpl<TrainingType> {

    private String filePath;
    @Override
    public void setFilePath(String filePath) {
       this.filePath = filePath;
    }

    @Override
    public String getFilePath() {
        return filePath;
    }

    @Override
    public void afterPropertiesSet() {
        setEntities(getEntities(TrainingType.class));
    }

    @Override
    public int getNextId() {
        return entities.get(entities.size() - 1).getId() + 1;
    }

    @Override
    public TrainingType setId(TrainingType trainingType, int id) {
        trainingType.setId(id);
        return trainingType;
    }

    @Override
    public int getId(TrainingType trainingType) {
        return trainingType.getId();
    }
}
