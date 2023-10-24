package org.example.configuration;

import org.example.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EntitiesReaderTest {

    EntitiesReader entitiesReader;
    @BeforeEach
    void setUp() {
       entitiesReader = new EntitiesReader();
    }

    @Test
    void getUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User());
        entitiesReader.setUsers(users);
        assertInstanceOf(User.class,entitiesReader.getUsers().get(0));

    }

    @Test
    void getTrainers() {
        List<Trainer> trainers = new ArrayList<>();
        trainers.add(new Trainer());
        entitiesReader.setTrainers(trainers);
        assertInstanceOf(Trainer.class,entitiesReader.getTrainers().get(0));
    }

    @Test
    void getTrainees() {
        List<Trainee> trainees = new ArrayList<>();
        trainees.add(new Trainee());
        entitiesReader.setTrainees(trainees);
        assertInstanceOf(Trainee.class,entitiesReader.getTrainees().get(0));
    }

    @Test
    void getTrainings() {
        List<Training> trainings = new ArrayList<>();
        trainings.add(new Training());
        entitiesReader.setTrainings(trainings);
        assertInstanceOf(Training.class,entitiesReader.getTrainings().get(0));
    }

    @Test
    void getTrainingTypes() {
        List<TrainingType> trainingTypes = new ArrayList<>();
        trainingTypes.add(new TrainingType());
        entitiesReader.setTrainingTypes(trainingTypes);
        assertInstanceOf(TrainingType.class,entitiesReader.getTrainingTypes().get(0));
    }
}