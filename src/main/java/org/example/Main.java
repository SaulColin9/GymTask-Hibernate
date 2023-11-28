package org.example;

import jakarta.persistence.EntityManager;
import org.example.configuration.jpa.JpaBeanConfiguration;
import org.example.dao.jpa.*;
import org.example.facade.inMemory.GymFacade;
import org.example.model.Trainee;
import org.example.model.Training;
import org.example.model.TrainingType;
import org.example.model.Trainer;
import org.example.model.User;
import org.example.service.authentication.Credentials;
import org.example.service.authentication.CredentialsAuthenticatorImpl;
import org.example.service.serviceImpl.jpa.JpaTraineeService;
import org.example.service.serviceImpl.jpa.JpaTrainerService;
import org.example.service.serviceImpl.jpa.JpaTrainingService;
import org.example.service.serviceImpl.jpa.JpaTrainingServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Date;


public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(JpaBeanConfiguration.class);
        JpaTraineeService jpaTraineeService = context.getBean(JpaTraineeService.class);
        JpaTrainerService jpaTrainerService = context.getBean(JpaTrainerService.class);
        JpaTrainingService jpaTrainingService = context.getBean(JpaTrainingService.class);

//        GymFacade gymFacade = context.getBean(GymFacade.class);

        EntityManager em = context.getBean(EntityManager.class);
        GymFacade gymFacade = context.getBean(GymFacade.class);
        JpaDaoTrainerImpl daoTrainer = context.getBean(JpaDaoTrainerImpl.class);
        JpaDaoTrainingImpl daoTraining = context.getBean(JpaDaoTrainingImpl.class);
        JpaDaoTraineeImpl daoTrainee = context.getBean(JpaDaoTraineeImpl.class);
        JpaDaoTrainingTypeImpl daoTrainingType = context.getBean(JpaDaoTrainingTypeImpl.class);
        JpaDaoUserImpl jpaDaoUser = context.getBean(JpaDaoUserImpl.class);
        CredentialsAuthenticatorImpl credentialsAuthenticator = context.getBean(CredentialsAuthenticatorImpl.class);

        Credentials credentials = new Credentials("Gerardo.Lopez", "password");


        User newUser = new User();
        newUser.setFirstName("Gerardo");
        newUser.setLastName("Lopez");
        newUser.setUsername("Gerardo.Lopez");
        newUser.setPassword("password");
        newUser.setIsActive(true);

        User newUser2 = new User();
        newUser2.setIsActive(true);
        newUser2.setFirstName("Saul");
        newUser2.setLastName("Colin");
        newUser2.setPassword("password");
        newUser2.setUsername("Saul.Colin");

        jpaDaoUser.save(newUser);
        jpaDaoUser.save(newUser2);

//        jpaDaoUser.save(newUser);
        Trainee newTrainee = new Trainee();
        newTrainee.setUser(newUser);
        newTrainee.setAddress("St Apple");
        newTrainee.setDateOfBirth(new Date());
        daoTrainee.save(newTrainee);

        Trainer trainer = new Trainer();
        trainer.setUser(newUser2);
        trainer.setSpecialization(2);

        daoTrainer.save(trainer);

        TrainingType trainingType = new TrainingType();
        trainingType.setTrainingTypeName("Weight Lifting");
        daoTrainingType.save(trainingType);

        Training newTraining = new Training();
        newTraining.setTrainingDuration(1);
        newTraining.setTrainingName("Some Name");
        newTraining.setTrainee(newTrainee);
        newTraining.setTrainer(trainer);
        newTraining.setTrainingDate(new Date());
        newTraining.setTrainingType(trainingType);

        daoTraining.save(newTraining);
//        daoTraining.getTrainingsByTraineeUsername("Gerardo.Lopez", "Some Name", 1.0).forEach(System.out::println);

        daoTraining.getTrainingsByTrainerUsername("Saul.Colin", null, 5.0).forEach(System.out::println);
        System.out.println(credentialsAuthenticator.authenticate(credentials));

        System.out.println(gymFacade.getTrainer(1));
        int newTrainerId = gymFacade.addTrainer("Patricio", "Alvarez", 1);
        System.out.println(gymFacade.getTrainer(newTrainerId));
        System.out.println(jpaTraineeService.selectTraineeProfileByUsername("Gerardo.Lopez"));
        jpaTraineeService.updateTraineeTraineeStatus(1, false);
//        jpaTraineeService.updateTraineePassword(1, "root123Password");
//        jpaTraineeService.deleteTraineeProfile(5);
//        jpaTraineeService.selectTraineeProfile(1);
//        jpaTraineeService.createTraineeProfile("Alejandro", "Salas", new Date(), "Some Address");
//        System.out.println(jpaTrainerService.selectTrainerProfileByUsername("Patricio.Alvaez"));
//        System.out.println(jpaTrainerService.selectTrainerProfile(3));
//        jpaTrainerService.updateTrainerPassword(1, "SomeNewPassword123");
//        int martinId = jpaTrainerService.createTrainerProfile("Martin", "Garza", 1);
//        jpaTrainerService.updateTrainerPassword(martinId, "martin123");
//        jpaTrainerService.updateTrainerTraineeStatus(martinId, false);

        jpaTrainingService.createTrainingProfile(1, 1, "New Training", 1, new Date(), 1.0);
        System.out.println(jpaTrainingService.selectTraineeTrainingsByUsername("Gerardo.Lopez", null, 1.0));
