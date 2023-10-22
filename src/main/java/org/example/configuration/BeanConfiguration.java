package org.example.configuration;

import org.example.dao.Dao;
import org.example.dao.DaoConnection;
import org.example.dao.DaoConnectionImpl;
import org.example.dao.daoImpl.*;
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
    public Dao userDao(@Value("${users.source}")String usersPath){
        Dao userDao = new UserDao();
        userDao.setFilePath(usersPath);
        userDao.setDaoConnection(new DaoConnectionImpl<User>());
        return userDao;
    }
    @Bean("trainings")
    public Dao trainingDao(@Value("${trainings.source}")String trainigsPath){
        Dao trainingDao = new TrainingDao();
        trainingDao.setFilePath(trainigsPath);
        trainingDao.setDaoConnection(new DaoConnectionImpl<Training>());
        return trainingDao;
    }

    @Bean("trainingTypes")
    public Dao trainingTypesDao(@Value("${trainingTypes.source}")String trainingTypesPath){
        Dao trainingTypesDao = new TrainingTypeDao();
        trainingTypesDao.setFilePath(trainingTypesPath);
        trainingTypesDao.setDaoConnection( new DaoConnectionImpl<TrainingType>());
        return trainingTypesDao;
    }

    @Bean("trainers")
    public Dao trainerDao(@Value("${trainers.source}") String trainersPath){
        Dao trainerDao = new TrainerDao();
        trainerDao.setFilePath(trainersPath);
        trainerDao.setDaoConnection(new DaoConnectionImpl<Trainer>());
        return trainerDao;
    }

    @Bean("trainees")
    public Dao traineeDao(@Value("${trainees.source}")String traineesPath){
        Dao traineeDao = new TraineeDao();
        traineeDao.setFilePath(traineesPath);
        traineeDao.setDaoConnection(new DaoConnectionImpl<Trainee>());
        return traineeDao;
    }

    @Bean
    public Storage storage(@Autowired Map<String, Dao> tables){
        Storage storage = new StorageImpl();
        storage.setDaos(tables);
        return storage;
    }

}
