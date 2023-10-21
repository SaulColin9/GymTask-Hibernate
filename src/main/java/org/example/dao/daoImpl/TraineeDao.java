package org.example.dao.daoImpl;

import com.fasterxml.jackson.core.type.TypeReference;
import org.example.dao.Dao;
import org.example.dao.DaoImpl;
import org.example.model.Trainee;
import org.example.model.User;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class TraineeDao extends DaoImpl<Trainee> {

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
       setEntities(getEntities(Trainee.class));
    }

    @Override
    public int getNextId() {
        return entities.get(entities.size() - 1).getId() + 1;
    }

    @Override
    public Trainee setId(Trainee trainee, int id) {
        trainee.setId(id);
        return trainee;
    }

    @Override
    public int getId(Trainee trainee) {
        return trainee.getId();
    }
}
