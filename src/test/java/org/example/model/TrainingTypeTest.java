package org.example.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TrainingTypeTest {
    @Mock
    private TrainingType trainingType;

    @BeforeEach
    void setUp(){
        trainingType = new TrainingType("Test Training Type");
        trainingType.setId(1);
    }
    @Test
    void getTrainingTypeName() {
        assertEquals(trainingType.getTrainingTypeName(), "Test Training Type");
    }

    @Test
    void testToString() {
        assertTrue(trainingType.toString().contains("TrainingType"));
    }
}