package org.example.dao.daoImpl;

import com.fasterxml.jackson.core.type.TypeReference;
import org.example.dao.Dao;
import org.example.model.Trainee;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class TraineeDao implements Dao<Trainee>{

    @Override
    public Optional<Trainee> get(int id) {
        return Optional.empty();
    }

    @Override
    public List<Trainee> getAll() {
        return null;
    }

    @Override
    public void save(Trainee trainee) {

    }

    @Override
    public void update(Trainee trainee) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<Trainee> getEntities(String filePath, TypeReference<List<Trainee>> typeReference) throws IOException {
        return null;
    }

    @Override
    public void writeEntities(String path, List<Trainee> entities) throws IOException {

    }

    @Override
    public void setFilePath(String filePath) {

    }

    @Override
    public String getFilePath() {
        return null;
    }
}
