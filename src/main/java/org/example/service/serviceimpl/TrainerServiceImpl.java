package org.example.service.serviceimpl;

import org.example.dao.Dao;
import org.example.model.Trainer;
import org.example.model.TrainingType;
import org.example.model.User;
import org.example.service.TrainerService;
import org.example.service.utils.user.UserUtils;
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
    public Trainer createTrainerProfile(String firstName, String lastName, int specialization) {
        Optional<TrainingType> trainingType = trainingTypeDao.get(specialization);
        Map<String, Object> params = new HashMap<>();
        params.put("firstName", firstName);
        params.put("lastName", lastName);

        validator.validateFieldsNotNull(params);
        validator.validatePositiveField("specialization", specialization);

        User newUser = userUtils.createUser(firstName, lastName);
        logger.info("Creating Trainer Profile with id {}", newUser.getId());
        TrainingType foundTrainingType = null;
        if (trainingType.isPresent()) {
            foundTrainingType = trainingType.get();
        }
        return trainerDao.save(new Trainer(foundTrainingType, newUser));
    }

    @Override
    public boolean updateTrainerProfile(int id, String firstName, String lastName, boolean isActive, int specialization) {
        Optional<Trainer> trainerToUpdate = trainerDao.get(id);
        Optional<TrainingType> trainingType = trainingTypeDao.get(specialization);

        Trainer foundTrainer = trainerToUpdate.orElse(null);
        trainerToUpdate.ifPresent(trainer -> {
            int userId = trainer.getUser().getId();
            User updatedUser = userUtils.updateUser(userId,
                    firstName == null ? trainer.getUser().getFirstName() : firstName,
                    lastName == null ? trainer.getUser().getLastName() : lastName,
                    isActive
            );
            trainer.setUser(updatedUser);
            TrainingType foundTrainingType = null;
            if (trainingType.isPresent())
                foundTrainingType = trainingType.get();

            trainer.setSpecialization(foundTrainingType);

            logger.info("Updating Trainer Profile with id {}", id);

        });

        return trainerDao.update(id, foundTrainer) != null;
    }

    @Override
    public Trainer selectTrainerProfile(int id) {
        Optional<Trainer> trainer = trainerDao.get(id);
        Map<String, Object> params = new HashMap<>();
        params.put("Trainer", trainer.orElse(null));

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
