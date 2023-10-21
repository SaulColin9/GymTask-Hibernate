package org.example.dao;

import com.fasterxml.jackson.core.type.TypeReference;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    Optional<T> get(int id);
    List<T> getAll();
    void save(T t);
    void update(int id, T t);
    void delete(int id);
    List<T> getEntities(Class<T> tClass) throws IOException;
    void writeEntities() throws IOException;
    void setFilePath(String filePath);
    String getFilePath();
}
