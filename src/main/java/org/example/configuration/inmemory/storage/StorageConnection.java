package org.example.configuration.inmemory.storage;

public interface StorageConnection<T> {
    T getEntities(String filePath) throws IllegalArgumentException;
}
