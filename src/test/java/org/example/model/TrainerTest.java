package org.example.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.*;


class TrainerTest {

    @Mock
    private Trainer trainer;

    @BeforeEach
    void setUp() {
        trainer = new Trainer(1, new User());
    }

    @Test
    void getSpecialization() {
        assertThat(trainer.getSpecialization()).isEqualTo(1);
    }

    @Test
    void testToString() {
        assertThat(trainer.toString().contains("Trainer")).isTrue();
    }
}