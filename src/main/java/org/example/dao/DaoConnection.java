package org.example.dao;

import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.util.List;

public interface DaoConnection<T> {
    List<T> getEntities(String filePath, TypeReference<List<T>> typeReference) throws IOException;
    void writeEntities(String path, List<T> entities) throws IOException;

}
