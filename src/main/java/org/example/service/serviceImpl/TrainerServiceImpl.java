package org.example.service.serviceImpl;

import org.example.dao.Dao;
import org.example.model.Trainer;
import org.example.model.User;
import org.example.service.PasswordGeneratorImpl;
import org.example.service.TrainerService;
import org.example.service.UsernameGeneratorImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;


public class TrainerServiceImpl implements TrainerService {
    Dao<User> userDao;

    Dao<Trainer> trainerDao;

    private static final Logger logger = LoggerFactory.getLogger(TrainerServiceImpl.class);

    @Override
    public Trainer createTrainerProfile(String firstName, String lastName, int specialization) {
        String username = UsernameGeneratorImpl.generateUserName(firstName, lastName, ".", userDao);
        String password = PasswordGeneratorImpl.generatePassword(10);
        User newUser = userDao
                .save(new User(firstName, lastName, username, password, true));
        logger.info("Creating Trainer Profile with id " + newUser.getId());
        return trainerDao.save(new Trainer(specialization, newUser.getId(), newUser));
    }

    @Override
    public Trainer updateTrainerProfile(int id, String firstName, String lastName, boolean isActive, int specialization) {
        Optional<Trainer> trainerToUpdate = trainerDao.get(id);
        if (trainerToUpdate.isEmpty()) {
            logger.error("Provided Trainer Id does not exist");
            return null;
        }
        Optional<User> userToUpdate = userDao.get(trainerToUpdate.get().getUserId());
        if (userToUpdate.isEmpty()) {
            logger.error("Trainer Profile User Id does not exist");
            return null;
        }

        userToUpdate.get().setFirstName(firstName);
        userToUpdate.get().setLastName(lastName);
        userToUpdate.get().setIsActive(isActive);
        String newUserName = UsernameGeneratorImpl.generateUserName(firstName, lastName, ".", userDao);
        userToUpdate.get().setUsername(newUserName);

        userDao.update(trainerToUpdate.get().getUserId(), userToUpdate.get());

        trainerToUpdate.get().setSpecialization(specialization);
        trainerToUpdate.get().setUser(userToUpdate.get());

        logger.info("Updating Trainer Profile with id " + id);
        return trainerDao.update(id, trainerToUpdate.get());
    }

    @Override
    public Trainer selectTrainerProfile(int id) {
        Optional<Trainer> trainer = trainerDao.get(id);
        if (trainer.isEmpty()) {
            logger.info("Provided Trainer Id does not exist" + id);
        }
        logger.info("Selecting Trainer Profile with id " + id);
        Trainer selectedTrainer = trainer.orElse(null);
        Optional<User> trainersUser = userDao.get(selectedTrainer.getUserId());
        selectedTrainer.setUser(trainersUser.orElse(null));
        return selectedTrainer;
    }

    @Override
    public void setTrainerDao(Dao<Trainer> trainerDao) {
        this.trainerDao = trainerDao;
    }

    @Override
    public void setUserDao(Dao<User> userDao) {
        this.userDao = userDao;
    }
}
