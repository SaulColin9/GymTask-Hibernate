package org.example.configuration;

import org.example.dao.Dao;
import org.example.model.*;

import java.util.Map;

public interface Storage {

      void setDaos(Map<String, Dao> tables);
      Dao getDao(String tableName);
      Dao<User> getUserDao();
      Dao<Trainee> getTraineeDao();
      Dao<Trainer> getTrainerDao();
      Dao<Training> getTrainingDao();
      Dao<TrainingType> getTrainingTypeDao();

}
