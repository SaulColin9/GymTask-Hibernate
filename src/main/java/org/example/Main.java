package org.example;

import org.example.configuration.BeanConfiguration;
import org.example.facade.impl.GymFacadeImpl;
import org.example.model.Trainee;
import org.example.model.Trainer;
import org.example.model.User;
import org.example.service.authentication.Credentials;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Calendar;
import java.util.Date;


public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.getEnvironment().setActiveProfiles("jpa");
        context.register(BeanConfiguration.class);
        context.refresh();
        GymFacadeImpl gymFacade = context.getBean(GymFacadeImpl.class);

        // Trainer

        // Trainee
        Trainee trainee1 = gymFacade.addTrainee("Saul", "Colin", new Date(), "St 123");
        Credentials trainee1Credentials = new Credentials(trainee1.getUser().getUsername(), trainee1.getUser().getPassword());
        System.out.println(gymFacade.getTrainee(trainee1Credentials, trainee1.getId()));

        // Training
    }
}