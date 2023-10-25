package org.example.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

class TrainerTest {

    @Mock
    private Trainer trainer;

    @BeforeEach
    void setUp() {
        trainer = new Trainer(1,  new User());
    }

    @Test
    void getSpecialization() {
        assertEquals(1, trainer.getSpecialization());
    }

    @Test
    void testToString() {
        assertTrue(trainer.toString().contains("Trainer"));
    }
}