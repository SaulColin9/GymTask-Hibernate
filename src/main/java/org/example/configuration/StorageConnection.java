package org.example.configuration;

import java.io.IOException;

public interface StorageConnection<T> {
    T getEntities(String filePath) throws IOException;
}
