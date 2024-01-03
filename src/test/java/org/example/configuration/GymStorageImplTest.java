package org.example.configuration;

import org.example.configuration.inmemory.storage.GymStorageImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThatThrownBy;


class GymStorageImplTest {

    @InjectMocks
    GymStorageImpl gymStorage;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void givenInvalidFilePath_ShouldThrowException() {
        // arrange
        gymStorage.setFilePath(null);
        assertThatThrownBy(() -> gymStorage.afterPropertiesSet()).isInstanceOf(Exception.class);
    }


}