package org.example.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class StorageConnectionImpl<T> implements StorageConnection<T> {
    private final Class<T> tClass;
    private static final Logger logger = LoggerFactory.getLogger(StorageConnectionImpl.class);

    public StorageConnectionImpl(Class<T> tClass) {
        this.tClass = tClass;
    }

    @Override
    public T getEntities(String filePath) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(new File(filePath), tClass);

        } catch (IOException e) {
            logger.error("No file path specified for getEntities");
            throw new RuntimeException(e);
        }
    }

}
