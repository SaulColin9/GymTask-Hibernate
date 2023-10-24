package org.example.configuration;

import org.example.dao.Dao;
import org.example.model.*;
import org.springframework.beans.factory.InitializingBean;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface Storage extends InitializingBean {

    void setFilePath(String filePath);

    Map<Integer, User> getUsers();

    Map<Integer, Trainee> getTrainees();

    Map<Integer, Trainer> getTrainers();

    Map<Integer, Training> getTrainings();

    Map<Integer, TrainingType> getTrainingTypes();

    List<Entity> writeEntities(String filePath, List<Entity> entities) throws IOException;

}
