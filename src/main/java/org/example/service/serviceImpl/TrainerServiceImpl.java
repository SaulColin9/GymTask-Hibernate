package org.example.service.serviceImpl;

import org.example.dao.Dao;
import org.example.model.Trainer;
import org.example.model.User;
import org.example.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;


public class TrainerServiceImpl implements TrainerService {
    Dao<User> userDao;
    private final UserUtils createUtils = new UserUtilsImpl();

    Dao<Trainer> trainerDao;

    private static final Logger logger = LoggerFactory.getLogger(TrainerServiceImpl.class);

    @Override
    public int createTrainerProfile(String firstName, String lastName, int specialization) {
        if (firstName == null || lastName == null || specialization <= 0) {
            logger.error("The next fields were not provided: " +
                    (firstName == null ? "firstName, " : "") +
                    (lastName == null ? "lastName, " : "") +
                    (specialization == 0 ? "specialization, " : "")
            );
            return -1;
        }

        User newUser = userDao
                .save(createUtils.createUser(firstName, lastName, userDao));

        logger.info("Creating Trainer Profile with id " + newUser.getId());
        Trainer newTrainer = trainerDao.save(new Trainer(specialization, newUser));
        return newTrainer.getId();
    }

    @Override
    public boolean updateTrainerProfile(int id, String firstName, String lastName, boolean isActive, int specialization) {
        Optional<Trainer> trainerToUpdate = trainerDao.get(id);
        if (trainerToUpdate.isEmpty()) {
            logger.error("Provided Trainer Id does not exist");
            return false;
        }

        int userId = trainerToUpdate.get().getUser().getId();
        User updatedUser = createUtils.updateUser(userId, firstName, lastName, userDao);

        trainerToUpdate.get().setSpecialization(specialization);
        trainerToUpdate.get().setUser(updatedUser);

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
