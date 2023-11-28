package org.example.configuration.inMemory.storage;

import java.io.IOException;

public interface StorageConnection<T> {
    T getEntities(String filePath) throws IOException;
}
