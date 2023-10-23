package org.example.configuration;

import org.example.dao.Dao;
import org.example.dao.DaoConnection;
import org.example.dao.DaoConnectionImpl;
import org.example.dao.DaoImpl;
import org.example.facade.GymFacade;
import org.example.facade.GymFacadeImpl;
import org.example.model.*;
import org.example.service.TraineeService;
import org.example.service.TrainerService;
import org.example.service.TrainingService;
import org.example.service.serviceImpl.TraineeServiceImpl;
import org.example.service.serviceImpl.TrainerServiceImpl;
import org.example.service.serviceImpl.TrainingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Map;

@Configuration
@PropertySource(value="storage.properties")
public class BeanConfiguration {
    @Bean
    public GymFacade gymFacade(@Autowired TraineeService traineeService, @Autowired TrainerService trainerService, @Autowired TrainingService trainingService){
        return new GymFacadeImpl(traineeService, trainerService, trainingService);
    }
    @Bean
    public TrainingService trainingService(@Autowired Storage storage){
        return new TrainingServiceImpl(storage);
    }
    @Bean
    public TrainerService trainerService (@Autowired Storage storage){
        return new TrainerServiceImpl(storage);
    }
    @Bean
    TraineeService traineeService(@Autowired Storage storage ){
        return new TraineeServiceImpl(storage);
    }
    @Bean("users")
    public Dao<User> userDao(@Value("${users.source}")String usersPath){
        Dao<User> userDao = new DaoImpl<>(User.class);
        userDao.setFilePath(usersPath);
        return userDao;
    }
    @Bean("trainings")
    public Dao<Training> trainingDao(@Value("${trainings.source}")String trainigsPath){
        Dao<Training> trainingDao = new DaoImpl<>(Training.class);
        trainingDao.setFilePath(trainigsPath);
        return trainingDao;
    }

    @Bean("trainingTypes")
    public Dao<TrainingType> trainingTypesDao(@Value("${trainingTypes.source}")String trainingTypesPath){
        Dao<TrainingType> trainingTypesDao = new DaoImpl<>(TrainingType.class);
        trainingTypesDao.setFilePath(trainingTypesPath);
        return trainingTypesDao;
    }

    @Bean("trainers")
    public Dao<Trainer> trainerDao(@Value("${trainers.source}") String trainersPath){
        Dao<Trainer> trainerDao = new DaoImpl<>(Trainer.class);
        trainerDao.setFilePath(trainersPath);
        return trainerDao;
    }

    @Bean("trainees")
    public Dao<Trainee> traineeDao(@Value("${trainees.source}")String traineesPath){
        Dao<Trainee> traineeDao = new DaoImpl<>(Trainee.class);
        traineeDao.setFilePath(traineesPath);
        return traineeDao;
    }

    @Bean
    public Storage storage(@Autowired Map<String, Dao> tables){
        Storage storage = new StorageImpl();
        storage.setDaos(tables);
        return storage;
    }

}
