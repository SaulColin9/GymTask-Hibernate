package org.example.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.User;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DaoImpl{

//    protected String filePath;
//
//    protected List<T> entities = new ArrayList<>();
//    @Override
//    public void setFilePath(String filePath){
//        this.filePath = filePath;
//    }
//
//    @Override
//    public String getFilePath() {
//        return null;
//    }
//
//    @Override
//    public Optional<T> get(int id) {
//        return Optional.ofNullable(entities.get(id));
//    }
//
//    @Override
//    public List<T> getAll() {
//        return entities;
//    }
//
//    @Override
//    public void save(T t) {
//
//    }
//
//    @Override
//    public void update(T t) {
//        int id = entities.indexOf(t);
//        entities.set(id, t);
//    }
//
//    @Override
//    public void delete(int id) {
//        entities.remove(id);
//        writeEntities(filePath, entities);
//    }
//
//    public List getEntities(String filePath, TypeReference<List<T>> typeReference){
//        try{
//            ObjectMapper mapper = new ObjectMapper();
//            InputStream inputStream = new FileInputStream(filePath);
//            return mapper.readValue(inputStream, typeReference);
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

//    @Override
//    public void writeEntities(String filePath, List<T> entities){
//        try{
//            ObjectMapper mapper = new ObjectMapper();
//            mapper.writeValue(new File(filePath), entities);
//
//        }catch (IOException e){
//            throw new RuntimeException(e);
//        }
//    }

//    public abstract void save(T t);
}
