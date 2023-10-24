package org.example.configuration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class StorageConnectionImplTest {

    private StorageConnectionImpl<EntitiesReader> storageConnection;

    @BeforeEach
    void setUp() {
        storageConnection = new StorageConnectionImpl<>(EntitiesReader.class);
    }

    @Test
    void getEntities() {
        assertThrows(RuntimeException.class, () -> storageConnection.getEntities(""));
    }

    @Test
    void writeEntities() {
        assertThrows(RuntimeException.class, () -> storageConnection.writeEntities("", new ArrayList<>()));
    }
}