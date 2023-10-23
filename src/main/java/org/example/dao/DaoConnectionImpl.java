package org.example.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.example.model.Entity;
import org.example.service.TraineeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DaoConnectionImpl<T extends Entity> implements DaoConnection<T>{

    private Class<T> tClass;
    private static Logger logger = LoggerFactory.getLogger(DaoConnectionImpl.class);


    public DaoConnectionImpl(Class<T> tClass) {
        this.tClass = tClass;
    }

    public List<T> getEntities(String filePath){
        try{
            ObjectMapper mapper = new ObjectMapper();
            CollectionType listType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, tClass );

            InputStream inputStream = new FileInputStream(filePath);
            return mapper.readValue(inputStream, listType);

        } catch (IOException e) {
            logger.error("No file path specified for getEntities");
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
            logger.error("No file path specified for writeEntities");
            throw new RuntimeException(e);
        }
    }
}
