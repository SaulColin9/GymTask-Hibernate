package org.example;

import org.example.configuration.BeanConfiguration;
import org.example.configuration.Storage;
import org.example.configuration.StorageImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;



public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(BeanConfiguration.class);
        Storage storage = context.getBean("storage", StorageImpl.class);
        System.out.println(storage.getDaos().get("users").getAll());

    }
}