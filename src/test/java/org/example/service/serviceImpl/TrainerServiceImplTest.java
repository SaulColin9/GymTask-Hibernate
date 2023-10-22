package org.example.service.serviceImpl;

import org.example.model.Trainer;
import org.junit.jupiter.api.Test;
import org.example.model.Training;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class TrainerServiceImplTest {

    private TrainerServiceImpl mock = mock(TrainerServiceImpl.class);

    @Test
    void createTrainerProfile() {
        Trainer trainerTest = new Trainer(1, 1);
        when(mock.createTrainerProfile("Test Name", "Test LastName", 1)).thenReturn(trainerTest);
        Trainer trainerCreated = mock.createTrainerProfile("Test Name", "Test LastName", 1);
        assertNotNull(trainerCreated);
        verify(mock, times(1)).createTrainerProfile("Test Name", "Test LastName", 1);

    }

//    @Test
//    void updateTrainerProfile() {
//    }
//
    @Test
    void selectTrainerProfile() {
        Trainer trainerTest = new Trainer(1, 1);
        when(mock.selectTrainerProfile(1)).thenReturn(Optional.of(trainerTest));
        Optional<Trainer> selectedTrainer = mock.selectTrainerProfile(1);
        assertNotNull(selectedTrainer);
    }

    @Test
    void selectAll() {
        List<Trainer> trainerTests = new ArrayList<>();
        trainerTests.add(new Trainer(1,1));
        trainerTests.add(new Trainer(2,2));
        when(mock.selectAll()).thenReturn(trainerTests);
        List<Trainer> trainers = mock.selectAll();
        assertNotNull(trainers);
    }
}