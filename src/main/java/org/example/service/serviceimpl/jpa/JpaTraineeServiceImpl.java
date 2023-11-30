package org.example.service.serviceimpl.jpa;

import org.example.dao.jpa.JpaDaoTraineeImpl;
import org.example.model.Trainee;
import org.example.model.Trainer;
import org.example.service.serviceimpl.TraineeServiceImpl;

import java.util.HashMap;
import java.util.List;
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
        logger.info("Selecting Trainee Profile with username {}", username);

        return foundTrainee.get();
    }

    @Override
    public boolean deleteTraineeProfile(int id) {
        Optional<Trainee> traineeToDelete = traineeDao.get(id);
        validator.validateEntityNotNull(id, traineeToDelete);

        Optional<Trainee> deletedTrainee = traineeDao.delete(id);
        logger.info("Deleting Trainee Profile with id {}", id);

        return deletedTrainee.isPresent();
    }

    @Override
    public boolean deleteTraineeProfileByUsername(String username) {
        Optional<Trainee> traineeToDelete = ((JpaDaoTraineeImpl) traineeDao).getByUsername(username);
        validator.validateEntityNotNull(username, traineeToDelete);

        Optional<Trainee> deletedTrainee = ((JpaDaoTraineeImpl) traineeDao).deleteByUsername(username);
        logger.info("Deleting Trainee Profile with username {}", username);

        return deletedTrainee.isPresent();
    }

    @Override
    public boolean updateTraineePassword(int id, String newPassword) {
        Optional<Trainee> traineeToUpdate = traineeDao.get(id);
        validator.validateEntityNotNull(id, traineeToUpdate);

        Trainee foundTrainee = traineeToUpdate.get();
        foundTrainee.getUser().setPassword(newPassword);
        logger.info("Updating Trainee Password with id {}", id);

        return traineeDao.update(id, foundTrainee) != null;
    }

    @Override
    public boolean updateTraineeTraineeStatus(int id, boolean isActive) {
        Optional<Trainee> traineeToUpdate = traineeDao.get(id);
        validator.validateEntityNotNull(id, traineeToUpdate);

        Trainee foundTrainee = traineeToUpdate.get();
        foundTrainee.getUser().setIsActive(false);
        logger.info("Updating status for Trainee with id {} to {}", id, isActive);

        return traineeDao.update(id, foundTrainee) != null;
    }

    @Override
    public List<Trainer> selectNotAssignedOnTraineeTrainersList(int traineeId) {
        Optional<Trainee> foundTrainee = traineeDao.get(traineeId);
        validator.validateEntityNotNull(traineeId, foundTrainee);
        logger.info("Selecting not assigned on trainee with id {} trainers list", traineeId);

        return ((JpaDaoTraineeImpl) traineeDao).getNotAssignedOnTraineeTrainersList(foundTrainee.get());
    }


}
