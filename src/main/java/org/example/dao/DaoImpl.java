package org.example.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.InitializingBean;

import java.util.List;
import java.util.Optional;

public abstract class DaoImpl<T> extends DaoConnectionImpl<T> implements Dao<T>, InitializingBean {
    protected List<T> entities;
    public abstract Optional<T> get(int id);
    public abstract void save(T t);
    public abstract void setFilePath(String filePath);
    public abstract  String getFilePath();
    public abstract void afterPropertiesSet();

    public void setEntities(List<T> entities) {
        this.entities = entities;
    }

    @Override
    public List<T> getAll() {
        return entities;
    }

    @Override
    public void update(int id, T t) {
        Optional<T> foundEntity = get(id);
        int listId;
        if(foundEntity.isPresent()){
            listId = entities.indexOf(foundEntity.get());
            entities.set(listId, t);
            writeEntities();
        }
    }

    @Override
    public void delete(int id) {
        Optional<T> foundEntity = get(id);
        int listId;
        if(foundEntity.isPresent()){
            listId = entities.indexOf(foundEntity.get());
            entities.remove(listId);
            writeEntities();
        }
    }

    @Override
    public List<T> getEntities(Class<T> tClass) {
        return getEntities(getFilePath(), tClass);
    }

    @Override
    public void writeEntities() {
        writeEntities(getFilePath(), entities);
    }
}
