package org.example.service;


public interface UsernameGenerator {
    int userNameExists(String firstName, String lastName);

    String generateUserName(String firstName, String lastName);
}
