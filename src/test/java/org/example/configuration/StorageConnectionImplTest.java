package org.example.configuration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class StorageConnectionImplTest {

    @Test
    void getEntities() {
        StorageConnectionImpl<EntitiesReader> storageConnection =
                new StorageConnectionImpl<>(EntitiesReader.class);
        assertThrows(RuntimeException.class, () -> storageConnection.getEntities(""));
    }

    @Test
    void writeEntities() {
        StorageConnectionImpl<EntitiesReader> storageConnection =
                new StorageConnectionImpl<>(EntitiesReader.class);
        assertThrows(RuntimeException.class, () -> storageConnection.writeEntities("", new ArrayList<>()));
    }
}