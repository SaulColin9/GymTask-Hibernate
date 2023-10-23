package org.example;

import org.example.configuration.BeanConfiguration;
import org.example.service.TraineeService;
import org.example.service.TrainerService;
import org.example.service.TrainingService;
import org.example.service.serviceImpl.TraineeServiceImpl;
import org.example.service.serviceImpl.TrainerServiceImpl;
import org.example.service.serviceImpl.TrainingServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;





public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(BeanConfiguration.class);
        TrainerService trainerService = context.getBean("trainerService", TrainerServiceImpl.class);
        TraineeService traineeService = context.getBean("traineeService", TraineeServiceImpl.class);
        TrainingService trainingService = context.getBean("trainingService", TrainingServiceImpl.class);
        System.out.println(trainingService.selectAll());
        System.out.println(traineeService.selectAll());
        System.out.println(trainerService.selectAll());

    }
}