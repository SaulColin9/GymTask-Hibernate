package org.example.configuration;

import org.example.configuration.inmemory.storage.EntitiesReader;
import org.example.configuration.inmemory.storage.StorageConnectionImpl;
import org.example.model.Trainee;
import org.example.model.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class StorageConnectionImplTest {

    @Test
    void givenInvalidFilePath_ThrowsException() {
        String filePath = "";
        StorageConnectionImpl<EntitiesReader> storageConnection =
                new StorageConnectionImpl<>(EntitiesReader.class);
        assertThatThrownBy(() -> storageConnection.getEntities(filePath)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void givenValidFilePath_ShouldReturnEntityReader() {
        // arrange
        User newUser = new User();
        newUser.setId(1);
        newUser.setFirstName("Annadiane");
        newUser.setLastName("UpdatedLast");
        newUser.setUsername("Updated.UpdatedLast");
        newUser.setPassword("uP8>Ly1UA%M/");
        newUser.setIsActive(false);

        Trainee newTrainee = new Trainee();
        newTrainee.setAddress("Address");
        newTrainee.setDateOfBirth(new Date(1698096231546L));
        newTrainee.setUser(newUser);
        newTrainee.setId(1);

        List<Trainee> expectedTrainees = new ArrayList<>();
        expectedTrainees.add(newTrainee);
        String filePath = "src\\test\\resources\\data\\entities.json";

        StorageConnectionImpl<EntitiesReader> storageConnection =
                new StorageConnectionImpl<>(EntitiesReader.class);
        // act
        EntitiesReader actualResponse = storageConnection.getEntities(filePath);

        // assert
        assertThat(actualResponse.getTrainees()).isEqualTo(expectedTrainees);
    }

}