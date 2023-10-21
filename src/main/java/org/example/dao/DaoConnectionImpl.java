package org.example.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class DaoConnectionImpl<T> implements DaoConnection<T>{

    public List<T> getEntities(String filePath, TypeReference<List<T>> typeReference){
        try{
            ObjectMapper mapper = new ObjectMapper();
            InputStream inputStream = new FileInputStream(filePath);
            return mapper.readValue(inputStream, typeReference);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeEntities(String filePath, List<T> entities){
        try{
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(new File(filePath), entities);

        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
