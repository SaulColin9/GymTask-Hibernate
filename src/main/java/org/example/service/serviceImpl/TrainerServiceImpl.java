package org.example.service.serviceImpl;

import org.example.configuration.Storage;
import org.example.model.Trainer;
import org.example.model.User;
import org.example.service.PasswordGeneratorImpl;
import org.example.service.TraineeService;
import org.example.service.TrainerService;
import org.example.service.UsernameGeneratorImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;


public class TrainerServiceImpl implements TrainerService {
    Storage storage;
    private static final String TRAINERS_KEY = "trainers";
    private static Logger logger = LoggerFactory.getLogger(TrainerServiceImpl.class);
    public TrainerServiceImpl(Storage storage){
        this.storage = storage;
    }
    @Override
    public Trainer createTrainerProfile(String firstName, String lastName, int specialization) {
            
            String username = UsernameGeneratorImpl.generateUserName(firstName, lastName,".", storage);
            String passowrd = PasswordGeneratorImpl.generatePassword(10);
            User newUser = (User) storage.getDao("users")
                    .save(new User(firstName, lastName, username, passowrd,true));
            logger.info("Creating Trainer Profile with id " + newUser.getId());
            return (Trainer) storage.getDao(TRAINERS_KEY).save(new Trainer(specialization, newUser.getId()));
    }

    @Override
    public Trainer updateTrainerProfile(int id, Trainer trainer) {
        logger.info("Updating Trainer Profile with id " + id);
        return (Trainer) storage.getDao(TRAINERS_KEY).update(id, trainer);
    }

    @Override
    public Optional<Trainer> selectTrainerProfile(int id) {
        logger.info("Selecting Trainer Profile with id " + id);
        return storage.getDao(TRAINERS_KEY).get(id);
    }

    @Override
    public List<Trainer> selectAll() {
        logger.info("Selecting All Trainer Profiles");
        return storage.getDao(TRAINERS_KEY).getAll();
    }
}
