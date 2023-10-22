package org.example.service;

import org.example.configuration.Storage;
import org.example.model.User;

import java.util.List;

public class UsernameGeneratorImpl{
    public static int userNameExists(String firstName, String lastName, String separator, Storage storage){
        int serial = 0;
        for (User user:(List<User>) storage.getDao("users").getAll() ){
            if(user.getUsername().contains(firstName+separator+lastName)){
                serial++;
            }
        }
        return serial;
    }

    public static String generateUserName(String firstName, String lastName, String separator, Storage storage) {
        int serial = userNameExists(firstName, lastName,separator, storage);
        return serial>0? firstName+separator+lastName+serial: firstName+separator+lastName;
    }
}
