package org.example.configuration;

import org.example.configuration.inmemory.InMemoryBeanConfiguration;
import org.example.configuration.jpa.JpaBeanConfiguration;
import org.example.controller.*;
import org.example.exception.RestExceptionHandler;
import org.example.facade.impl.GymFacadeImpl;
import org.example.service.TraineeService;
import org.example.service.TrainerService;
import org.example.service.TrainingService;
import org.example.service.TrainingTypeService;
import org.example.service.authentication.CredentialsAuthenticator;
import org.example.service.serviceimpl.jpa.JpaTraineeService;
import org.example.service.serviceimpl.jpa.JpaTrainerService;
import org.example.service.serviceimpl.jpa.JpaTrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.handler.MappedInterceptor;

@Configuration
@EnableWebMvc
@Import({InMemoryBeanConfiguration.class, JpaBeanConfiguration.class})
public class BeanConfiguration {
    @Bean
    public GymFacadeImpl gymFacade(@Autowired TraineeService traineeService, @Autowired TrainerService trainerService,
                                   @Autowired TrainingService trainingService, @Autowired CredentialsAuthenticator credentialsAuthenticator) {
        GymFacadeImpl gymFacade = new GymFacadeImpl(traineeService, trainerService, trainingService);
        gymFacade.setCredentialsAuthenticator(credentialsAuthenticator);
        return gymFacade;
    }

    @Bean
    public MappedInterceptor mappedInterceptor() {
        return new MappedInterceptor(null, new CustomHttpInterceptor());
    }

    @Bean
    public TraineeController traineeController(@Autowired JpaTraineeService traineeService,
                                               @Autowired JpaTrainingService trainingService,
                                               @Autowired CredentialsAuthenticator credentialsAuthenticator) {
        TraineeController traineeController = new TraineeController();
        traineeController.setTraineeService(traineeService);
        traineeController.setTrainingService(trainingService);
        traineeController.setCredentialsAuthenticator(credentialsAuthenticator);
        return traineeController;
    }

    @Bean
    public TrainerController trainerController(@Autowired JpaTrainerService trainerService,
                                               @Autowired JpaTrainingService trainingService,
                                               @Autowired CredentialsAuthenticator credentialsAuthenticator) {
        TrainerController trainerController = new TrainerController();
        trainerController.setTrainerService(trainerService);
        trainerController.setTrainingService(trainingService);
        trainerController.setCredentialsAuthenticator(credentialsAuthenticator);
        return trainerController;
    }

    @Bean
    public TrainingController trainingController(@Autowired JpaTrainingService trainingService) {
        TrainingController trainingController = new TrainingController();
        trainingController.setTrainingService(trainingService);
        return trainingController;
    }

    @Bean
    public LoginController loginController(@Autowired CredentialsAuthenticator credentialsAuthenticator,
                                           @Autowired JpaTrainerService trainerService,
                                           @Autowired JpaTraineeService traineeService) {
        LoginController loginController = new LoginController();
        loginController.setCredentialsAuthenticator(credentialsAuthenticator);
        loginController.setTraineeService(traineeService);
        loginController.setTrainerService(trainerService);
        return loginController;
    }

    @Bean
    public TrainingTypeController trainingTypeController(@Autowired TrainingTypeService trainingTypeService) {
        TrainingTypeController trainingTypeController = new TrainingTypeController();
        trainingTypeController.setTrainingTypeService(trainingTypeService);
        return trainingTypeController;
    }


    @Bean
    public RestExceptionHandler restExceptionHandler() {
        return new RestExceptionHandler();
    }
}
