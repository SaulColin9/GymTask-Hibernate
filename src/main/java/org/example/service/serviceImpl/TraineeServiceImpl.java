package org.example.service.serviceImpl;

import org.example.dao.Dao;
import org.example.model.Trainee;
import org.example.model.User;
import org.example.service.PasswordGeneratorImpl;
import org.example.service.TraineeService;
import org.example.service.UsernameGeneratorImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Date;
import java.util.Optional;

public class TraineeServiceImpl implements TraineeService {
    private static final Logger logger = LoggerFactory.getLogger(TraineeService.class);

    private final String NO_ID_MSG = "Provided Trainee Id does not exist";
    private Dao<User> userDao;
    private Dao<Trainee> traineeDao;

    @Override
    public void setUserDao(Dao<User> userDao) {
        this.userDao = userDao;
    }

    @Override
    public void setTraineeDao(Dao<Trainee> traineeDao) {
        this.traineeDao = traineeDao;
    }



    @Override
    public Trainee createTraineeProfile(String firstName, String lastName, Date dateOfBirth, String address) {
        String username = UsernameGeneratorImpl.generateUserName(firstName, lastName,".", userDao);
        String password = PasswordGeneratorImpl.generatePassword(10);
        User newUser = userDao
                .save(new User(firstName, lastName, username, password,true));
        logger.info("Creating Trainee Profile with id " + newUser.getId());
        return  traineeDao.save(new Trainee(dateOfBirth, address, newUser.getId(), newUser));
    }

    @Override
    public Trainee updateTraineeProfile(int id, String firstName, String lastName, boolean isActive, Date dateOfBirth, String address) {
        Optional<Trainee> traineeToUpdate = traineeDao.get(id);
        if(traineeToUpdate.isEmpty()){
            logger.error(NO_ID_MSG);
            return null;
        }
        Optional<User> userToUpdate = userDao.get(traineeToUpdate.get().getUserId());
        if (userToUpdate.isEmpty()){
            logger.error("Trainee Profile User Id does not exist");
            return null;
        }

        userToUpdate.get().setFirstName(firstName);
        userToUpdate.get().setLastName(lastName);
        userToUpdate.get().setIsActive(isActive);
        String newUserName = UsernameGeneratorImpl.generateUserName(firstName, lastName,".", userDao);
        userToUpdate.get().setUsername(newUserName);

        userDao.update(traineeToUpdate.get().getUserId(), userToUpdate.get());

        traineeToUpdate.get().setDateOfBirth(dateOfBirth);
        traineeToUpdate.get().setAddress(address);
        traineeToUpdate.get().setUser(userToUpdate.get());

        logger.info("Updating Trainee Profile with id " + id);
        return traineeDao.update(id,traineeToUpdate.get());
    }

    @Override
    public Trainee deleteTraineeProfile(int id) {
        Optional<Trainee> traineeToDelete = traineeDao.get(id);
        if(traineeToDelete.isEmpty()){
            logger.error(NO_ID_MSG);
            return null;
        }
        logger.info("Deleting Trainee Profile with id " + id);
        Optional<Trainee> deletedTrainee = traineeDao.delete(id);
        return deletedTrainee.orElse(null);
    }

    @Override
    public Trainee selectTraineeProfile(int id) {
        Optional<Trainee> trainee = traineeDao.get(id);
        if(trainee.isEmpty()){
            logger.error(NO_ID_MSG);
            return null;
        }
        logger.info("Selecting Trainee Profile with id " + id);
        Trainee selectedTrainee = traineeDao.get(id).orElse(null);
        assert selectedTrainee != null;
        Optional<User> traineesUser = userDao.get(selectedTrainee.getUserId());
        selectedTrainee.setUser(traineesUser.orElse(null));
        return selectedTrainee;
    }

}
