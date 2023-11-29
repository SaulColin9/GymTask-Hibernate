package org.example.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Date;

import static org.assertj.core.api.Assertions.*;

class TrainingTest {

    @Mock
    private Training training;

    @BeforeEach
    void setUp() {
        training = new Training();
        training.setTrainee(new Trainee(new Date(), "Address"));
        training.setTrainer(new Trainer(1));
        training.setTrainingType(new TrainingType("TrainingTypeName"));
        training.setTrainingName("TrainingName");
        training.setTrainingDate(new Date());
        training.setTrainingDuration(1);
        training.setId(1);
    }

    @Test
    void getTrainingDate() {
        Date date = new Date();
        training.setTrainingDate(date);
        assertThat(training.getTrainingDate()).isEqualTo(date);
    }

    @Test
    void getTrainingDuration() {
        int trainingDuration = 1;
        assertThat(training.getTrainingDuration()).isEqualTo(trainingDuration);
    }


    @Test
    void getTrainee() {
        assertThat(training.getTrainee()).isNotNull();
    }

    @Test
    void getTrainer() {
        assertThat(training.getTrainer()).isNotNull();
    }

    @Test
    void getTrainingType() {
        assertThat(training.getTrainingType()).isNotNull();
    }

    @Test
    void testToString() {
        assertThat(training.toString().contains("Training")).isTrue();
    }
}