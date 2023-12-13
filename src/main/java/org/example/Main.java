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
        GymFacadeImpl jpaGymFacade = context.getBean(GymFacadeImpl.class);

//        Trainee newTrainee = jpaGymFacade.addTrainee("Manuel", "Lopez", new Date(), "Address");
//        System.out.println(jpaGymFacade.getTrainee(new Credentials(newTrainee.getUser().getUsername(), newTrainee.getUser().getPassword()), newTrainee.getId()));


        // Trainee methods from facade
        Trainee newTrainee = jpaGymFacade.addTrainee("Saul", "Colin", new Date(), "St 123");
        User traineeUser = newTrainee.getUser();
        Credentials traineeCredentials = new Credentials(traineeUser.getUsername(), traineeUser.getPassword());
        System.out.println(jpaGymFacade.getTrainee(traineeCredentials, newTrainee.getId()));
        System.out.println(jpaGymFacade.getTraineeByUsername(traineeCredentials, traineeCredentials.username()));

        jpaGymFacade
                .updateTrainee(traineeCredentials, newTrainee.getId(), "Alejandro",
                        "Colin", true, new Date(), "New Address");

        traineeCredentials = new Credentials("Alejandro.Colin", traineeCredentials.password());

        jpaGymFacade.updateActiveTraineeStatus(traineeCredentials, newTrainee.getId(), false);
        jpaGymFacade.updateTraineePassword(traineeCredentials, newTrainee.getId(), "1234");

        // Trainer methods from facade
        Trainer newTrainer = jpaGymFacade.addTrainer("John", "Doe", 1);
        User trainerUser = newTrainer.getUser();
        Credentials trainerCredentials = new Credentials(trainerUser.getUsername(), trainerUser.getPassword());

        System.out.println(jpaGymFacade.getTrainer(trainerCredentials, newTrainer.getId()));
        System.out.println(jpaGymFacade.getTrainerByUsername(trainerCredentials, trainerCredentials.username()));

        jpaGymFacade.updateTrainer(trainerCredentials, 1, "John", "Smith", true, 1);
        trainerCredentials = new Credentials("John.Smith", trainerUser.getPassword());

        System.out.println(jpaGymFacade.getTrainer(trainerCredentials, newTrainer.getId()));
        jpaGymFacade.updateTrainerPassword(trainerCredentials, newTrainer.getId(), "1234");
        jpaGymFacade.updateTraineePassword(new Credentials("Alejandro.Colin", "1234"), newTrainee.getId(), "password");

        Date currentDate = new Date();

        // Create a calendar instance and set it to the current date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);

        // Subtract one day from the current date
        calendar.add(Calendar.DAY_OF_MONTH, 1);

        // Get the Date object for yesterday
        Date yesterday = calendar.getTime();

        // Get the long value for yesterday
        long yesterdayInMillis = yesterday.getTime();

        System.out.println("Yesterday in milliseconds: " + yesterdayInMillis);
        // Training methods from facade
        int newTrainingId = jpaGymFacade
                .addTraining(newTrainee.getId(), 1, "New Training", 1, new Date(yesterdayInMillis), 2.0);
        System.out.println(jpaGymFacade.getTraining(newTrainingId));
//        System.out.println(jpaGymFacade.getTraineeTrainings("Alejandro.Colin"));
        System.out.println(jpaGymFacade.getTrainerTrainingsByUsername("John.Smith"));
        System.out.println(jpaGymFacade.getTrainerTrainingsByUsernameAndTrainingCompleteness("John.Smith", false));


    }
}