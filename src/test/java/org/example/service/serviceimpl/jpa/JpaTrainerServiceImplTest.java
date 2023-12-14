package org.example.service.serviceimpl.jpa;

import org.example.dao.jpa.JpaDaoTrainerImpl;
import org.example.entitiesFactory.EntitiesFactory;
import org.example.matchers.TrainerMatcher;
import org.example.model.Trainer;
import org.example.service.utils.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


class JpaTrainerServiceImplTest {

    EntitiesFactory entitiesFactory;
    @Mock
    Validator<Trainer> validator;
    @Mock
    JpaDaoTrainerImpl trainerDao;
    @InjectMocks
    JpaTrainerServiceImpl jpaTrainerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        entitiesFactory = new EntitiesFactory();
    }

    @Test
    void givenValidUsername_TrainerShouldBeReturned() {
        // arrange
        String username = "John.Doe";
        when(trainerDao.getByUsername("John.Doe")).thenReturn(Optional.of(createNewTrainer()));
        // act
        Trainer actualResponse = jpaTrainerService.selectTrainerProfileByUsername(username);
        // assert
        assertThat(actualResponse).isNotNull();
        verify(trainerDao, times(1)).getByUsername("John.Doe");
    }

    @Test
    void givenValidRequest_TrainerPasswordShouldBeUpdated() {
        // arrange
        int id = 1;
        String newPassword = "newPassword";
        Trainer trainerToUpdate = createNewTrainer();

        when(trainerDao.get(1)).thenReturn(Optional.of(trainerToUpdate));
        trainerToUpdate.getUser().setPassword(newPassword);
        when(trainerDao.update(eq(1), argThat(new TrainerMatcher(trainerToUpdate)))).thenReturn(trainerToUpdate);
        // act
        boolean actualResponse = jpaTrainerService.updateTrainerPassword(id, newPassword);

        // assert
        assertThat(actualResponse).isTrue();
        verify(trainerDao, times(1)).get(1);
        verify(trainerDao, times(1))
                .update(eq(1), argThat(new TrainerMatcher(trainerToUpdate)));
    }

    @Test
    void givenValidRequest_TrainerStatusShouldBeUpdated() {
        // arrange
        int id = 1;
        boolean isActive = false;
        Trainer trainerToUpdate = createNewTrainer();

        when(trainerDao.get(1)).thenReturn(Optional.of(trainerToUpdate));
        trainerToUpdate.getUser().setIsActive(isActive);
        when(trainerDao.update(eq(1), argThat(new TrainerMatcher(trainerToUpdate)))).thenReturn(trainerToUpdate);
        // act
        boolean actualResponse = jpaTrainerService.updateTrainerTraineeStatus(id, isActive);

        // assert
        assertThat(actualResponse).isTrue();
        verify(trainerDao, times(1)).get(1);
        verify(trainerDao, times(1))
                .update(eq(1), argThat(new TrainerMatcher(trainerToUpdate)));
    }

    @Test
    void givenValidRequest_TraineeTrainersListShouldBeUpdated() {
        // arrange
        int trainee_id = 1;
        int trainer_id = 1;
        Optional<Trainer> foundTrainer = Optional.of(createNewTrainer());
        List<Trainer> trainerList = new ArrayList<>();
        trainerList.add(new Trainer());
        trainerList.add(new Trainer());

        when(trainerDao.get(trainer_id)).thenReturn(foundTrainer);
        when(trainerDao.updateTraineeTrainersList(eq(trainee_id), argThat(new TrainerMatcher(foundTrainer.get()))))
                .thenReturn(trainerList);
        // act
        List<Trainer> actualResponse = jpaTrainerService.updateTraineeTrainersList(trainee_id, trainer_id);
        // assert
        assertThat(actualResponse).isNotNull();
        verify(trainerDao, times(1)).get(trainer_id);
        verify(trainerDao, times(1)).updateTraineeTrainersList(eq(trainee_id), argThat(new TrainerMatcher(foundTrainer.get())));
    }

    Trainer createNewTrainer() {
        return entitiesFactory.createNewTrainer();
    }
}