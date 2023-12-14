package org.example.service.serviceimpl;

import org.example.dao.Dao;
import org.example.model.Trainee;
import org.example.model.User;
import org.example.service.TraineeService;
import org.example.service.utils.UserUtils;
import org.example.service.utils.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TraineeServiceImpl implements TraineeService {
    private UserUtils userUtils;
    protected static final Logger logger = LoggerFactory.getLogger(TraineeService.class);
    protected Validator<Trainee> validator;
    protected Dao<Trainee> traineeDao;


    @Override
    public int createTraineeProfile(String firstName, String lastName, Date dateOfBirth, String address) {
        Map<String, Object> params = new HashMap<>();
        params.put("firstName", firstName);
        params.put("lastName", lastName);
        validator.validateFieldsNotNull(params);

        User newUser = userUtils.createUser(firstName, lastName);

        logger.info("Creating Trainee Profile with id {}", newUser.getId());
        Trainee newTrainee = traineeDao.save(new Trainee(dateOfBirth, address, newUser));
        return newTrainee.getId();
    }

    @Override
    public boolean updateTraineeProfile(int id, String firstName, String lastName, boolean isActive, Date dateOfBirth, String address) {
        Optional<Trainee> traineeToUpdate = traineeDao.get(id);
        validator.validateEntityNotNull(id, traineeToUpdate.orElse(null));

        Trainee foundTrainee = traineeToUpdate.orElse(null);
        if (traineeToUpdate.isPresent()) {
            int userId = foundTrainee.getUser().getId();
            User updatedUser = userUtils.updateUser(userId,
                    firstName == null ? foundTrainee.getUser().getFirstName() : firstName,
                    lastName == null ? foundTrainee.getUser().getLastName() : lastName,
                    isActive
            );
            foundTrainee.setUser(updatedUser);

            foundTrainee.setDateOfBirth(dateOfBirth == null ? foundTrainee.getDateOfBirth() : dateOfBirth);
            foundTrainee.setAddress(address == null ? foundTrainee.getAddress() : address);

            logger.info("Updating Trainee Profile with id {}", id);
        }


        return traineeDao.update(id, foundTrainee) != null;
    }

    @Override
    public boolean deleteTraineeProfile(int id) {
        Optional<Trainee> traineeToDelete = traineeDao.get(id);
        validator.validateEntityNotNull(id, traineeToDelete.orElse(null));


        Optional<Trainee> deletedTrainee = traineeDao.delete(id);
        if (traineeToDelete.isPresent()) {
            userUtils.deleteUser(traineeToDelete.get().getUser().getId());
            logger.info("Deleting Trainee Profile with id " + id);
        }
        return deletedTrainee.isPresent();
    }

    @Override
    public Trainee selectTraineeProfile(int id) {
        Optional<Trainee> trainee = traineeDao.get(id);
        validator.validateEntityNotNull(id, trainee.orElse(null));
        logger.info("Selecting Trainee Profile with id " + id);
        return trainee.orElse(null);
    }


    public void setValidator(Validator<Trainee> validator) {
        this.validator = validator;
    }

    public void setTraineeDao(Dao<Trainee> traineeDao) {
        this.traineeDao = traineeDao;
    }

    public void setUserUtils(UserUtils userUtils) {
        this.userUtils = userUtils;
    }

}
