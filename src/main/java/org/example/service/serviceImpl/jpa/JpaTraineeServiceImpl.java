package org.example.service.serviceImpl.jpa;

import org.example.dao.jpa.JpaDaoTraineeImpl;
import org.example.model.Trainee;
import org.example.service.serviceImpl.TraineeServiceImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class JpaTraineeServiceImpl extends TraineeServiceImpl implements JpaTraineeService {
    @Override
    public Trainee selectTraineeProfileByUsername(String username) {
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        validator.validateFieldsNotNull(params);

        Optional<Trainee> foundTrainee = ((JpaDaoTraineeImpl) traineeDao).getByUsername(username);

        validator.validateEntityNotNull(username, foundTrainee);
        return foundTrainee.get();
    }

    @Override
    public boolean deleteTraineeProfile(int id) {
        Optional<Trainee> traineeToDelete = traineeDao.get(id);
        validator.validateEntityNotNull(id, traineeToDelete);

        logger.info("Deleting Trainee Profile with id " + id);
        Optional<Trainee> deletedTrainee = traineeDao.delete(id);

        return deletedTrainee.isPresent();
    }

    @Override
    public boolean deleteTraineeProfileByUsername(String username) {
        Optional<Trainee> traineeToDelete = ((JpaDaoTraineeImpl) traineeDao).getByUsername(username);
        validator.validateEntityNotNull(username, traineeToDelete);

        logger.info("Deleting Trainee Profile with username " + username);
        Optional<Trainee> deletedTrainee = ((JpaDaoTraineeImpl) traineeDao).deleteByUsername(username);

        return deletedTrainee.isPresent();
    }

    @Override
    public boolean updateTraineePassword(int id, String newPassword) {
        Optional<Trainee> traineeToUpdate = traineeDao.get(id);
        validator.validateEntityNotNull(id, traineeToUpdate);

        Trainee foundTrainee = traineeToUpdate.get();
        foundTrainee.getUser().setPassword(newPassword);

        return traineeDao.update(id, foundTrainee) != null;
    }

    @Override
    public boolean updateTraineeTraineeStatus(int id, boolean isActive) {
        Optional<Trainee> traineeToUpdate = traineeDao.get(id);
        validator.validateEntityNotNull(id, traineeToUpdate);

        Trainee foundTrainee = traineeToUpdate.get();
        foundTrainee.getUser().setIsActive(false);

        return traineeDao.update(id, foundTrainee) != null;
    }
}
