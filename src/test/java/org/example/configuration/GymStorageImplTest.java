package org.example.configuration;

import org.example.configuration.inMemory.storage.GymStorageImpl;
import org.example.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.*;


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
        String filePath = null;
        gymStorage.setFilePath(filePath);
        assertThatThrownBy(() -> gymStorage.afterPropertiesSet()).isInstanceOf(Exception.class);
    }

    @Test
    void givenValidFilePath_MapEntitiesAreFilled() {
        try {
            // arrange
            User firstUser = new User();
            firstUser.setId(1);
            firstUser.setFirstName("Annadiane");
            firstUser.setLastName("UpdatedLast");
            firstUser.setUsername("Updated.UpdatedLast");
            firstUser.setPassword("uP8>Ly1UA%M/");
            firstUser.setIsActive(false);


            String filePath = "src\\test\\resources\\data\\entities.json";
            gymStorage.setFilePath(filePath);
            // act
            gymStorage.afterPropertiesSet();

            // assert
            assertThat(gymStorage.getUsers().get(1)).isEqualTo(firstUser);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }

    }
}