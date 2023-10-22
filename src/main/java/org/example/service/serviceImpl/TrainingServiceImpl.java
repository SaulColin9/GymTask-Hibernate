package org.example.service.serviceImpl;

import org.example.configuration.Storage;
import org.example.dao.Dao;
import org.example.model.Training;
import org.example.service.TrainingService;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

public class TrainingServiceImpl implements TrainingService {
    Storage storage;
    public TrainingServiceImpl(Storage storage){
        this.storage = storage;
    }

    @Override
    public Training createTrainingProfile(int traineeId, int trainerId, String trainingName, int trainingTypeId, Date trainingDate, double trainingDuration) {
        return (Training) storage.getDao("trainings").save(new Training(traineeId, trainerId, trainingName, trainingTypeId, trainingDate, trainingDuration));
    }

    @Override
    public Optional<Training> selectTrainingProfile(int id) {
        return storage.getDao("trainings").get(id);
    }
}
