package org.example;

import org.example.configuration.BeanConfiguration;
import org.example.facade.impl.GymFacadeImpl;
import org.example.model.Trainee;
import org.example.model.Trainer;
import org.example.service.authentication.Credentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Date;


public class Main {

    protected static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.getEnvironment().setActiveProfiles("jpa");
        context.register(BeanConfiguration.class);
        context.refresh();
        GymFacadeImpl gymFacade = context.getBean(GymFacadeImpl.class);

        // Trainer
        // creating new Trainer
        Trainer trainer1 = gymFacade.addTrainer("George", "Smith", 1);
        Credentials trainer1Credentials = new Credentials(trainer1.getUser().getUsername(), trainer1.getUser().getPassword());
        // Selecting trainer by id
        logger.info(gymFacade.getTrainer(trainer1Credentials, trainer1.getId()).toString());
        // Updating Trainer
        gymFacade.updateTrainer(trainer1Credentials, trainer1.getId(), "George", "Ford", false, 2);
        String updatedTrainerUsername = "George.Ford";
        trainer1Credentials = new Credentials(updatedTrainerUsername, trainer1.getUser().getPassword());
        logger.info(gymFacade.getTrainer(trainer1Credentials, trainer1.getId()).toString());
        // Updating Trainer active status
        gymFacade.updateActiveTrainerStatus(trainer1Credentials, trainer1.getId(), true);
        // Select Trainer by username
        logger.info(gymFacade.getTrainerByUsername(trainer1Credentials, updatedTrainerUsername).toString());
        // Trainer password change
        String newTrainerPassword = "1234";
        gymFacade.updateTrainerPassword(trainer1Credentials, trainer1.getId(), newTrainerPassword);

        // Trainee
        // creating new Trainee
        Trainee trainee1 = gymFacade.addTrainee("Saul", "Colin", new Date(), "St 123");
        Credentials trainee1Credentials = new Credentials(trainee1.getUser().getUsername(), trainee1.getUser().getPassword());
        // Selecting trainee by id
        logger.info(gymFacade.getTrainee(trainee1Credentials, trainee1.getId()).toString());
        // Updating Trainee
        gymFacade.updateTrainee(trainee1Credentials, trainee1.getId(), "Alejandro", "Colin", false, new Date(), "New Address");
        String updatedTraineeUsername = "Alejandro.Colin";
        Credentials updatedTrainee1Credentials = new Credentials(updatedTraineeUsername, trainee1.getUser().getPassword());
        logger.info(gymFacade.getTrainee(updatedTrainee1Credentials, trainee1.getId()).toString());
        // Updating Trainee active status
        gymFacade.updateActiveTraineeStatus(updatedTrainee1Credentials, 1, true);
        logger.info(gymFacade.getTrainee(updatedTrainee1Credentials, trainee1.getId()).toString());
        // Select Trainee by username
        logger.info(gymFacade.getTraineeByUsername(updatedTrainee1Credentials, updatedTraineeUsername).toString());
        // Trainee password change
        String newTraineePassword = "password";
        gymFacade.updateTraineePassword(updatedTrainee1Credentials, trainee1.getId(), newTraineePassword);
        updatedTrainee1Credentials = new Credentials(updatedTraineeUsername, newTraineePassword);

        // Deleting a new Trainee by username
        Trainee trainee2 = gymFacade.addTrainee("John", "Doe", new Date(), "St 45");
        Credentials trainee2Credentials = new Credentials(trainee2.getUser().getUsername(), trainee2.getUser().getPassword());
        gymFacade.deleteTraineeByUsername(trainee2Credentials, trainee2.getUser().getUsername());

        // Training
        // Create training
        gymFacade.addTraining(trainee1.getId(), trainer1.getId(), "Elite Training", 1, new Date(), 1.0);

        // Get Trainee Trainings List by username and trainingType (criteria)
        logger.info(gymFacade.getTraineeTrainingsByUsernameAndTrainingType(updatedTraineeUsername, 1).toString());
        // Get Trainer Trainings List by username and criteria
        logger.info(gymFacade.getTrainerTrainingsByUsernameAndTrainingCompleteness(updatedTrainerUsername, true).toString());

        // Get not assigned on specific trainee active trainers list
        gymFacade.addTrainer("Martin", "Robins", 1);
        logger.info(gymFacade.getNotAssignedOnTraineeTrainersList(updatedTrainee1Credentials, trainee1.getId()).toString());


    }
}