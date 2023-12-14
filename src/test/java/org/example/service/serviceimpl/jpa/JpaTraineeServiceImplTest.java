package org.example.service.serviceimpl.jpa;

import org.example.dao.jpa.JpaDaoTraineeImpl;
import org.example.matchers.TraineeMatcher;
import org.example.model.Trainee;
import org.example.model.Trainer;
import org.example.model.User;
import org.example.service.utils.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class JpaTraineeServiceImplTest {

    @Mock
    Validator<Trainee> validator;
    @Mock
    JpaDaoTraineeImpl traineeDao;
    @InjectMocks
    JpaTraineeServiceImpl jpaTraineeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void givenValidUsername_TraineeShouldBeReturned() {
        // arrange
        String username = "John.Doe";
        when((traineeDao).getByUsername("John.Doe")).thenReturn(Optional.of(createNewTrainee()));

        // act
        Trainee actualResponse = jpaTraineeService.selectTraineeProfileByUsername(username);

        // assert
        assertThat(actualResponse).isNotNull();
        verify(traineeDao, times(1)).getByUsername("John.Doe");
    }

    @Test
    void givenValidId_TraineeShouldBeDeleted() {
        // arrange
        int id = 1;
        when(traineeDao.delete(1)).thenReturn(Optional.of(createNewTrainee()));

        // act
        boolean actualResponse = jpaTraineeService.deleteTraineeProfile(id);

        // assert
        assertThat(actualResponse).isTrue();
        verify(traineeDao, times(1)).delete(1);

    }

    @Test
    void givenValidUsername_TraineeShouldBeDeleted() {
        // arrange
        String username = "John.Doe";
        when(traineeDao.deleteByUsername("John.Doe")).thenReturn(Optional.of(createNewTrainee()));

        // act
        boolean actualResponse = jpaTraineeService.deleteTraineeProfileByUsername(username);

        // assert
        assertThat(actualResponse).isTrue();
        verify(traineeDao, times(1)).deleteByUsername("John.Doe");

    }

    @Test
    void givenValidRequest_TraineePasswordShouldBeUpdated() {
        // arrange
        int id = 1;
        String newPassword = "newPassword";
        Trainee traineeToUpdate = createNewTrainee();

        when(traineeDao.get(1)).thenReturn(Optional.of(traineeToUpdate));
        traineeToUpdate.getUser().setPassword(newPassword);
        when(traineeDao.update(eq(1), argThat(new TraineeMatcher(traineeToUpdate)))).thenReturn(traineeToUpdate);

        // act
        boolean actualResponse = jpaTraineeService.updateTraineePassword(id, newPassword);

        // assert
        assertThat(actualResponse).isTrue();
        verify(traineeDao, times(1)).get(1);
        verify(traineeDao, times(1))
                .update(eq(1), argThat(new TraineeMatcher(traineeToUpdate)));

    }

    @Test
    void givenValidRequest_TraineeStatusShouldBeUpdated() {
        // arrange
        int id = 1;
        boolean isActive = false;
        Trainee traineeToUpdate = createNewTrainee();

        when(traineeDao.get(1)).thenReturn(Optional.of(traineeToUpdate));
        traineeToUpdate.getUser().setIsActive(isActive);
        when(traineeDao.update(eq(1), argThat(new TraineeMatcher(traineeToUpdate)))).thenReturn(traineeToUpdate);

        // act
        boolean actualResponse = jpaTraineeService.updateTraineeTraineeStatus(id, isActive);

        // assert
        assertThat(actualResponse).isTrue();
        verify(traineeDao, times(1)).get(1);
        verify(traineeDao, times(1))
                .update(eq(1), argThat(new TraineeMatcher(traineeToUpdate)));

    }

    @Test
    void givenValidId_TrainerListShouldBeReturned() {
        // arrange
        int id = 1;
        Optional<Trainee> foundTrainee = Optional.of(createNewTrainee());
        List<Trainer> trainerList = new ArrayList<>();
        trainerList.add(new Trainer());
        trainerList.add(new Trainer());

        when(traineeDao.get(1)).thenReturn(foundTrainee);
        when(traineeDao.getNotAssignedOnTraineeTrainersList(argThat(new TraineeMatcher(foundTrainee.get())))).thenReturn(trainerList);

        // act
        List<Trainer> actualResponse = jpaTraineeService.selectNotAssignedOnTraineeTrainersList(id);

        // assert
        assertThat(actualResponse).isNotNull();
        verify(traineeDao, times(1)).get(1);
        verify(traineeDao, times(1)).getNotAssignedOnTraineeTrainersList(argThat(new TraineeMatcher(foundTrainee.get())));

    }

    Trainee createNewTrainee() {
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
}