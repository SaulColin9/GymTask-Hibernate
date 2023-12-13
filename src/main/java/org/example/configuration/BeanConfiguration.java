package org.example.configuration;

import org.example.configuration.inmemory.InMemoryBeanConfiguration;
import org.example.configuration.jpa.JpaBeanConfiguration;
import org.example.facade.SimpleGymFacade;
import org.example.facade.impl.GymFacadeImpl;
import org.example.service.TraineeService;
import org.example.service.TrainerService;
import org.example.service.TrainingService;
import org.example.service.authentication.CredentialsAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({InMemoryBeanConfiguration.class, JpaBeanConfiguration.class})
public class BeanConfiguration {
    @Bean
    public GymFacadeImpl gymFacade(@Autowired TraineeService traineeService, @Autowired TrainerService trainerService,
                                   @Autowired TrainingService trainingService, @Autowired CredentialsAuthenticator credentialsAuthenticator) {
        GymFacadeImpl gymFacade = new GymFacadeImpl(traineeService, trainerService, trainingService);
        gymFacade.setCredentialsAuthenticator(credentialsAuthenticator);
        return gymFacade;
    }
}
