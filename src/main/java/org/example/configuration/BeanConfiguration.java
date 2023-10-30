package org.example.configuration;

import org.example.dao.Dao;
import org.example.dao.DaoImpl;
import org.example.dao.entities.*;
import org.example.facade.GymFacade;
import org.example.facade.GymFacadeImpl;
import org.example.model.*;
import org.example.service.*;
import org.example.service.serviceImpl.TraineeServiceImpl;
import org.example.service.serviceImpl.TrainerServiceImpl;
import org.example.service.serviceImpl.TrainingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;


@Configuration
@PropertySource(value = "storage.properties")
public class BeanConfiguration {
    @Bean
    public Storage storage(@Value("${entities.source}") String filePath) {
        GymStorageImpl gymStorage =  new GymStorageImpl();
        gymStorage.setFilePath(filePath);
        return gymStorage;
    }

    @Bean
    public GymFacade gymFacade(@Autowired TraineeService traineeService, @Autowired TrainerService trainerService,
                               @Autowired TrainingService trainingService) {
        return new GymFacadeImpl(traineeService, trainerService, trainingService);
    }

    @Bean
    public TrainingService trainingService(@Autowired Dao<Trainee> traineeDao,
                                           @Autowired Dao<Trainer> trainerDao, @Autowired Dao<Training> trainingDao,
                                           @Autowired Dao<TrainingType> trainingTypeDao) {
        TrainingServiceImpl trainingService = new TrainingServiceImpl();
        trainingService.setTraineeDao(traineeDao);
        trainingService.setTrainerDao(trainerDao);
        trainingService.setTrainingDao(trainingDao);
        trainingService.setTrainingTypeDao(trainingTypeDao);
        return trainingService;
    }

    @Bean
    public TrainerService trainerService(@Autowired Dao<Trainer> trainerDao, @Autowired UserUtils userUtils) {
        TrainerServiceImpl trainerService = new TrainerServiceImpl();
        trainerService.setTrainerDao(trainerDao);
        trainerService.setUserUtils(userUtils);
        return trainerService;
    }

    @Bean
    TraineeService traineeService(@Autowired Dao<Trainee> traineeDao, @Autowired UserUtils userUtils) {
        TraineeServiceImpl traineeService = new TraineeServiceImpl();
        traineeService.setTraineeDao(traineeDao);
        traineeService.setUserUtils(userUtils);
        return traineeService;
    }

    @Bean("users")
    public Dao<User> userDao(@Autowired Storage storage) {
        DaoImpl<User> userDao = new DaoImpl<>();
        userDao.setStorageEntities(storage.getUsers());
        return userDao;
    }

    @Bean("trainings")
    public Dao<Training> trainingDao(@Autowired Storage storage) {
        Dao<Training> trainingDao = new TrainingDao();
        trainingDao.setStorage(storage);
        return trainingDao;
    }

    @Bean("trainingTypes")
    public Dao<TrainingType> trainingTypesDao(@Autowired Storage storage) {
        Dao<TrainingType> trainingTypesDao = new TrainingTypeDao();
        trainingTypesDao.setStorage(storage);
        return trainingTypesDao;
    }

    @Bean("trainers")
    public Dao<Trainer> trainerDao(@Autowired Storage storage) {
        Dao<Trainer> trainerDao = new TrainerDao();
        trainerDao.setStorage(storage);
        return trainerDao;
    }

    @Bean("trainees")
    public Dao<Trainee> traineeDao(@Autowired Storage storage) {
        Dao<Trainee> traineeDao = new TraineeDao();
        traineeDao.setStorage(storage);
        return traineeDao;
    }

    @Bean
    public PasswordGenerator passwordGenerator() {
        return new PasswordGeneratorImpl();
    }

    @Bean
    UsernameGenerator usernameGenerator(@Autowired Dao<User> userDao) {
        UsernameGeneratorImpl usernameGenerator = new UsernameGeneratorImpl();
        usernameGenerator.setUserDao(userDao);
        return usernameGenerator;
    }

    @Bean
    UserUtils userUtils(@Autowired UsernameGenerator usernameGenerator, @Autowired PasswordGenerator passwordGenerator,
                        @Autowired Dao<User> userDao) {
        UserUtilsImpl userUtils = new UserUtilsImpl();
        userUtils.setUserDao(userDao);
        userUtils.setUsernameGenerator(usernameGenerator);
        userUtils.setPasswordGenerator(passwordGenerator);
        return userUtils;
    }
}
