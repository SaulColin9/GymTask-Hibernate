package org.example.service.serviceImpl;

import org.example.configuration.Storage;
import org.example.model.Trainee;
import org.example.model.User;
import org.example.service.PasswordGeneratorImpl;
import org.example.service.TraineeService;
import org.example.service.UsernameGeneratorImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class TraineeServiceImpl implements TraineeService {
    Storage storage;
    private final static String TRAINEES_KEY = "trainees";
    public TraineeServiceImpl(Storage storage){
        this.storage = storage;
    }


    @Override
    public Trainee createTraineeProfile(String firstName, String lastName, Date dateOfBirth, String address) {
        String username = UsernameGeneratorImpl.generateUserName(firstName, lastName,".", storage);
        String passowrd = PasswordGeneratorImpl.generatePassword(10);
        User newUser = (User) storage.getDao("users")
                .save(new User(firstName, lastName, username, passowrd,true));
        return (Trainee) storage.getDao(TRAINEES_KEY).save(new Trainee(dateOfBirth, address, newUser.getId()));
    }

    @Override
    public Trainee updateTraineeProfile(int id, Trainee trainee) {
        return null;
    }

    @Override
    public Optional<Trainee> deleteTraineeProfile(int id) {
        return storage.getDao(TRAINEES_KEY).delete(id);
    }

    @Override
    public Optional<Trainee> selectTraineeProfile(int id) {
        return storage.getDao(TRAINEES_KEY).get(id);
    }

    @Override
    public List<Trainee> selectAll() {
        return storage.getDao(TRAINEES_KEY).getAll();
    }
}
