package org.example.service;

import org.example.service.utils.PasswordGeneratorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;

class PasswordGeneratorImplTest {

    @InjectMocks
    PasswordGeneratorImpl passwordGenerator;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void givenTwoDifferentMethodCalls_PasswordReturnedShouldBeDifferent() {
        // arrange
        int passwordLength = 10;

        // act
        String passwordA = passwordGenerator.generatePassword(passwordLength);
        String passwordB = passwordGenerator.generatePassword(passwordLength);

        // assert
        assertThat(passwordA).isNotEqualTo(passwordB);

    }
}