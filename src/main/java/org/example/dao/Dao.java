package org.example.dao;

import org.example.model.EntityModel;

import java.util.List;
import java.util.Optional;

public interface Dao<T extends EntityModel> {
    Optional<T> get(int id);

    List<T> getAll();

    T save(T t);

    T update(int id, T t);

    Optional<T> delete(int id);

}
