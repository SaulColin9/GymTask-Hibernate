package org.example.configuration;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import io.micrometer.core.instrument.MeterRegistry;
import org.example.configuration.indicator.MaxMemoryHealthIndicator;
import org.example.configuration.indicator.PingHealthIndicator;
import org.example.configuration.inmemory.InMemoryBeanConfiguration;
import org.example.configuration.jpa.JpaBeanConfiguration;
import org.example.configuration.metric.TrainingTypesMetric;
import org.example.configuration.security.JwtIssuer;
import org.example.controller.*;
import org.example.exception.RestExceptionHandler;
import org.example.facade.impl.GymFacadeImpl;
import org.example.interceptor.CustomHttpInterceptor;
import org.example.service.TraineeService;
import org.example.service.TrainerService;
import org.example.service.TrainingService;
import org.example.service.TrainingTypeService;
import org.example.service.authentication.CredentialsAuthenticator;
import org.example.service.serviceimpl.jpa.JpaTraineeService;
import org.example.service.serviceimpl.jpa.JpaTrainerService;
import org.example.service.serviceimpl.jpa.JpaTrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.handler.MappedInterceptor;

import java.time.Duration;

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
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> circuitBreakerFactoryCustomizer() {
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofMillis(1000))
                .slidingWindowSize(2)
                .build();
        TimeLimiterConfig timeLimiterConfig = TimeLimiterConfig.custom()
                .timeoutDuration(Duration.ofSeconds(4))
                .build();
        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                .timeLimiterConfig(timeLimiterConfig)
                .circuitBreakerConfig(circuitBreakerConfig)
                .build());
    }


    @Bean
    MaxMemoryHealthIndicator maxMemoryHealthIndicator() {
        return new MaxMemoryHealthIndicator();
    }

    @Bean
    PingHealthIndicator serverHealthIndicator() {
        return new PingHealthIndicator();
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
                                           @Autowired JpaTraineeService traineeService, @Autowired JwtIssuer jwtIssuer) {

        LoginController loginController = new LoginController();
        loginController.setCredentialsAuthenticator(credentialsAuthenticator);
        loginController.setTraineeService(traineeService);
        loginController.setTrainerService(trainerService);
        loginController.setJwtIssuer(jwtIssuer);
        return loginController;
    }

    @Bean
    public TrainingTypeController trainingTypeController(@Autowired TrainingTypeService trainingTypeService,
                                                         @Autowired MeterRegistry registry) {
        TrainingTypeController trainingTypeController = new TrainingTypeController(registry);
        trainingTypeController.setTrainingTypeService(trainingTypeService);
        return trainingTypeController;
    }

    @Bean
    public TrainingTypesMetric trainingTypesMetric(@Autowired MeterRegistry registry,
                                                   @Autowired TrainingTypeController trainingTypeController) {
        TrainingTypesMetric metric = new TrainingTypesMetric(registry);
        metric.setTrainingTypeController(trainingTypeController);
        return metric;
    }

    @Bean
    public RestExceptionHandler restExceptionHandler() {
        return new RestExceptionHandler();
    }
}
