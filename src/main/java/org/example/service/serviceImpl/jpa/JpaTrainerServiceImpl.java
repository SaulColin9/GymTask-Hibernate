package org.example.service.serviceImpl.jpa;

import org.example.dao.jpa.JpaDaoTrainerImpl;
import org.example.model.Trainer;
import org.example.service.serviceImpl.TrainerServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JpaTrainerServiceImpl extends TrainerServiceImpl implements JpaTrainerService {
    @Override
    public Trainer selectTrainerProfileByUsername(String username) {
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        validator.validateFieldsNotNull(params);

        Optional<Trainer> foundTrainer = ((JpaDaoTrainerImpl) trainerDao).getByUsername(username);

        validator.validateEntityNotNull(username, foundTrainer);
        logger.info("Selecting Trainer Profile with username {}", username);

        return foundTrainer.get();
    }

    @Override
    public boolean updateTrainerPassword(int id, String newPassword) {
        Optional<Trainer> trainerToUpdate = trainerDao.get(id);
        validator.validateEntityNotNull(id, trainerToUpdate);

        Trainer foundTrainer = trainerToUpdate.get();
        foundTrainer.getUser().setPassword(newPassword);
        logger.info("Updating Trainer Password with id {}", id);
        return trainerDao.update(id, foundTrainer) != null;
    }

    @Override
    public boolean updateTrainerTraineeStatus(int id, boolean isActive) {
        Optional<Trainer> trainerToUpdate = trainerDao.get(id);
        validator.validateEntityNotNull(id, trainerToUpdate);

        Trainer foundTrainer = trainerToUpdate.get();
        foundTrainer.getUser().setIsActive(false);

        logger.info("Updating status for Trainer with id {} to {}", id, isActive);
        return trainerDao.update(id, foundTrainer) != null;
    }

    @Override
    public List<Trainer> updateTraineeTrainersList(int trainee_id, int trainer_id) {
        Optional<Trainer> foundTrainer = trainerDao.get(trainer_id);
        validator.validateEntityNotNull(trainer_id, foundTrainer);

        logger.info("Updating trainee with id {} trainers list with id {}", trainee_id, trainer_id);
        return ((JpaDaoTrainerImpl) trainerDao).updateTraineeTrainersList(trainee_id, foundTrainer.get());
    }
}