package org.example.configuration;

import org.example.dao.Dao;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

import java.util.Map;

@Configuration
@PropertySource(value = "storage.properties")
public class BeanConfiguration {
    @Bean
    public Storage storage(@Value("${entities.source}") String filePath) {
        Storage storage = new GymStorageImpl();
        storage.setFilePath(filePath);

        return storage;
    }

    @Bean
    public GymFacade gymFacade(@Autowired TraineeService traineeService, @Autowired TrainerService trainerService, @Autowired TrainingService trainingService) {
        return new GymFacadeImpl(traineeService, trainerService, trainingService);
    }

    @Bean
    public TrainingService trainingService(@Autowired Dao<User> userDao, @Autowired Dao<Trainee> traineeDao, @Autowired Dao<Trainer> trainerDao, @Autowired Dao<Training> trainingDao, @Autowired Dao<TrainingType> trainingTypeDao) {
        TrainingService trainingService = new TrainingServiceImpl();
        trainingService.setUserDao(userDao);
        trainingService.setTraineeDao(traineeDao);
        trainingService.setTrainerDao(trainerDao);
        trainingService.setTrainingDao(trainingDao);
        trainingService.setTrainingTypeDao(trainingTypeDao);
        return trainingService;
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
    public Dao<User> userDao(@Autowired Storage storage) {
        Dao<User> userDao = new DaoImpl<>(User.class);
        userDao.setStorage(storage);
        return userDao;
    }

    @Bean("trainings")
    public Dao<Training> trainingDao(@Autowired Storage storage) {
        Dao<Training> trainingDao = new DaoImpl<>(Training.class);
        trainingDao.setStorage(storage);
        return trainingDao;
    }

    @Bean("trainingTypes")
    public Dao<TrainingType> trainingTypesDao(@Autowired Storage storage) {
        Dao<TrainingType> trainingTypesDao = new DaoImpl<>(TrainingType.class);
        trainingTypesDao.setStorage(storage);
        return trainingTypesDao;
    }

    @Bean("trainers")
    public Dao<Trainer> trainerDao(@Autowired Storage storage) {
        Dao<Trainer> trainerDao = new DaoImpl<>(Trainer.class);
        trainerDao.setStorage(storage);
        return trainerDao;
    }

    @Bean("trainees")
    public Dao<Trainee> traineeDao(@Autowired Storage storage) {
        Dao<Trainee> traineeDao = new DaoImpl<>(Trainee.class);
        traineeDao.setStorage(storage);
        return traineeDao;
    }


}
