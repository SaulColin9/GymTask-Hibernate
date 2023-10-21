package org.example;

import org.example.configuration.BeanConfiguration;
import org.example.configuration.Storage;
import org.example.configuration.StorageImpl;
import org.example.dao.Dao;
import org.example.model.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Optional;


public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(BeanConfiguration.class);
        Storage storage = context.getBean("storage", StorageImpl.class);
        Dao<User> daoUser = storage.getDao("users");
        System.out.println(daoUser.getAll());
//        Optional<User> saul = daoUser.get(11);
//        if(saul.isPresent()){
//            saul.get().setUsername("Saul Alejandro");
//            daoUser.update(11, saul.get());
//
//        }


    }
}