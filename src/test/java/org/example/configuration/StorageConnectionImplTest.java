package org.example.configuration;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class StorageConnectionImplTest {

    @Test
    void getEntities() {
        StorageConnectionImpl<EntitiesReader> storageConnection =
                new StorageConnectionImpl<>(EntitiesReader.class);
        assertThrows(RuntimeException.class, () -> storageConnection.getEntities(""));
    }

}