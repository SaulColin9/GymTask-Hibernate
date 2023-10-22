package org.example.service;

import org.example.configuration.Storage;

public interface  UsernameGenerator {
    int userNameExists(String firstName, String lastName, Storage storage);
    String generateUserName(String firstName, String lastName,String separator, Storage storage);

}
