package org.example;

import org.example.configuration.BeanConfiguration;
import org.example.facade.GymFacade;
import org.example.facade.GymFacadeImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Date;


public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(BeanConfiguration.class);
        GymFacade gymFacade = context.getBean("gymFacade", GymFacadeImpl.class);
        System.out.println(gymFacade.getTrainee(1));
        int newTrianeeId = gymFacade.addTrainee(null, "Last Name", new Date(), "Test Address");
        gymFacade.updateTrainee(2, "Updated", "Trainee", true, new Date(), "address");
        System.out.println(gymFacade.getTrainee(newTrianeeId));


        System.out.println(gymFacade.getTraining(1));
    }
}