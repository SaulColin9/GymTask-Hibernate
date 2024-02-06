package org.example.configuration.jpa;

import jakarta.persistence.EntityManager;
import org.example.dao.Dao;
import org.example.dao.jpa.*;
import org.example.model.*;
import org.example.service.TrainingTypeService;
import org.example.service.authentication.CredentialsAuthenticator;
import org.example.service.authentication.JpaCredentialsAuthenticator;
import org.example.service.serviceimpl.TrainingTypeServiceImpl;
import org.example.service.serviceimpl.jpa.*;
import org.example.service.utils.*;
import org.example.service.utils.user.JpaUserUtils;
import org.example.service.utils.user.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("jpa")
@ComponentScan("org.example.service.authentication")
public class JpaBeanConfiguration {

    @Bean
    CredentialsAuthenticator credentialsAuthenticator(@Autowired(required = false) EntityManager entityManager) {
        JpaCredentialsAuthenticator credentialsAuthenticator = new JpaCredentialsAuthenticator();
        credentialsAuthenticator.setEntityManager(entityManager);
        return credentialsAuthenticator;
    }

    @Bean
    public JpaDaoUserImpl jpaDaoUser(@Autowired(required = false) EntityManager entityManager) {
        JpaDaoUserImpl jpaDaoUser = new JpaDaoUserImpl();
        jpaDaoUser.setEntityManager(entityManager);
        return jpaDaoUser;
    }

    @Bean
    public JpaDaoTrainerImpl jpaDaoTrainer(@Autowired(required = false) EntityManager entityManager) {
        JpaDaoTrainerImpl jpaDaoTrainer = new JpaDaoTrainerImpl();
        jpaDaoTrainer.setEntityManager(entityManager);
        return jpaDaoTrainer;
    }

    @Bean
    public JpaDaoTraineeImpl jpaDaoTrainee(@Autowired(required = false) EntityManager entityManager) {
        JpaDaoTraineeImpl jpaDaoTrainee = new JpaDaoTraineeImpl();
        jpaDaoTrainee.setEntityManager(entityManager);
        return jpaDaoTrainee;
    }

    @Bean
    public JpaDaoTrainingImpl jpaDaoTraining(@Autowired(required = false) EntityManager entityManager) {
        JpaDaoTrainingImpl jpaDaoTraining = new JpaDaoTrainingImpl();
        jpaDaoTraining.setEntityManager(entityManager);
        return jpaDaoTraining;
    }

    @Bean
    public JpaDaoTrainingTypeImpl jpaDaoTrainingType(@Autowired(required = false) EntityManager entityManager) {
        JpaDaoTrainingTypeImpl jpaDaoTrainingType = new JpaDaoTrainingTypeImpl();
        jpaDaoTrainingType.setEntityManager(entityManager);
        return jpaDaoTrainingType;
    }


    @Bean
    public JpaTrainingService trainingService(@Autowired Dao<Trainee> traineeDao,
                                              @Autowired Dao<Trainer> trainerDao,
                                              @Autowired Dao<Training> trainingDao,
                                              @Autowired Dao<TrainingType> trainingTypeDao
    ) {
        JpaTrainingServiceImpl trainingService = new JpaTrainingServiceImpl();
        trainingService.setTraineeDao(traineeDao);
        trainingService.setTrainerDao(trainerDao);
        trainingService.setTrainingDao(trainingDao);
        trainingService.setTrainingTypeDao(trainingTypeDao);
        trainingService.setValidator(new Validator());
        return trainingService;
    }

    @Bean
    public TrainingTypeService trainingTypeService(@Autowired Dao<TrainingType> trainingTypeDao) {
        TrainingTypeServiceImpl trainingTypeService = new TrainingTypeServiceImpl();
        trainingTypeService.setTrainingTypeDao(trainingTypeDao);
        return trainingTypeService;
    }

    @Bean
    public JpaTrainerService trainerService(@Autowired Dao<Trainer> trainerDao,
                                            @Autowired Dao<TrainingType> trainingTypeDao,
                                            @Autowired UserUtils userUtils) {
        JpaTrainerServiceImpl trainerService = new JpaTrainerServiceImpl();
        trainerService.setTrainerDao(trainerDao);
        trainerService.setTrainingTypeDao(trainingTypeDao);
        trainerService.setUserUtils(userUtils);
        trainerService.setValidator(new Validator());
        return trainerService;
    }

    @Bean
    JpaTraineeService traineeService(@Autowired Dao<Trainee> traineeDao, @Autowired UserUtils userUtils) {
        JpaTraineeServiceImpl traineeService = new JpaTraineeServiceImpl();
        traineeService.setTraineeDao(traineeDao);
        traineeService.setUserUtils(userUtils);
        traineeService.setValidator(new Validator());
        return traineeService;
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
        JpaUserUtils userUtils = new JpaUserUtils();
        userUtils.setUserDao(userDao);
        userUtils.setUsernameGenerator(usernameGenerator);
        userUtils.setPasswordGenerator(passwordGenerator);
        return userUtils;
    }

}
