package org.example.configuration;

import java.io.IOException;
import java.util.List;

public interface StorageConnection<T> {
    T getEntities(String filePath) throws IOException;
}
