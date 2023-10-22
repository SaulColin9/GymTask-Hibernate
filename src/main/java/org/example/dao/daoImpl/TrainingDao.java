package org.example.dao.daoImpl;

import org.example.dao.Dao;
import org.example.dao.DaoImpl;
import org.example.model.Training;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class TrainingDao extends DaoImpl<Training> {
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
        setEntities(getEntities(Training.class));
    }

    @Override
    public int getNextId() {
        if(entities.size()== 0){
            return 0;
        }
        return entities.get(entities.size() - 1).getId() + 1;
    }

    @Override
    public Training setId(Training training, int id) {
        training.setId(id);
        return training;
    }

    @Override
    public int getId(Training training) {
        return training.getId();
    }
}
