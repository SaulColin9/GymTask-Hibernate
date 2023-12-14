package org.example.service.serviceimpl;

import org.example.dao.Dao;
import org.example.matchers.TraineeMatcher;
import org.example.model.Trainee;
import org.example.model.User;
import org.example.service.utils.UserUtils;
import org.example.service.utils.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;


class TraineeServiceImplTest {
    @Mock
    private Dao<Trainee> traineeDao;
    @Mock
    private UserUtils userUtils;
    @Mock
    private Validator<Trainee> validator;
    @InjectMocks
    private TraineeServiceImpl traineeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void givenValidRequest_TraineeShouldBeCreated() {
        // arrange
        Trainee testTrainee = createNewTrainee();

        when(traineeDao.save(argThat(new TraineeMatcher(testTrainee)))).thenReturn(testTrainee);
        when(userUtils.createUser("John", "Doe")).thenReturn(testTrainee.getUser());

        // act
        int createdTraineeId = traineeService.createTraineeProfile("John", "Doe", new Date(1054789200000L), "Test Address");

        // assert
        assertThat(createdTraineeId)
                .isEqualTo(1);
        verify(traineeDao, times(1)).save(argThat(new TraineeMatcher(testTrainee)));
        verify(userUtils, times(1)).createUser("John", "Doe");

    }


    @Test
    void givenValidRequest_TraineeShouldBeUpdated() {
        // arrange
        Date newDate = new Date();
        Trainee testTrainee = createNewTrainee();

        User updatedUser = new User();
        updatedUser.setId(1);
        updatedUser.setFirstName("Jean");
        updatedUser.setLastName("Doe");
        updatedUser.setIsActive(false);
        updatedUser.setUsername("Jean.Doe");

        Trainee updatedTrainee = new Trainee();
        updatedTrainee.setId(1);
        updatedTrainee.setUser(updatedUser);
        updatedTrainee.setDateOfBirth(newDate);
        updatedTrainee.setAddress("New Address");


        when(traineeDao.get(1)).thenReturn(Optional.of(testTrainee));
        when(userUtils.updateUser(1, "Jean", "Doe", false)).thenReturn(updatedUser);
        when(traineeDao.update(eq(1), argThat(new TraineeMatcher(updatedTrainee)))).thenReturn(updatedTrainee);

        // act
        boolean actualResponse = traineeService.updateTraineeProfile(1, "Jean", "Doe", false, newDate, "New Address");


        // assert
        assertThat(actualResponse).isTrue();
        verify(traineeDao, times(1)).get(1);
        verify(userUtils, times(1)).updateUser(1, "Jean", "Doe", false);
        verify(traineeDao, times(1)).update(eq(1), argThat(new TraineeMatcher(testTrainee)));

    }

    @Test
    void givenValidId_TraineeShouldBeDeleted() {
        // arrange
        Trainee testTrainee = createNewTrainee();

        when(traineeDao.get(1)).thenReturn(Optional.of(testTrainee));
        when(traineeDao.delete(1)).thenReturn(Optional.of(testTrainee));

        // act
        boolean actualResponse = traineeService.deleteTraineeProfile(1);

        //assert
        assertThat(actualResponse).isTrue();
        verify(traineeDao, times(1)).get(1);
        verify(traineeDao, times(1)).delete(1);

    }

    @Test
    void givenExistingTraineeId_TraineeShouldBeReturned() {
        // arrange
        Trainee testTrainee = createNewTrainee();

        when(traineeDao.get(1)).thenReturn(Optional.of(testTrainee));
        // act
        Trainee actualResponse = traineeService.selectTraineeProfile(1);

        // assert
        assertThat(actualResponse).isNotNull();
        verify(traineeDao, times(1)).get(1);

    }

    @Test
    void givenNonExistingTraineeIdSelect_ThrowsException() {
        // arrange
        doThrow(new IllegalArgumentException()).when(validator).validateEntityNotNull(77, null);
        when(traineeDao.get(77)).thenReturn(Optional.empty());

        // act

        // assert
        assertThatThrownBy(() -> traineeService.selectTraineeProfile(77)).
                isInstanceOf(IllegalArgumentException.class);
        verify(traineeDao, times(1)).get(77);

    }

    @Test
    void givenNonExistingTraineeIdDelete_ThrowsException() {
        // arrange
        doThrow(new IllegalArgumentException()).when(validator).validateEntityNotNull(77, null);
        when(traineeDao.get(77)).thenReturn(Optional.empty());

        // act

        // assert
        assertThatThrownBy(() -> traineeService.deleteTraineeProfile(77)).
                isInstanceOf(IllegalArgumentException.class);
        verify(traineeDao, times(1)).get(77);

    }

    @Test
    void givenNonExistingTraineeIdUpdate_ThrowsException() {
        // arrange
        doThrow(new IllegalArgumentException()).when(validator).validateEntityNotNull(77, null);
        when(traineeDao.get(77)).thenReturn(Optional.empty());

        // act

        // assert
        assertThatThrownBy(
                () -> traineeService.updateTraineeProfile(77, "Jean", "Doe", false, new Date(), "New Address")).
                isInstanceOf(IllegalArgumentException.class);
        verify(traineeDao, times(1)).get(77);

    }

    @Test
    void givenInvalidRequestCreate_ThrowsException() {
        // arrange
        Map<String, Object> params = new HashMap<>();
        params.put("firstName", null);
        params.put("lastName", null);
        doThrow(new IllegalArgumentException()).when(validator).validateFieldsNotNull(params);

        // act

        // assert
        assertThatThrownBy(
                () -> traineeService.createTraineeProfile(null, null, new Date(), "Address")).
                isInstanceOf(IllegalArgumentException.class);

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