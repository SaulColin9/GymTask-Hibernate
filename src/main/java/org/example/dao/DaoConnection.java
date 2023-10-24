package org.example.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import org.example.model.Entity;

import java.io.IOException;
import java.util.List;

public interface DaoConnection<T extends Entity> {
    List<T> getEntities(String filePath) throws IOException;

}
