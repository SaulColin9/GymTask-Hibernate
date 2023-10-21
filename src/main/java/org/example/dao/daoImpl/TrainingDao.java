package org.example.dao.daoImpl;

import org.example.dao.Dao;
import org.example.model.Training;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class TrainingDao implements Dao<Training>{

    @Override
    public Optional<Training> get(int id) {
        return Optional.empty();
    }

    @Override
    public List<Training> getAll() {
        return null;
    }

    @Override
    public void save(Training training) {

    }

    @Override
    public void update(int id, Training training) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<Training> getEntities(Class<Training> tClass) throws IOException {
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
