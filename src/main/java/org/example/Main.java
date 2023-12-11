package org.example;

import org.example.configuration.jpa.JpaBeanConfiguration;
import org.example.facade.jpa.JpaGymFacadeImpl;
import org.example.model.Trainee;
import org.example.model.Trainer;
import org.example.model.User;
import org.example.service.authentication.Credentials;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Date;


public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(JpaBeanConfiguration.class);
        JpaGymFacadeImpl jpaGymFacade = context.getBean(JpaGymFacadeImpl.class);



        // Trainee methods from facade
        Trainee newTrainee = jpaGymFacade.addTrainee("Saul", "Colin", new Date(), "St 123");
        User traineeUser = newTrainee.getUser();
        Credentials traineeCredentials = new Credentials(traineeUser.getUsername(), traineeUser.getPassword());
        System.out.println(jpaGymFacade.getTrainee(traineeCredentials, newTrainee.getId()));
        System.out.println(jpaGymFacade.getTraineeByUsername(traineeCredentials, traineeCredentials.getUsername()));

        jpaGymFacade
                .updateTrainee(traineeCredentials, newTrainee.getId(), "Alejandro",
                        "Colin", true, new Date(), "New Address");

        traineeCredentials = new Credentials("Alejandro.Colin", traineeCredentials.getPassword());

        jpaGymFacade.updateActiveTraineeStatus(traineeCredentials, newTrainee.getId(), false);
        jpaGymFacade.updateTraineePassword(traineeCredentials, newTrainee.getId(), "1234");

        // Trainer methods from facade
        Trainer newTrainer = jpaGymFacade.addTrainer("John", "Doe", 1);
        User trainerUser = newTrainer.getUser();
        Credentials trainerCredentials = new Credentials(trainerUser.getUsername(), trainerUser.getPassword());

        System.out.println(jpaGymFacade.getTrainer(trainerCredentials, newTrainer.getId()));
        System.out.println(jpaGymFacade.getTrainerByUsername(trainerCredentials, trainerCredentials.getUsername()));

        jpaGymFacade.updateTrainer(trainerCredentials, 1, "John", "Smith", true, 1);
        trainerCredentials = new Credentials("John.Smith", trainerUser.getPassword());

        System.out.println(jpaGymFacade.getTrainer(trainerCredentials, newTrainer.getId()));
        jpaGymFacade.updateTrainerPassword(trainerCredentials, newTrainer.getId(), "1234");
        jpaGymFacade.updateTraineePassword(new Credentials("Alejandro.Colin", "1234"), newTrainee.getId(), "password");

        // Training methods from facade
        int newTrainingId = jpaGymFacade
                .addTraining(newTrainee.getId(), 1, "New Training", 1, new Date(), 2.0);
        System.out.println(jpaGymFacade.getTraining(newTrainingId));
        System.out.println(jpaGymFacade.getTraineeTrainings("Alejandro.Colin"));
        System.out.println(jpaGymFacade.getTrainerTrainingsByUsername("John.Smith"));


    }
}