//        daoTrainee.delete(1);
//        daoTrainee.delete(3);
//        Optional<Trainee> optionalTrainee = daoTrainee.get(2);
//        optionalTrainee.get().setAddress("New Updated Address");
//        daoTrainee.update(2, optionalTrainee.get());
//
//        System.out.println(daoTrainee.get(2).get());
//
//        Optional<TrainingType> trainingTypeOptional = daoTrainingType.get(1);
//        System.out.println(trainingTypeOptional.get());
//
//        System.out.println(daoTrainer.getByUsername("Jorge.Campos"));
//        Optional<Trainee> trainee = daoTrainee.get(2);
//        trainee.get().getUser().setUsername("John.Doe1");
//        daoTrainee.save(trainee.get());
//        System.out.println(daoTrainee.getByUsername("John.Doe1"));
//
//        daoTrainee.deleteByUsername("John.Doe1");
//        System.out.println(daoTrainee.getAll());

//
//
//        em.getTransaction().begin();
//        em.persist(trainingType);
//        em.getTransaction().commit();
//
//
//        Training training = new Training();
//
//        training.setTrainingType(trainingType);
//        training.setTrainingDate(new Date());
//        training.setTrainingName("Elite Program");
//        training.setTrainer(daoTrainer.get(1).get());
//        training.setTrainee(daoTrainee.get(2).get());
//        training.setTrainingDuration(1.5);
//
//        daoTraining.save(training);
//        TrainerServiceImpl trainerService = context.getBean(TrainerServiceImpl.class);
//        daoTrainer.delete(5);

//        trainerService.selectTrainerProfile()
//        int newId = trainerService.createTrainerProfile("Jorge", "Campos", 2);
//        System.out.println(trainerService.selectTrainerProfile(newId));
//        JpaDaoUserImpl daoUser = context.getBean(JpaDaoUserImpl.class);
//        JpaDaoTrainerImpl daoTrainer = context.getBean(JpaDaoTrainerImpl.class);
//        daoTrainer.getAll().forEach(System.out::println);


//        daoUser.save(newUser);
//        daoUser.getAll().forEach(System.out::println);
//        System.out.println("================");
//        daoUser.delete(6);
//        System.out.println("================");
//        daoUser.getAll().forEach(System.out::println);
//        EntityManager em = context.getBean("entityManager", EntityManager.class);
//
//        EntityTransaction tx = em.getTransaction();
//        tx.begin();
//
//
//        User newUser2 = new User();
//        newUser2.setIsActive(true);
//        newUser2.setFirstName("Jenny");
//        newUser2.setLastName("Jane");
//        newUser2.setPassword("password");
//        newUser2.setUsername("Jenny.Jane");
//

//        Trainee trainee = new Trainee();
//        trainee.setUser(em.find(User.class, 2));
//        trainee.setAddress("St Main 123");
//        trainee.setDateOfBirth(new Date());

//        Trainee trainee = em.find(Trainee.class, 2);
//        trainee.setUser(em.find(User.class, 3));
//        newUser.setId(1);
//        List<User> users = em.createQuery("from User").getResultList();
//        users.forEach(System.out::println);
//        List<Trainee> trainees = em.createQuery("from Trainee").getResultList();
//        trainees.forEach(System.out::println);

//        Trainer foundTrainer = em.find(Trainer.class, 1);
//        foundTrainer.getUser().setLastName("NewLastName");
//        em.persist(trainee);
//        em.persist(foundTrainer);

//        em.persist(newUser);
//        em.persist(newUser2);
//        em.persist(trainer);

//        tx.commit();


//        GymFacade gymFacade = context.getBean("gymFacade", GymFacadeImpl.class);
//        int newTraineeId = gymFacade.addTrainee("Test", "Last", null, null);
//        Trainee newTrainee = gymFacade.getTrainee(newTraineeId);
//        System.out.println(newTrainee);
//        gymFacade.updateTrainee(2, null, null,
//                newTrainee.getUser().getIsActive(), new Date(), "Test Street");
//        System.out.println(gymFacade.getTrainee(newTraineeId));
//        System.out.println(gymFacade.deleteTrainee(newTraineeId));
//
//
//        int newTrainerId = gymFacade.addTrainer("Saul", "Colin", 1);
//        System.out.println(gymFacade.getTrainer(newTrainerId));
//        gymFacade.updateTrainer(newTrainerId, null, "Salas", false, 2);
//        System.out.println(gymFacade.getTrainer(newTrainerId));
//
//
//        int newTrainingId = gymFacade.addTraining(1, 1, "New Training",
//                1, new Date(), 1);
//        System.out.println(gymFacade.getTraining(newTrainingId));

    }
}