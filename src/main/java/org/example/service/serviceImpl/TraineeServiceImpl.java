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
    private static final String TRAINEES_KEY = "trainees";
    private static Logger logger = LoggerFactory.getLogger(TraineeService.class);

    public TraineeServiceImpl(Storage storage){
        this.storage = storage;
    }


    @Override
    public Trainee createTraineeProfile(String firstName, String lastName, Date dateOfBirth, String address) {
        String username = UsernameGeneratorImpl.generateUserName(firstName, lastName,".", storage);
        String passowrd = PasswordGeneratorImpl.generatePassword(10);
        User newUser = (User) storage.getDao("users")
                .save(new User(firstName, lastName, username, passowrd,true));
        logger.info("Creating Trainee Profile with id " + newUser.getId());
        return (Trainee) storage.getDao(TRAINEES_KEY).save(new Trainee(dateOfBirth, address, newUser.getId()));
    }

    @Override
    public Trainee updateTraineeProfile(int id, Trainee trainee) {
        logger.info("Updating Trainee Profile with id " + id);
        return (Trainee) storage.getDao(TRAINEES_KEY).update(id,trainee);
    }

    @Override
    public Optional<Trainee> deleteTraineeProfile(int id) {
        logger.info("Deleting Trainee Profile with id " + id);
        return storage.getDao(TRAINEES_KEY).delete(id);
    }

    @Override
    public Optional<Trainee> selectTraineeProfile(int id) {
        logger.info("Selecting Trainee Profile with id " + id);
        return storage.getDao(TRAINEES_KEY).get(id);
    }

    @Override
    public List<Trainee> selectAll() {
        logger.info("Selecting All Trainee Profiles ");
        return storage.getDao(TRAINEES_KEY).getAll();
    }
}
