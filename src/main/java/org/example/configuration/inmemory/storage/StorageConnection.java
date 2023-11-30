package org.example.configuration.inmemory.storage;

import java.io.IOException;

public interface StorageConnection<T> {
    T getEntities(String filePath) throws IOException;
}
