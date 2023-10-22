package org.example.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.InitializingBean;

import java.util.List;
import java.util.Optional;

public abstract class DaoImpl<T> extends DaoConnectionImpl<T> implements Dao<T>, InitializingBean {
    protected List<T> entities;
    public abstract void setFilePath(String filePath);
    public abstract  String getFilePath();
    public abstract void afterPropertiesSet();
    public abstract int getNextId();
    public abstract T setId(T t, int id);
    public abstract int getId(T t);

    public void setEntities(List<T> entities) {
        this.entities = entities;
    }

    @Override
    public  Optional<T> get(int id){
        T foundT = null;
        for(T t: entities){
            if(getId(t) == id){
                foundT = t;
            }
        }
        return Optional.ofNullable(foundT);
    }
    @Override
    public List<T> getAll() {
        return entities;
    }
    @Override
    public T save(T t){
        T newT = setId(t, getNextId());
        entities.add(newT);
        writeEntities();
        return newT;

    }

    @Override
    public T update(int id, T t) {
        Optional<T> foundEntity = get(id);
        int listId;
        if(foundEntity.isPresent()){
            listId = entities.indexOf(foundEntity.get());
            entities.set(listId, t);
            writeEntities();
            return foundEntity.get();
        }
        return null;
    }

    @Override
    public Optional<T> delete(int id) {
        Optional<T> foundEntity = get(id);
        int listId;
        if(foundEntity.isPresent()){
            listId = entities.indexOf(foundEntity.get());
            entities.remove(listId);
            writeEntities();
        }
        return foundEntity;
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
