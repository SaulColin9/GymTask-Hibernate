package org.example.configuration.inMemory.storage;

import org.example.model.*;
import org.springframework.beans.factory.InitializingBean;

import java.util.Map;

public interface Storage{


    Map<Integer, User> getUsers();

    Map<Integer, Trainee> getTrainees();

    Map<Integer, Trainer> getTrainers();

    Map<Integer, Training> getTrainings();

    Map<Integer, TrainingType> getTrainingTypes();


}
