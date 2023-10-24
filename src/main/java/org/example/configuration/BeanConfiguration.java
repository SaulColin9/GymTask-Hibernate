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
import org.springframework.context.annotation.*;

import java.util.Map;

@Configuration
@PropertySource(value = "storage.properties")
public class BeanConfiguration {
    @Bean
    public Storage storage() {
        Storage storage = new GymStorageImpl();
        return storage;
    }

    @Bean
    public GymFacade gymFacade(@Autowired TraineeService traineeService, @Autowired TrainerService trainerService, @Autowired TrainingService trainingService) {
        return new GymFacadeImpl(traineeService, trainerService, trainingService);
    }

    @Bean
    public TrainingService trainingService(@Autowired Storage storage) {
        return new TrainingServiceImpl(storage);
    }

    @Bean
    public TrainerService trainerService(@Autowired Storage storage, @Autowired Dao<Trainer> trainerDao, @Autowired Dao<User> userDao) {
        TrainerService trainerService = new TrainerServiceImpl();
        trainerService.setTrainerDao(trainerDao);
        trainerService.setUserDao(userDao);
        return trainerService;
    }

    @Bean
    TraineeService traineeService(@Autowired Storage storage, @Autowired Dao<Trainee> traineeDao, @Autowired Dao<User> userDao) {
        TraineeService traineeService = new TraineeServiceImpl();
        traineeService.setTraineeDao(traineeDao);
        traineeService.setUserDao(userDao);
        return traineeService;
    }

    @Bean("users")
    public Dao<User> userDao(@Value("${users.source}") String usersPath, @Autowired Storage storage) {
        System.out.println(storage);
        Dao<User> userDao = new DaoImpl<>(User.class);
        System.out.println(storage);
        userDao.setStorage(storage);
        return userDao;
    }

    @Bean("trainings")
    public Dao<Training> trainingDao(@Value("${trainings.source}") String trainigsPath, @Autowired Storage storage) {
        Dao<Training> trainingDao = new DaoImpl<>(Training.class);
        trainingDao.setStorage(storage);
        return trainingDao;
    }

    @Bean("trainingTypes")
    public Dao<TrainingType> trainingTypesDao(@Value("${trainingTypes.source}") String trainingTypesPath, @Autowired Storage storage) {
        Dao<TrainingType> trainingTypesDao = new DaoImpl<>(TrainingType.class);
        trainingTypesDao.setStorage(storage);
        return trainingTypesDao;
    }

    @Bean("trainers")
    public Dao<Trainer> trainerDao(@Value("${trainers.source}") String trainersPath, @Autowired Storage storage) {
        Dao<Trainer> trainerDao = new DaoImpl<>(Trainer.class);
        trainerDao.setStorage(storage);
        return trainerDao;
    }

    @Bean("trainees")
    public Dao<Trainee> traineeDao(@Value("${trainees.source}") String traineesPath, @Autowired Storage storage) {
        Dao<Trainee> traineeDao = new DaoImpl<>(Trainee.class);
        traineeDao.setStorage(storage);
        return traineeDao;
    }


}
