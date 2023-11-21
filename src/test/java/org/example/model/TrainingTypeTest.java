package org.example.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;


class TrainingTypeTest {
    @Mock
    private TrainingType trainingType;

    @BeforeEach
    void setUp() {
        trainingType = new TrainingType("Test Training Type");
        trainingType.setId(1);
    }

    @Test
    void getTrainingTypeName() {
        assertThat(trainingType.getTrainingTypeName()).isEqualTo("Test Training Type");
    }


    @Test
    void testToString() {
        assertThat(trainingType.toString().contains("TrainingType")).isTrue();
    }
}