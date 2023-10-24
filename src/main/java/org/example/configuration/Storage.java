package org.example.configuration;

import org.example.dao.Dao;
import org.example.model.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface Storage {

    void setDaos(Map<String, Dao> tables);

    Dao getDao(String tableName);

    Map<Integer, User> getUsers();

    Map<Integer, Trainee> getTrainees();

    Map<Integer, Trainer> getTrainers();

    Map<Integer, Training> getTrainings();

    Map<Integer, TrainingType> getTrainingTypes();

    List<Entity> writeEntities(String filePath, List<Entity> entities) throws IOException;

}
