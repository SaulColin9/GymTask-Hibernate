package org.example.service.serviceImpl;

import org.example.configuration.Storage;
import org.example.model.Training;
import org.example.service.TrainingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class TrainingServiceImpl implements TrainingService {
    Storage storage;
    private static final String TRAININGS_KEY = "trainings";
    private static Logger logger = LoggerFactory.getLogger(TrainerServiceImpl.class);

    public TrainingServiceImpl(Storage storage){
        this.storage = storage;
    }

    @Override
    public Training createTrainingProfile(Training training) {
        Training newTraining = (Training) storage.getDao(TRAININGS_KEY).save(training);
        logger.info("Creating Training Profile with id " + newTraining.getId());
        return newTraining;
    }

    @Override
    public Optional<Training> selectTrainingProfile(int id) {
        logger.info("Selecting Training Profile with id " + id);
        return storage.getDao(TRAININGS_KEY).get(id);
    }

    @Override
    public List<Training> selectAll() {
        logger.info("Selecting All Training Profiles");
        return storage.getDao(TRAININGS_KEY).getAll();
    }
}
