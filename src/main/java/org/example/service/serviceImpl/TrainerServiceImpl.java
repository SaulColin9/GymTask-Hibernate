package org.example.service.serviceImpl;

import org.example.configuration.Storage;
import org.example.model.Trainer;
import org.example.model.User;
import org.example.service.PasswordGeneratorImpl;
import org.example.service.TrainerService;
import org.example.service.UsernameGeneratorImpl;

import java.util.Optional;


public class TrainerServiceImpl implements TrainerService {
    Storage storage;
    public TrainerServiceImpl(Storage storage){
        this.storage = storage;
    }
    @Override
    public Trainer createTrainerProfile(String firstName, String lastName, int specialization) {
            String username = UsernameGeneratorImpl.generateUserName(firstName, lastName,".", storage);
            String passowrd = PasswordGeneratorImpl.generatePassword(10);
            User newUser = (User) storage.getDao("users")
                    .save(new User(firstName, lastName, username, passowrd,true));

            return (Trainer) storage.getDao("trainers").save(new Trainer(specialization, newUser.getId()));
    }

    @Override
    public Trainer updateTrainerProfile(int id, Trainer trainer) {
        return (Trainer) storage.getDao("trainers").update(id, trainer);
    }

    @Override
    public Optional<Trainer> selectTrainerProfile(int id) {
        return storage.getDao("trainers").get(id);
    }
}
