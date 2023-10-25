package org.example.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TrainingTest {

    @Mock
    private Training training;

    @BeforeEach
    void setUp() {
        training = new Training();
        training.setTrainee(new Trainee());
        training.setTrainer(new Trainer());
        training.setTrainingName("TrainingName");
        training.setTrainingDate(new Date());
        training.setTrainingDuration(1);
        training.setId(1);
    }

    @Test
    void getTrainingDate() {
        Date date = new Date();
        training.setTrainingDate(date);
        assertEquals(date, training.getTrainingDate());
    }

    @Test
    void getTrainingDuration() {
        int trainingDuration = 1;
        assertEquals(training.getTrainingDuration(), trainingDuration);
    }

    @Test
    void testToString() {
        assertTrue(training.toString().contains("Training"));
    }
}