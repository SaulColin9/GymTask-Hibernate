package org.example.dao.daoImpl;

import com.fasterxml.jackson.core.type.TypeReference;
import org.example.dao.Dao;
import org.example.dao.DaoImpl;
import org.example.model.Trainee;
import org.example.model.Trainer;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class TrainerDao extends DaoImpl<Trainer> {

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
        setEntities(getEntities(Trainer.class));
    }

    @Override
    public int getNextId() {
        if(entities.size()== 0){
            return 0;
        }
        return entities.get(entities.size() - 1).getId() + 1;
    }

    @Override
    public Trainer setId(Trainer trainer, int id) {
        trainer.setId(id);
        return trainer;
    }

    @Override
    public int getId(Trainer trainer) {
        return trainer.getId();
    }
}
