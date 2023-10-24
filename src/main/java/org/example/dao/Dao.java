package org.example.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import org.example.configuration.Storage;
import org.example.model.Entity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface Dao<T extends Entity> {
    Optional<T> get(int id);
    void setStorage(Storage storage);
    int getNextId();
    List<T> getAll();
    T save(T t);
    T update(int id, T t);
    Optional<T> delete(int id);

}
