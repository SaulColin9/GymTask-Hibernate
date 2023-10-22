package org.example.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DaoConnectionImpl<T> implements DaoConnection<T>{

    public List<T> getEntities(String filePath, Class<T> tClass){
        try{
            ObjectMapper mapper = new ObjectMapper();
            CollectionType listType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, tClass );

            InputStream inputStream = new FileInputStream(filePath);
            return mapper.readValue(inputStream, listType);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<T> writeEntities(String filePath, List<T> entities){
        try{
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(new File(filePath), entities);
            return entities;
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
