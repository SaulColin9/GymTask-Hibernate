package org.example.service.serviceImpl;

import org.example.dao.Dao;
import org.example.model.Trainer;
import org.example.model.User;
import org.example.service.*;
import org.example.service.utils.UserUtils;
import org.example.service.utils.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


public class TrainerServiceImpl implements TrainerService {
    protected Dao<Trainer> trainerDao;
    private UserUtils userUtils;
    private static final Logger logger = LoggerFactory.getLogger(TrainerServiceImpl.class);
    protected Validator<Trainer> validator;

    @Override
    public int createTrainerProfile(String firstName, String lastName, int specialization) {
        Map<String, Object> params = new HashMap<>();
        params.put("firstName", firstName);
        params.put("lastName", lastName);

        validator.validateFieldsNotNull(params);
        validator.validatePositiveField("specialization", specialization);

        User newUser = userUtils.createUser(firstName, lastName);

        logger.info("Creating Trainer Profile with id {}", newUser.getId());
        Trainer newTrainer = trainerDao.save(new Trainer(specialization, newUser));
        return newTrainer.getId();
    }

    @Override
    public boolean updateTrainerProfile(int id, String firstName, String lastName, boolean isActive, int specialization) {
        Optional<Trainer> trainerToUpdate = trainerDao.get(id);
        validator.validateEntityNotNull(id, trainerToUpdate);

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
        validator.validateEntityNotNull(id, trainer);

        logger.info("Selecting Trainer Profile with id {}", id);
        return trainer.get();
    }

    public void setValidator(Validator<Trainer> validator) {
        this.validator = validator;
    }

    public void setTrainerDao(Dao<Trainer> trainerDao) {
        this.trainerDao = trainerDao;
    }

    public void setUserUtils(UserUtils userUtils) {
        this.userUtils = userUtils;
    }
}
