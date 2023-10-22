package org.example.dao;

import org.example.model.Entity;
import org.springframework.beans.factory.InitializingBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DaoImpl<T extends Entity>  implements Dao<T>, InitializingBean {
    protected String filePath;
    final Class<T> tClass;
    protected List<T> entities = new ArrayList<>();
    protected  DaoConnection<T> daoConnection = new DaoConnectionImpl<>();

    public DaoImpl(Class<T> tClass) {
        this.tClass = tClass;
    }

    public void setFilePath(String filePath){
        this.filePath = filePath;
    }
    public String getFilePath(){
        return filePath;
    }
    public void afterPropertiesSet(){
        setEntities(getEntities(tClass));
    }
    public int getNextId(){
        if(entities.size()== 0){
            return 0;
        }
        return entities.get(entities.size() - 1).getId() + 1;
    }
    public T setId(T t, int id){
        t.setId(id);
        return t;
    }
    public int getId(T t){
        return t.getId();
    }
    @Override
    public void setDaoConnection(DaoConnection<T> daoConnection){
        this.daoConnection = daoConnection;
    }
    @Override
    public DaoConnection<T> getDaoConnection(){
        return this.daoConnection;
    }

    public void setEntities(List<T> entities) {
        this.entities = entities;
    }

    @Override
    public  Optional<T> get(int id){
        T foundT = null;
        for(T t: entities){
            if(t.getId() == id){
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
    public List<T> getEntities(Class<T> tClass){
        try {
            return getDaoConnection().getEntities(getFilePath(), tClass);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeEntities() {
        try {
            getDaoConnection().writeEntities(getFilePath(), entities);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
