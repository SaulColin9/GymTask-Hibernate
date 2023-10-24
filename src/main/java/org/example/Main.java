package org.example;

import org.example.configuration.BeanConfiguration;
import org.example.facade.GymFacade;
import org.example.facade.GymFacadeImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(BeanConfiguration.class);
        GymFacade gymFacade = context.getBean("gymFacade", GymFacadeImpl.class);
        System.out.println(gymFacade.getTrainee(1));
        System.out.println(gymFacade.getTrainer(1));
        System.out.println(gymFacade.getTraining(1));
    }
}