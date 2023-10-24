package org.example;
import org.example.configuration.BeanConfiguration;
import org.example.dao.DaoImpl;
import org.example.facade.GymFacade;
import org.example.facade.GymFacadeImpl;
import org.example.model.User;
import org.example.service.TraineeService;
import org.example.service.TrainerService;
import org.example.service.TrainingService;
import org.example.service.serviceImpl.TraineeServiceImpl;
import org.example.service.serviceImpl.TrainerServiceImpl;
import org.example.service.serviceImpl.TrainingServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Date;


public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(BeanConfiguration.class);
        GymFacade gymFacade = context.getBean("gymFacade", GymFacadeImpl.class);
        System.out.println(gymFacade.getTrainee(1));
        System.out.println(gymFacade.deleteTrainee(1));
        System.out.println(gymFacade.getAllTrainees());
//        System.out.println(gymFacade.getAllTrainees());




    }
}