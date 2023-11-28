package org.example.service.serviceImpl.jpa;

import org.example.dao.jpa.JpaDaoTrainerImpl;
import org.example.model.Trainer;
import org.example.service.serviceImpl.TrainerServiceImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class JpaTrainerServiceImpl extends TrainerServiceImpl implements JpaTrainerService {
    @Override
    public Optional<Trainer> selectTrainerProfileByUsername(String username) {
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        validator.validateFieldsNotNull(params);

        Optional<Trainer> foundTrainer = ((JpaDaoTrainerImpl) trainerDao).getByUsername(username);

        validator.validateEntityNotNull(username, foundTrainer);
        return foundTrainer;
    }

    @Override
    public boolean updateTrainerPassword(int id, String newPassword) {
        Optional<Trainer> trainerToUpdate = trainerDao.get(id);
        validator.validateEntityNotNull(id, trainerToUpdate);

        Trainer foundTrainer = trainerToUpdate.get();
        foundTrainer.getUser().setPassword(newPassword);

        return trainerDao.update(id, foundTrainer) != null;
    }

    @Override
    public void updateTrainerTraineeStatus(int id, boolean isActive) {
        Optional<Trainer> trainerToUpdate = trainerDao.get(id);
        validator.validateEntityNotNull(id, trainerToUpdate);

        Trainer foundTrainer = trainerToUpdate.get();
        foundTrainer.getUser().setIsActive(false);

        trainerDao.update(id, foundTrainer);
    }
}
