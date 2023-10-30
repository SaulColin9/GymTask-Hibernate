package org.example;

import org.example.configuration.BeanConfiguration;
import org.example.facade.GymFacade;
import org.example.facade.GymFacadeImpl;
import org.example.model.Trainee;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Date;


public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(BeanConfiguration.class);
        GymFacade gymFacade = context.getBean("gymFacade", GymFacadeImpl.class);
        int newTraineeId = gymFacade.addTrainee("Test", "Last", null, null);
        Trainee newTrainee = gymFacade.getTrainee(newTraineeId);
        System.out.println(newTrainee);
        gymFacade.updateTrainee(2, null, null,
                newTrainee.getUser().getIsActive(), new Date(), "Test Street");
        System.out.println(gymFacade.getTrainee(newTraineeId));
        System.out.println(gymFacade.deleteTrainee(newTraineeId));


        int newTrainerId = gymFacade.addTrainer("Saul", "Colin", 1);
        System.out.println(gymFacade.getTrainer(newTrainerId));
        gymFacade.updateTrainer(newTrainerId, null, "Salas", false, 2);
        System.out.println(gymFacade.getTrainer(newTrainerId));


        int newTrainingId = gymFacade.addTraining(1, 1, "New Training",
                1, new Date(), 1);
        System.out.println(gymFacade.getTraining(newTrainingId));

    }
}