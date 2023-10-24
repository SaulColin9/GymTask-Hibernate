package org.example.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dao.Dao;
import org.example.dao.DaoConnectionImpl;
import org.example.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GymStorageImpl implements Storage, InitializingBean {
    private static Logger logger = LoggerFactory.getLogger(DaoConnectionImpl.class);
    private String filePath = "data\\entities.json";
    private String userPath = "data\\users.json";
    private String trainersPath = "data\\trainers.json";
    private String traineesPath = "data\\trainees.json";
    private String trainingsPath = "data\\trainings.json";
    private String trainingTypesPath = "data\\trainingTypes.json";
    private Map<String, Dao> daos = new HashMap<>();
    private Map<Integer, User> users = new HashMap<>();
    private Map<Integer, Trainee> trainees = new HashMap<>();
    private Map<Integer, Trainer> trainers = new HashMap<>();
    private Map<Integer, Training> trainings = new HashMap<>();
    private Map<Integer, TrainingType> trainingTypes = new HashMap<>();
    List<Map<Integer, Object>> entities = new ArrayList<>();

    @Override
    public Map<Integer, User> getUsers() {
        return users;
    }


    @Override
    public Map<Integer, Trainee> getTrainees() {
        return trainees;
    }

    @Override
    public Map<Integer, Trainer> getTrainers() {

        return trainers;
    }

    @Override
    public Map<Integer, Training> getTrainings() {
        return trainings;
    }

    @Override
    public Map<Integer, TrainingType> getTrainingTypes() {
        return trainingTypes;
    }

    @Override
    public List<Entity> writeEntities(String filePath, List<Entity> entities) throws IOException {
        try{
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(new File(filePath), entities);
            return entities;
        }catch (IOException e){
            logger.error("No file path specified for writeEntities");
            throw new RuntimeException(e);
        }
    }


    @Override
    public void setDaos(Map<String, Dao> tables) {
        this.daos = tables;
    }

    @Override
    public Dao getDao(String tableName) {
        return daos.get(tableName);

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        StorageConnection storageConnection = new StorageConnectionImpl(EntitiesReader.class);
        EntitiesReader entitiesReader = (EntitiesReader) storageConnection.getEntities(filePath);
        entitiesReader.getUsers().forEach(user -> users.put(user.getId(),user));
        entitiesReader.getTrainees().forEach(trainee -> trainees.put(trainee.getId(),trainee));
        entitiesReader.getTrainers().forEach(trainer -> trainers.put(trainer.getId(),trainer));
        entitiesReader.getTrainings().forEach(training -> trainings.put(training.getId(),training));
        entitiesReader.getTrainingTypes().forEach(trainingType -> trainingTypes.put(trainingType.getId(),trainingType));

    }
}
