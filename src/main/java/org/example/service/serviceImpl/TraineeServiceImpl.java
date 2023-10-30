package org.example.service.serviceImpl;

import org.example.dao.Dao;
import org.example.model.Trainee;
import org.example.model.User;
import org.example.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Optional;

public class TraineeServiceImpl implements TraineeService {
    private UserUtils userUtils;
    private static final Logger logger = LoggerFactory.getLogger(TraineeService.class);

    private static final String NO_ID_MSG = "Provided Trainee Id does not exist";
    private Dao<Trainee> traineeDao;


    @Override
    public int createTraineeProfile(String firstName, String lastName, Date dateOfBirth, String address) {
        if (firstName == null || lastName == null) {
            logger.error("The next fields were not provided: {} {}",
                    (firstName == null ? "firstName, " : ""),
                    (lastName == null ? "lastName, " : ""));
            throw new IllegalArgumentException("firstName and lastName arguments cant be null");
        }
        User newUser = userUtils.createUser(firstName, lastName);

        logger.info("Creating Trainee Profile with id {}", newUser.getId());
        Trainee newTrainee = traineeDao.save(new Trainee(dateOfBirth, address, newUser));
        return newTrainee.getId();
    }

    @Override
    public boolean updateTraineeProfile(int id, String firstName, String lastName, boolean isActive, Date dateOfBirth, String address) {
        Optional<Trainee> traineeToUpdate = traineeDao.get(id);
        if (traineeToUpdate.isEmpty()) {
            logger.error(NO_ID_MSG);
            throw new IllegalArgumentException(NO_ID_MSG);
        }
        Trainee foundTrainee = traineeToUpdate.get();


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
        return traineeDao.update(id, foundTrainee) != null;
    }

    @Override
    public boolean deleteTraineeProfile(int id) {
        Optional<Trainee> traineeToDelete = traineeDao.get(id);
        if (traineeToDelete.isEmpty()) {
            logger.error(NO_ID_MSG);
            throw new IllegalArgumentException(NO_ID_MSG);
        }
        logger.info("Deleting Trainee Profile with id " + id);
        Optional<Trainee> deletedTrainee = traineeDao.delete(id);
        userUtils.deleteUser(traineeToDelete.get().getUser().getId());
        return deletedTrainee.isPresent();
    }

    @Override
    public Trainee selectTraineeProfile(int id) {
        Optional<Trainee> trainee = traineeDao.get(id);
        if (trainee.isEmpty()) {
            logger.error(NO_ID_MSG);
            throw new IllegalArgumentException(NO_ID_MSG);
        }
        logger.info("Selecting Trainee Profile with id " + id);
        return trainee.get();
    }


    public void setTraineeDao(Dao<Trainee> traineeDao) {
        this.traineeDao = traineeDao;
    }

    public void setUserUtils(UserUtils userUtils) {
        this.userUtils = userUtils;
    }

}
