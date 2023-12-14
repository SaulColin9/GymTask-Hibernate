package org.example.dao.inmemory;

import org.example.configuration.inmemory.storage.Storage;
import org.example.dao.Dao;
import org.example.model.EntityModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class DaoImpl<T extends EntityModel> implements Dao<T> {
    private Map<Integer, T> storageEntities;

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

    public abstract void setStorage(Storage storage);

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
