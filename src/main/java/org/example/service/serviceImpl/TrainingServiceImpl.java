package org.example.service.serviceImpl;

import org.example.configuration.Storage;
import org.example.model.Training;
import org.example.service.TrainingService;

import java.util.List;
import java.util.Optional;

public class TrainingServiceImpl implements TrainingService {
    Storage storage;
    private static final String TRAININGS_KEY = "trainings";

    public TrainingServiceImpl(Storage storage){
        this.storage = storage;
    }

    @Override
    public Training createTrainingProfile(Training training) {
        return (Training) storage.getDao(TRAININGS_KEY).save(training);
    }

    @Override
    public Optional<Training> selectTrainingProfile(int id) {
        return storage.getDao(TRAININGS_KEY).get(id);
    }

    @Override
    public List<Training> selectAll() {
        return storage.getDao(TRAININGS_KEY).getAll();
    }
}
