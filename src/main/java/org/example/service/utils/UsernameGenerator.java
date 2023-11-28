package org.example.service.utils;


public interface UsernameGenerator {
    int userNameExists(String firstName, String lastName);

    String generateUserName(String firstName, String lastName);
}
