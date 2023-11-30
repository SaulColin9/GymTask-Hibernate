package org.example.entitiesFactory;

import org.example.model.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class EntitiesFactory {
    public Training createNewTraining() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        TrainingType newTrainingType = new TrainingType();
        newTrainingType.setTrainingTypeName("Cardio");
        newTrainingType.setId(1);

        Training newTraining = new Training();
        newTraining.setTrainingType(newTrainingType);
        newTraining.setTrainingName("Elite");
        try {
            newTraining.setTrainingDate(sdf.parse("2003-06-05"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        newTraining.setTrainingDuration(1.0);
        newTraining.setTrainee(createNewTrainee());
        newTraining.setTrainer(createNewTrainer());
        newTraining.setId(1);

        return newTraining;
    }

    public Trainee createNewTrainee() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        User newUser = new User();

        newUser.setIsActive(true);
        newUser.setFirstName("John");
        newUser.setLastName("Doe");
        newUser.setPassword("password");
        newUser.setUsername("John.Doe");
        newUser.setId(1);

        Trainee newTrainee = new Trainee();
        newTrainee.setAddress("Test Address");
        try {
            newTrainee.setDateOfBirth(sdf.parse("2003-06-05"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        newTrainee.setUser(newUser);
        newTrainee.setId(1);

        return newTrainee;
    }

    public Trainer createNewTrainer() {
        User newUser = new User();
        newUser.setFirstName("Jane");
        newUser.setLastName("Doe");
        newUser.setPassword("password");
        newUser.setUsername("Jane.Doe");
        newUser.setId(1);

        TrainingType newTrainingType = new TrainingType();
        newTrainingType.setTrainingTypeName("Cardio");
        newTrainingType.setId(1);

        Trainer newTrainer = new Trainer();
        newTrainer.setSpecialization(newTrainingType);
        newTrainer.setId(1);
        newTrainer.setUser(newUser);

        return newTrainer;
    }

    public User createNewUser() {
        User newUser = new User();

        newUser.setIsActive(true);
        newUser.setFirstName("John");
        newUser.setLastName("Doe");
        newUser.setPassword("password");
        newUser.setUsername("John.Doe");
        newUser.setId(1);
        return newUser;
    }

    public TrainingType createNewTrainingType() {
        TrainingType newTrainingType = new TrainingType();
        newTrainingType.setTrainingTypeName("Cardio");
        newTrainingType.setId(1);

        return newTrainingType;
    }


}
