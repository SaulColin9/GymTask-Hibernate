package org.example;

import org.example.configuration.BeanConfiguration;
import org.example.configuration.Storage;
import org.example.configuration.StorageImpl;
import org.example.dao.Dao;
import org.example.model.Trainee;
import org.example.model.Training;
import org.example.model.TrainingType;
import org.example.model.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Optional;


public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(BeanConfiguration.class);
        Storage storage = context.getBean("storage", StorageImpl.class);
        Dao<User> daoUser = storage.getDao("users");
        Dao<Trainee> daoTrainee = storage.getDao("trainees");
        Dao<TrainingType> daoTrainingType = storage.getDao("trainingTypes") ;
        System.out.println(daoTrainingType.getAll());
        daoTrainingType.save(new TrainingType("Fitness Dancing"));
        System.out.println(daoTrainingType.getAll());


    }
}