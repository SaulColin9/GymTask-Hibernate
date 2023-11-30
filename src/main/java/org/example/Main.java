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


        Trainee newTrainee = jpaGymFacade.addTrainee("Saul", "Colin", new Date(), "St 123");
        User traineeUser = newTrainee.getUser();
        Credentials traineeCredentials = new Credentials(traineeUser.getUsername(), traineeUser.getPassword());
        System.out.println(jpaGymFacade.getTrainee(traineeCredentials, 1));
        System.out.println(jpaGymFacade.getTraineeByUsername(traineeCredentials, "Saul.Colin"));

        jpaGymFacade
                .updateTrainee(traineeCredentials, newTrainee.getId(), "Alejandro",
                        "Colin", true, new Date(), "New Address");

        traineeCredentials = new Credentials("Alejandro.Colin", traineeUser.getPassword());

        jpaGymFacade.updateActiveTraineeStatus(traineeCredentials, newTrainee.getId(), false);
        jpaGymFacade.updateTraineePassword(traineeCredentials, 1, "1234");

        Trainer newTrainer = jpaGymFacade.addTrainer("John", "Doe", 1);
        User trainerUser = newTrainer.getUser();
        Credentials trainerCredentials = new Credentials(trainerUser.getUsername(), trainerUser.getPassword());

        System.out.println(jpaGymFacade.getTrainer(trainerCredentials, 1));
        System.out.println(jpaGymFacade.getTrainerByUsername(trainerCredentials, "John.Doe"));

        jpaGymFacade.updateTrainer(trainerCredentials, 1, "John", "Smith", true, 1);
        trainerCredentials = new Credentials("John.Smith", trainerUser.getPassword());

        System.out.println(jpaGymFacade.getTrainer(trainerCredentials, 1));
        jpaGymFacade.updateTrainerPassword(trainerCredentials, 1, "1234");
        jpaGymFacade.updateTraineePassword(new Credentials("Alejandro.Colin","1234"), 1, "password");

        int newTrainingId = jpaGymFacade
                .addTraining(1, 1, "New Training", 1, new Date(), 2.0);
        System.out.println(jpaGymFacade.getTraining(1));
        System.out.println(jpaGymFacade.getTraineeTrainings("Alejandro.Colin"));
        System.out.println(jpaGymFacade.getTrainerTrainingsByUsername("John.Smith"));


    }
}