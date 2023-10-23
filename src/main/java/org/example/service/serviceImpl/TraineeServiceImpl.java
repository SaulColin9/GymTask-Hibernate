package org.example.service.serviceImpl;

import org.example.configuration.Storage;
import org.example.model.Trainee;
import org.example.model.User;
import org.example.service.PasswordGeneratorImpl;
import org.example.service.TraineeService;
import org.example.service.UsernameGeneratorImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class TraineeServiceImpl implements TraineeService {
    Storage storage;
    private static final Logger logger = LoggerFactory.getLogger(TraineeService.class);

    public TraineeServiceImpl(Storage storage){
        this.storage = storage;
    }


    @Override
    public Trainee createTraineeProfile(String firstName, String lastName, Date dateOfBirth, String address) {
        String username = UsernameGeneratorImpl.generateUserName(firstName, lastName,".", storage);
        String password = PasswordGeneratorImpl.generatePassword(10);
        User newUser = storage.getUserDao()
                .save(new User(firstName, lastName, username, password,true));
        logger.info("Creating Trainee Profile with id " + newUser.getId());
        return  storage.getTraineeDao().save(new Trainee(dateOfBirth, address, newUser.getId(), newUser));
    }

    @Override
    public Trainee updateTraineeProfile(int id, String firstName, String lastName, boolean isActive, Date dateOfBirth, String address) {
        Optional<Trainee> traineeToUpdate = storage.getTraineeDao().get(id);
        if(traineeToUpdate.isEmpty()){
            logger.error("Provided Trainee Id does not exist");
            return null;
        }
        Optional<User> userToUpdate = storage.getUserDao().get(traineeToUpdate.get().getUserId());
        if (userToUpdate.isEmpty()){
            logger.error("Trainee Profile User Id does not exist");
            return null;
        }

        userToUpdate.get().setFirstName(firstName);
        userToUpdate.get().setLastName(lastName);
        userToUpdate.get().setIsActive(isActive);
        String newUserName = UsernameGeneratorImpl.generateUserName(firstName, lastName,".", storage);
        userToUpdate.get().setUsername(newUserName);

        storage.getUserDao().update(traineeToUpdate.get().getUserId(), userToUpdate.get());

        traineeToUpdate.get().setDateOfBirth(dateOfBirth);
        traineeToUpdate.get().setAddress(address);
        traineeToUpdate.get().setUser(userToUpdate.get());

        logger.info("Updating Trainee Profile with id " + id);
        return storage.getTraineeDao().update(id,traineeToUpdate.get());
    }

    @Override
    public Trainee deleteTraineeProfile(int id) {
        Optional<Trainee> traineeToDelete = storage.getTraineeDao().get(id);
        if(traineeToDelete.isEmpty()){
            logger.error("Provided Trainee Id does not exist");
            return null;
        }
        logger.info("Deleting Trainee Profile with id " + id);
        Optional<Trainee> deletedTrainee = storage.getTraineeDao().delete(id);
        return deletedTrainee.orElse(null);
    }

    @Override
    public Trainee selectTraineeProfile(int id) {
        Optional<Trainee> trainee = storage.getTraineeDao().get(id);
        if(trainee.isEmpty()){
            logger.error("Provided Trainee Id does not exist");
            return null;
        }
        logger.info("Selecting Trainee Profile with id " + id);
        Trainee selectedTrainee = storage.getTraineeDao().get(id).orElse(null);
        Optional<User> traineesUser = storage.getUserDao().get(selectedTrainee.getUserId());
        selectedTrainee.setUser(traineesUser.orElse(null));
        return selectedTrainee;
    }

    @Override
    public List<Trainee> selectAll() {
        List<Trainee> trainees = storage.getTraineeDao().getAll();
        for(Trainee trainee : trainees){
            Optional<User> traineesUser = storage.getUserDao().get(trainee.getUserId());
            trainee.setUser(traineesUser.orElse(null));
        }
        logger.info("Selecting All Trainee Profiles ");
        return trainees;
    }
}
