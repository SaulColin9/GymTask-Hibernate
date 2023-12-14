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

        validator.validateEntityNotNull(username, foundTrainee.orElse(null));
        logger.info("Selecting Trainee Profile with username {}", username);

        return foundTrainee.orElse(null);
    }

    @Override
    public boolean deleteTraineeProfile(int id) {
        Optional<Trainee> traineeToDelete = traineeDao.get(id);
        validator.validateEntityNotNull(id, traineeToDelete.orElse(null));

        Optional<Trainee> deletedTrainee = traineeDao.delete(id);
        logger.info("Deleting Trainee Profile with id {}", id);

        return deletedTrainee.isPresent();
    }

    @Override
    public boolean deleteTraineeProfileByUsername(String username) {
        Optional<Trainee> traineeToDelete = ((JpaDaoTraineeImpl) traineeDao).getByUsername(username);
        validator.validateEntityNotNull(username, traineeToDelete.orElse(null));

        Optional<Trainee> deletedTrainee = ((JpaDaoTraineeImpl) traineeDao).deleteByUsername(username);
        logger.info("Deleting Trainee Profile with username {}", username);

        return deletedTrainee.isPresent();
    }

    @Override
    public boolean updateTraineePassword(int id, String newPassword) {
        Optional<Trainee> traineeToUpdate = traineeDao.get(id);
        validator.validateEntityNotNull(id, traineeToUpdate.orElse(null));

        Trainee foundTrainee = traineeToUpdate.orElse(null);
        if (traineeToUpdate.isPresent()) {
            foundTrainee.getUser().setPassword(newPassword);
            logger.info("Updating Trainee Password with id {}", id);
        }

        return traineeDao.update(id, foundTrainee) != null;
    }

    @Override
    public boolean updateTraineeActiveStatus(int id, boolean isActive) {
        Optional<Trainee> traineeToUpdate = traineeDao.get(id);
        validator.validateEntityNotNull(id, traineeToUpdate.orElse(null));

        Trainee foundTrainee = traineeToUpdate.orElse(null);
        if (traineeToUpdate.isPresent()) {
            foundTrainee.getUser().setIsActive(isActive);
            logger.info("Updating status for Trainee with id {} to {}", id, isActive);
        }

        return traineeDao.update(id, foundTrainee) != null;
    }

    @Override
    public List<Trainer> selectNotAssignedOnTraineeTrainersList(int traineeId) {
        Optional<Trainee> foundTrainee = traineeDao.get(traineeId);
        validator.validateEntityNotNull(traineeId, foundTrainee.orElse(null));

        Trainee selectedTrainee = foundTrainee.orElse(null);
        if (foundTrainee.isPresent()) {
            logger.info("Selecting not assigned on trainee with id {} trainers list", traineeId);
            return ((JpaDaoTraineeImpl) traineeDao).getNotAssignedOnTraineeTrainersList(selectedTrainee);
        }
        return List.of();

    }


}
