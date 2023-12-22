package org.example.service.serviceimpl.jpa;

import org.example.dao.jpa.JpaDaoTrainerImpl;
import org.example.model.Trainer;
import org.example.service.serviceimpl.TrainerServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JpaTrainerServiceImpl extends TrainerServiceImpl implements JpaTrainerService {
    private static final String TRAINER_ENTITY = "Trainer";

    @Override
    public Trainer selectTrainerProfileByUsername(String username) {
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        validator.validateFieldsNotNull(params);

        Optional<Trainer> foundTrainer = ((JpaDaoTrainerImpl) trainerDao).getByUsername(username);

        params.clear();
        params.put(TRAINER_ENTITY, foundTrainer.orElse(null));
        validator.validateEntitiesNotNull(params);
        logger.info("Selecting Trainer Profile with username {}", username);

        return foundTrainer.orElse(null);
    }

    @Override
    public boolean updateTrainerPassword(int id, String newPassword) {
        Optional<Trainer> trainerToUpdate = trainerDao.get(id);
        Map<String, Object> params = new HashMap<>();
        params.put(TRAINER_ENTITY, trainerToUpdate.orElse(null));
        validator.validateEntitiesNotNull(params);

        Trainer foundTrainer = trainerToUpdate.orElse(null);
        if (trainerToUpdate.isPresent()) {
            foundTrainer.getUser().setPassword(newPassword);
            logger.info("Updating Trainer Password with id {}", id);
        }
        return trainerDao.update(id, foundTrainer) != null;
    }

    @Override
    public boolean updateTrainerActiveStatus(int id, boolean isActive) {
        Optional<Trainer> trainerToUpdate = trainerDao.get(id);
        Map<String, Object> params = new HashMap<>();
        params.put(TRAINER_ENTITY, trainerToUpdate.orElse(null));
        validator.validateEntitiesNotNull(params);

        Trainer foundTrainer = trainerToUpdate.orElse(null);
        if (trainerToUpdate.isPresent()) {
            foundTrainer.getUser().setIsActive(isActive);
            logger.info("Updating status for Trainer with id {} to {}", id, isActive);
        }
        return trainerDao.update(id, foundTrainer) != null;
    }

    @Override
    public List<Trainer> updateTraineeTrainersList(int traineeId, int trainerId) {
        Optional<Trainer> foundTrainer = trainerDao.get(trainerId);
        Map<String, Object> params = new HashMap<>();
        params.put(TRAINER_ENTITY, foundTrainer.orElse(null));
        validator.validateEntitiesNotNull(params);

        logger.info("Updating trainee with id {} trainers list with id {}", traineeId, trainerId);
        return ((JpaDaoTrainerImpl) trainerDao).updateTraineeTrainersList(traineeId, foundTrainer.orElse(null));
    }
}
