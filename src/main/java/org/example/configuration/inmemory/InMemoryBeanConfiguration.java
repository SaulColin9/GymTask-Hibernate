package org.example.configuration.inmemory;

import org.example.configuration.inmemory.storage.GymStorageImpl;
import org.example.configuration.inmemory.storage.Storage;
import org.example.dao.Dao;
import org.example.dao.inmemory.*;
import org.example.model.*;
import org.example.service.TraineeService;
import org.example.service.TrainerService;
import org.example.service.TrainingService;
import org.example.service.authentication.CredentialsAuthenticator;
import org.example.service.authentication.InMemoryCredentialsAuthenticator;
import org.example.service.serviceimpl.TraineeServiceImpl;
import org.example.service.serviceimpl.TrainerServiceImpl;
import org.example.service.serviceimpl.TrainingServiceImpl;
import org.example.service.utils.*;
import org.example.service.utils.user.UserUtils;
import org.example.service.utils.user.InMemoryUserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;


@Configuration
@PropertySource(value = "classpath:storage.properties")
@Profile("inMemory")
public class InMemoryBeanConfiguration {
    @Bean
    public Storage storage(@Value("${entities.inMemory.source}") String filePath) {
        GymStorageImpl gymStorage = new GymStorageImpl();
        gymStorage.setFilePath(filePath);
        return gymStorage;
    }

    @Bean
    public TrainingService trainingService(@Autowired Dao<Trainee> traineeDao,
                                           @Autowired Dao<Trainer> trainerDao,
                                           @Autowired Dao<Training> trainingDao,
                                           @Autowired Dao<TrainingType> trainingTypeDao
    ) {
        TrainingServiceImpl trainingService = new TrainingServiceImpl();
        trainingService.setTraineeDao(traineeDao);
        trainingService.setTrainerDao(trainerDao);
        trainingService.setTrainingDao(trainingDao);
        trainingService.setTrainingTypeDao(trainingTypeDao);
        trainingService.setValidator(new Validator());
        return trainingService;
    }

    @Bean
    public TrainerService trainerService(@Autowired Dao<Trainer> trainerDao,
                                         @Autowired Dao<TrainingType> trainingTypeDao,
                                         @Autowired UserUtils userUtils) {
        TrainerServiceImpl trainerService = new TrainerServiceImpl();
        trainerService.setTrainerDao(trainerDao);
        trainerService.setTrainingTypeDao(trainingTypeDao);
        trainerService.setUserUtils(userUtils);
        trainerService.setValidator(new Validator());
        return trainerService;
    }

    @Bean
    TraineeService traineeService(@Autowired Dao<Trainee> traineeDao, @Autowired UserUtils userUtils) {
        TraineeServiceImpl traineeService = new TraineeServiceImpl();
        traineeService.setTraineeDao(traineeDao);
        traineeService.setUserUtils(userUtils);
        traineeService.setValidator(new Validator());
        return traineeService;
    }

    @Bean("users")
    public Dao<User> userDao(@Autowired Storage storage) {
        DaoImpl<User> userDao = new UserDao();
        userDao.setStorageEntities(storage.getUsers());
        return userDao;
    }

    @Bean("trainings")
    public Dao<Training> trainingDao(@Autowired Storage storage) {
        DaoImpl<Training> trainingDao = new TrainingDao();
        trainingDao.setStorage(storage);
        return trainingDao;
    }

    @Bean("trainingTypes")
    public Dao<TrainingType> trainingTypesDao(@Autowired Storage storage) {
        DaoImpl<TrainingType> trainingTypesDao = new TrainingTypeDao();
        trainingTypesDao.setStorage(storage);
        return trainingTypesDao;
    }

    @Bean("trainers")
    public Dao<Trainer> trainerDao(@Autowired Storage storage) {
        DaoImpl<Trainer> trainerDao = new TrainerDao();
        trainerDao.setStorage(storage);
        return trainerDao;
    }

    @Bean("trainees")
    public Dao<Trainee> traineeDao(@Autowired Storage storage) {
        DaoImpl<Trainee> traineeDao = new TraineeDao();
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
    UserUtils userUtils(@Autowired UsernameGenerator usernameGenerator,
                        @Autowired PasswordGenerator passwordGenerator,
                        @Autowired Dao<User> userDao) {
        InMemoryUserUtils userUtils = new InMemoryUserUtils();
        userUtils.setUserDao(userDao);
        userUtils.setUsernameGenerator(usernameGenerator);
        userUtils.setPasswordGenerator(passwordGenerator);
        return userUtils;
    }

    @Bean
    CredentialsAuthenticator credentialsAuthenticator(@Autowired Storage storage) {
        InMemoryCredentialsAuthenticator credentialsAuthenticator = new InMemoryCredentialsAuthenticator();
        credentialsAuthenticator.setStorageEntities(storage.getUsers());
        return credentialsAuthenticator;
    }
}
