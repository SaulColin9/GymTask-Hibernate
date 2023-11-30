package org.example.configuration.inmemory.storage;

import org.example.model.*;

import java.util.Map;

public interface Storage{


    Map<Integer, User> getUsers();

    Map<Integer, Trainee> getTrainees();

    Map<Integer, Trainer> getTrainers();

    Map<Integer, Training> getTrainings();

    Map<Integer, TrainingType> getTrainingTypes();


}
