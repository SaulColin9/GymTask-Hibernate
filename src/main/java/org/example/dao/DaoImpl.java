package org.example.dao;

import org.example.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class DaoImpl<T extends Entity> implements Dao<T> {
    private Map<Integer, T> storageEntities;

    @Override
    public int getNextId() {
        if (storageEntities.isEmpty()) {
            return 1;
        } else {
            int maxId = storageEntities.keySet().stream()
                    .max(Integer::compare)
                    .orElse(0);
            return maxId + 1;
        }
    }

    @Override
    public Optional<T> get(int id) {
        T foundT = null;
        for (T t : storageEntities.values()) {
            if (t.getId() == id) {
                foundT = t;
            }
        }
        return Optional.ofNullable(foundT);
    }

    @Override
    public List<T> getAll() {
        return new ArrayList<>(storageEntities.values());
    }

    @Override
    public T save(T t) {
        t.setId(getNextId());
        storageEntities.put(t.getId(), t);
        return t;

    }

    @Override
    public T update(int id, T t) {
        Optional<T> foundEntity = get(id);
        if (foundEntity.isPresent()) {
            t.setId(id);
            storageEntities.put(id, t);
            return t;
        }
        return null;
    }

    @Override
    public Optional<T> delete(int id) {
        Optional<T> foundEntity = get(id);
        if (foundEntity.isPresent()) {
            storageEntities.remove(id);
        }
        return foundEntity;
    }

    public void setStorageEntities(Map<Integer, T> storageEntities) {
        this.storageEntities = storageEntities;
    }
}
