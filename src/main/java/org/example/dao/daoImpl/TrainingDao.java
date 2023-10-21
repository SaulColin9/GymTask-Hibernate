package org.example.dao.daoImpl;

import com.fasterxml.jackson.core.type.TypeReference;
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
    public void update(Training training) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<Training> getEntities(String filePath, TypeReference<List<Training>> typeReference) throws IOException {
        return null;
    }

    @Override
    public void writeEntities(String path, List<Training> entities) throws IOException {

    }

    @Override
    public void setFilePath(String filePath) {

    }

    @Override
    public String getFilePath() {
        return null;
    }
}
