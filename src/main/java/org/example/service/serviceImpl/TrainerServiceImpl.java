package org.example.service.serviceImpl;

import org.example.dao.Dao;
import org.example.model.Trainer;
import org.example.model.User;
import org.example.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;


public class TrainerServiceImpl implements TrainerService {
    private final UsernameGenerator usernameGenerator = new UsernameGeneratorImpl();
    private final PasswordGenerator passwordGenerator = new PasswordGeneratorImpl();
    Dao<User> userDao;

    Dao<Trainer> trainerDao;

    private static final Logger logger = LoggerFactory.getLogger(TrainerServiceImpl.class);

    @Override
    public int createTrainerProfile(String firstName, String lastName, int specialization) {
        if (firstName == null|| lastName == null|| specialization <= 0) {
            logger.error("The next fields were not provided: " +
                    (firstName == null? "firstName, " : "") +
                    (lastName == null? "lastName, " : "") +
                    (specialization == 0 ? "specialization, " : "")
            );
            return -1;
        }
        String username = usernameGenerator.generateUserName(firstName, lastName, userDao);
        String password = passwordGenerator.generatePassword(10);
        User newUser = userDao
                .save(new User(firstName, lastName, username, password, true));
        logger.info("Creating Trainer Profile with id " + newUser.getId());
        Trainer newTrainer = trainerDao.save(new Trainer(specialization, newUser.getId(), newUser));
        return newTrainer.getId();
    }

    @Override
    public boolean updateTrainerProfile(int id, String firstName, String lastName, boolean isActive, int specialization) {
        Optional<Trainer> trainerToUpdate = trainerDao.get(id);
        if (trainerToUpdate.isEmpty()) {
            logger.error("Provided Trainer Id does not exist");
            return false;
        }
        Optional<User> userToUpdate = userDao.get(trainerToUpdate.get().getUserId());
        if (userToUpdate.isEmpty()) {
            logger.error("Trainer Profile User Id does not exist");
            return false;
        }

        userToUpdate.get().setFirstName(firstName);
        userToUpdate.get().setLastName(lastName);
        userToUpdate.get().setIsActive(isActive);
        String newUserName = usernameGenerator.generateUserName(firstName, lastName, userDao);
        userToUpdate.get().setUsername(newUserName);

        userDao.update(trainerToUpdate.get().getUserId(), userToUpdate.get());

        trainerToUpdate.get().setSpecialization(specialization);
        trainerToUpdate.get().setUser(userToUpdate.get());

        logger.info("Updating Trainer Profile with id " + id);
        return trainerDao.update(id, trainerToUpdate.get()) != null;
    }

    @Override
    public Trainer selectTrainerProfile(int id) {
        Optional<Trainer> trainer = trainerDao.get(id);
        if (trainer.isEmpty()) {
            logger.info("Provided Trainer Id does not exist" + id);
            return null;
        }
        logger.info("Selecting Trainer Profile with id " + id);
        return trainer.get();
    }

    public void setTrainerDao(Dao<Trainer> trainerDao) {
        this.trainerDao = trainerDao;
    }

    public void setUserDao(Dao<User> userDao) {
        this.userDao = userDao;
    }
}
