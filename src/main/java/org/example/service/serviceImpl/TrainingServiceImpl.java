package org.example.service.serviceImpl;

import org.example.configuration.Storage;
import org.example.dao.Dao;
import org.example.model.Training;
import org.example.service.TrainingService;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class TrainingServiceImpl implements TrainingService {
    Storage storage;
    public TrainingServiceImpl(Storage storage){
        this.storage = storage;
    }

    @Override
    public Training createTrainingProfile(Training training) {
        System.out.println(training);
        return (Training) storage.getDao("trainings").save(training);
    }

    @Override
    public Optional<Training> selectTrainingProfile(int id) {
        return storage.getDao("trainings").get(id);
    }

    @Override
    public List<Training> selectAll() {
        return storage.getDao("trainings").getAll();
    }
}
