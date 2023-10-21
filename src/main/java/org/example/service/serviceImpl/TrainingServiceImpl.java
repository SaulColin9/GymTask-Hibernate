package org.example.service.serviceImpl;

import org.example.configuration.Storage;
import org.example.dao.Dao;
import org.example.service.TrainingService;

import java.util.Map;

public class TrainingServiceImpl implements TrainingService {
    Storage storage;
    @Override
    public void createTrainingProfile() {
        System.out.println("Training profile created...");
        storage.getDao("users");

    }

    @Override
    public void selectTrainingProfile() {
        System.out.println("Training profile selected...");
    }
    public TrainingServiceImpl(Storage storage){
       this.storage = storage;
    }
}
