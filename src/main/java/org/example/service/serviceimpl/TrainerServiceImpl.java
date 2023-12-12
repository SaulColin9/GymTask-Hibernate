package org.example.service.serviceimpl;

import org.example.dao.Dao;
import org.example.model.Trainer;
import org.example.model.TrainingType;
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
    protected Dao<TrainingType> trainingTypeDao;
    private UserUtils userUtils;
    protected static final Logger logger = LoggerFactory.getLogger(TrainerServiceImpl.class);
    protected Validator<Trainer> validator;

    @Override
    public int createTrainerProfile(String firstName, String lastName, int specialization) {
        Optional<TrainingType> trainingType = trainingTypeDao.get(specialization);
        Map<String, Object> params = new HashMap<>();
        params.put("firstName", firstName);
        params.put("lastName", lastName);

        validator.validateFieldsNotNull(params);
        validator.validatePositiveField("specialization", specialization);

        User newUser = userUtils.createUser(firstName, lastName);
        logger.info("Creating Trainer Profile with id {}", newUser.getId());
        Trainer newTrainer = trainerDao.save(new Trainer(trainingType.get(), newUser));

        return newTrainer.getId();
    }

    @Override
    public boolean updateTrainerProfile(int id, String firstName, String lastName, boolean isActive, int specialization) {
        Optional<Trainer> trainerToUpdate = trainerDao.get(id);
        Optional<TrainingType> trainingType = trainingTypeDao.get(specialization);
        validator.validateEntityNotNull(id, trainerToUpdate.orElse(null));

        Trainer foundTrainer = trainerToUpdate.orElse(null);
        if (trainerToUpdate.isPresent()) {
            int userId = foundTrainer.getUser().getId();
            User updatedUser = userUtils.updateUser(userId,
                    firstName == null ? foundTrainer.getUser().getFirstName() : firstName,
                    lastName == null ? foundTrainer.getUser().getLastName() : lastName,
                    isActive
            );
            foundTrainer.setUser(updatedUser);

            foundTrainer.setSpecialization(trainingType.get());

            logger.info("Updating Trainer Profile with id {}", id);
        }

        return trainerDao.update(id, foundTrainer) != null;
    }

    @Override
    public Trainer selectTrainerProfile(int id) {
        Optional<Trainer> trainer = trainerDao.get(id);
        validator.validateEntityNotNull(id, trainer.orElse(null));

        logger.info("Selecting Trainer Profile with id {}", id);
        return trainer.orElse(null);
    }

    public void setValidator(Validator<Trainer> validator) {
        this.validator = validator;
    }

    public void setTrainerDao(Dao<Trainer> trainerDao) {
        this.trainerDao = trainerDao;
    }

    public void setTrainingTypeDao(Dao<TrainingType> trainingTypeDao) {
        this.trainingTypeDao = trainingTypeDao;
    }

    public void setUserUtils(UserUtils userUtils) {
        this.userUtils = userUtils;
    }
}
