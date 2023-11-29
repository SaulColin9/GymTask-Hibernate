package org.example.service.serviceImpl.jpa;

import org.example.dao.jpa.JpaDaoTrainingImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class JpaTrainingServiceImplTest {
    @Mock
    JpaDaoTrainingImpl daoTraining;
    @InjectMocks
    JpaTrainerServiceImpl jpaTrainerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void selectTraineeTrainingsByUsername() {
    }

    @Test
    void selectTrainerTrainingsByUsername() {
    }
}