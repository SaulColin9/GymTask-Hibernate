package org.example.service.serviceImpl;

import org.example.dao.Dao;
import org.example.model.Trainer;
import org.example.model.User;
import org.example.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;


public class TrainerServiceImpl implements TrainerService {
    private Dao<Trainer> trainerDao;
    private UserUtils userUtils;
    private static final Logger logger = LoggerFactory.getLogger(TrainerServiceImpl.class);
    private static final String NO_ID_MSG = "Provided Trainer Id does not exist";


    @Override
    public int createTrainerProfile(String firstName, String lastName, int specialization) {
        if (firstName == null || lastName == null || specialization <= 0) {
            logger.error("The next fields were not provided: " +
                    (firstName == null ? "firstName, " : "") +
                    (lastName == null ? "lastName, " : "") +
                    (specialization == 0 ? "specialization, " : "")
            );
            throw new IllegalArgumentException("firstName, lastName and specialization arguments cant be null");
        }
        User newUser = userUtils.createUser(firstName, lastName);

        logger.info("Creating Trainer Profile with id {}", newUser.getId());
        Trainer newTrainer = trainerDao.save(new Trainer(specialization, newUser));
        return newTrainer.getId();
    }

    @Override
    public boolean updateTrainerProfile(int id, String firstName, String lastName, boolean isActive, int specialization) {
        Optional<Trainer> trainerToUpdate = trainerDao.get(id);
        if (trainerToUpdate.isEmpty()) {
            logger.error(NO_ID_MSG);
            throw new IllegalArgumentException(NO_ID_MSG);
        }
        Trainer foundTrainer = trainerToUpdate.get();

        int userId = foundTrainer.getUser().getId();
        User updatedUser = userUtils.updateUser(userId,
                firstName == null ? foundTrainer.getUser().getFirstName() : firstName,
                lastName == null ? foundTrainer.getUser().getLastName() : lastName,
                isActive
        );
        foundTrainer.setUser(updatedUser);

        foundTrainer.setSpecialization(specialization);

        logger.info("Updating Trainer Profile with id {}", id);
        return trainerDao.update(id, foundTrainer) != null;
    }

    @Override
    public Trainer selectTrainerProfile(int id) {
        Optional<Trainer> trainer = trainerDao.get(id);
        if (trainer.isEmpty()) {
            logger.info(NO_ID_MSG);
            throw new IllegalArgumentException(NO_ID_MSG);
        }
        logger.info("Selecting Trainer Profile with id {}", id);
        return trainer.get();
    }

    public void setTrainerDao(Dao<Trainer> trainerDao) {
        this.trainerDao = trainerDao;
    }

    public void setUserUtils(UserUtils userUtils) {
        this.userUtils = userUtils;
    }
}
