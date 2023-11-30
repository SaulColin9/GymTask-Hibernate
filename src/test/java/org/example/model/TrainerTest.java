package org.example.model;

import org.example.entitiesFactory.EntitiesFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.*;


class TrainerTest {

    EntitiesFactory entitiesFactory;
    @Mock
    private Trainer trainer;

    @BeforeEach
    void setUp() {
        entitiesFactory = new EntitiesFactory();
        trainer = new Trainer(entitiesFactory.createNewTrainingType(), new User());
    }


    @Test
    void testToString() {
        assertThat(trainer.toString().contains("Trainer")).isTrue();
    }
}