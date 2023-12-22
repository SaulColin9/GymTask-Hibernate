package org.example.service.serviceimpl;

import org.example.dao.Dao;
import org.example.model.Trainee;
import org.example.model.User;
import org.example.service.TraineeService;
import org.example.service.utils.user.UserUtils;
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
    protected Validator validator;
    protected Dao<Trainee> traineeDao;


    @Override
    public Trainee createTraineeProfile(String firstName, String lastName, Date dateOfBirth, String address) {
        Map<String, Object> params = new HashMap<>();
        params.put("firstName", firstName);
        params.put("lastName", lastName);
        validator.validateFieldsNotNull(params);

        User newUser = userUtils.createUser(firstName, lastName);


        logger.info("Creating Trainee Profile with id {}", newUser.getId());
        return traineeDao.save(new Trainee(dateOfBirth, address, newUser));
    }

    @Override
    public boolean updateTraineeProfile(int id, String firstName, String lastName, boolean isActive, Date dateOfBirth, String address) {
        Optional<Trainee> traineeToUpdate = traineeDao.get(id);

        Trainee foundTrainee = traineeToUpdate.orElse(null);
        traineeToUpdate.ifPresent(trainee -> {
            int userId = trainee.getUser().getId();
            User updatedUser = userUtils.updateUser(userId,
                    firstName == null ? trainee.getUser().getFirstName() : firstName,
                    lastName == null ? trainee.getUser().getLastName() : lastName,
                    isActive
            );
            trainee.setUser(updatedUser);

            trainee.setDateOfBirth(dateOfBirth == null ? trainee.getDateOfBirth() : dateOfBirth);
            trainee.setAddress(address == null ? trainee.getAddress() : address);

            logger.info("Updating Trainee Profile with id {}", id);
        });

        return traineeDao.update(id, foundTrainee) != null;
    }

    @Override
    public boolean deleteTraineeProfile(int id) {
        Optional<Trainee> traineeToDelete = traineeDao.get(id);
        Map<String, Object> params = new HashMap<>();
        params.put("Trainee", traineeToDelete.orElse(null));
        validator.validateEntitiesNotNull(params);

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


        logger.info("Selecting Trainee Profile with id " + id);
        return trainee.orElse(null);
    }


    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    public void setTraineeDao(Dao<Trainee> traineeDao) {
        this.traineeDao = traineeDao;
    }

    public void setUserUtils(UserUtils userUtils) {
        this.userUtils = userUtils;
    }

}
