package org.example.dao.daoImpl;

import com.fasterxml.jackson.core.type.TypeReference;
import org.example.dao.Dao;
import org.example.model.Trainee;
import org.example.model.Trainer;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class TrainerDao implements Dao<Trainer>{


    @Override
    public Optional<Trainer> get(int id) {
        return Optional.empty();
    }

    @Override
    public List<Trainer> getAll() {
        return null;
    }

    @Override
    public void save(Trainer trainer) {

    }

    @Override
    public void update(Trainer trainer) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<Trainer> getEntities(String filePath, TypeReference<List<Trainer>> typeReference) throws IOException {
        return null;
    }

    @Override
    public void writeEntities(String path, List<Trainer> entities) throws IOException {

    }

    @Override
    public void setFilePath(String filePath) {

    }

    @Override
    public String getFilePath() {
        return null;
    }
}
