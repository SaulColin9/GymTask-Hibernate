package org.example.dao.daoImpl;

import com.fasterxml.jackson.core.type.TypeReference;
import org.example.dao.Dao;
import org.example.model.TrainingType;
import org.springframework.beans.factory.InitializingBean;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class TrainingTypeDao implements Dao<TrainingType> {

    @Override
    public Optional<TrainingType> get(int id) {
        return Optional.empty();
    }

    @Override
    public List<TrainingType> getAll() {
        return null;
    }

    @Override
    public void save(TrainingType trainingType) {

    }

    @Override
    public void update(int id, TrainingType trainingType) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<TrainingType> getEntities() throws IOException {
        return null;
    }

    @Override
    public void writeEntities() throws IOException {

    }


    @Override
    public void setFilePath(String filePath) {

    }

    @Override
    public String getFilePath() {
        return null;
    }
}
