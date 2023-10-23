package org.example.service.serviceImpl;

import org.example.configuration.Storage;
import org.example.model.Trainer;
import org.example.model.User;
import org.example.service.PasswordGeneratorImpl;
import org.example.service.TrainerService;
import org.example.service.UsernameGeneratorImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;


public class TrainerServiceImpl implements TrainerService {
    Storage storage;

    private static final Logger logger = LoggerFactory.getLogger(TrainerServiceImpl.class);
    public TrainerServiceImpl(Storage storage){
        this.storage = storage;
    }
    @Override
    public Trainer createTrainerProfile(String firstName, String lastName, int specialization) {
            String username = UsernameGeneratorImpl.generateUserName(firstName, lastName,".", storage);
            String password = PasswordGeneratorImpl.generatePassword(10);
            User newUser = storage.getUserDao()
                    .save(new User(firstName, lastName, username, password,true));
            logger.info("Creating Trainer Profile with id " + newUser.getId());
            return storage.getTrainerDao().save(new Trainer(specialization, newUser.getId(), newUser));
    }

    @Override
    public Trainer updateTrainerProfile(int id, String firstName, String lastName, boolean isActive, int specialization) {
        Optional<Trainer> trainerToUpdate = storage.getTrainerDao().get(id);
        if (trainerToUpdate.isEmpty()){
            logger.error("Provided Trainer Id does not exist");
            return null;
        }
        Optional<User> userToUpdate = storage.getUserDao().get(trainerToUpdate.get().getUserId());
        if (userToUpdate.isEmpty()){
            logger.error("Trainer Profile User Id does not exist");
            return null;
        }

        userToUpdate.get().setFirstName(firstName);
        userToUpdate.get().setLastName(lastName);
        userToUpdate.get().setIsActive(isActive);
        String newUserName = UsernameGeneratorImpl.generateUserName(firstName, lastName,".", storage);
        userToUpdate.get().setUsername(newUserName);

        storage.getUserDao().update(trainerToUpdate.get().getUserId(),userToUpdate.get());

        trainerToUpdate.get().setSpecialization(specialization);
        trainerToUpdate.get().setUser(userToUpdate.get());

        logger.info("Updating Trainer Profile with id " + id);
        return storage.getTrainerDao().update(id, trainerToUpdate.get());
    }

    @Override
    public Trainer selectTrainerProfile(int id) {
        Optional<Trainer> trainer = storage.getTrainerDao().get(id);
        if(trainer.isEmpty()){
            logger.info("Provided Trainer Id does not exist" + id);
        }
        logger.info("Selecting Trainer Profile with id " + id);
        Trainer selectedTrainer = trainer.orElse(null);
        Optional<User> trainersUser = storage.getUserDao().get(selectedTrainer.getUserId());
        selectedTrainer.setUser(trainersUser.orElse(null));
        return selectedTrainer;
    }

    @Override
    public List<Trainer> selectAll() {
        List<Trainer> trainers = storage.getTrainerDao().getAll();
        for(Trainer trainer: trainers){
            Optional<User> trainersUser = storage.getUserDao().get(trainer.getUserId());
            trainer.setUser(trainersUser.orElse(null));
        }
        logger.info("Selecting All Trainer Profiles");
        return trainers;
    }
}
