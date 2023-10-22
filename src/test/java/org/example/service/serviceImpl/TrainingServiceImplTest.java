package org.example.service.serviceImpl;


import org.example.configuration.Storage;
import org.example.configuration.StorageImpl;
import org.example.dao.Dao;
import org.example.dao.DaoConnection;
import org.example.dao.DaoConnectionImpl;
import org.example.dao.daoImpl.TrainingDao;
import org.example.model.Training;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
class TrainingServiceImplTest {

//    @Autowired
    private TrainingServiceImpl trainingService;
//    @Autowired
    private Storage storage = new StorageImpl();
    private TrainingServiceImpl mock = mock(TrainingServiceImpl.class);
    private DaoConnectionImpl<Training> daoConnection;
    @Before
    public void setUp(){
        Map<String, Dao> daos = new HashMap<>();
        daos.put("trainings", new TrainingDao());
        storage.setDaos(daos);
        trainingService = new TrainingServiceImpl(storage);
        daoConnection = mock(DaoConnectionImpl.class);
        daos.get("trainings").setDaoConnection(daoConnection);
    }
    @Test
    void createTrainingProfile() {
        Map<String, Dao> daos = new HashMap<>();
        daos.put("trainings", new TrainingDao());
        storage.setDaos(daos);
        trainingService = new TrainingServiceImpl(storage);
        daoConnection = mock(DaoConnectionImpl.class);
        daos.get("trainings").setDaoConnection(daoConnection);
        daos.get("trainings").setFilePath("mockFilePath");

        Training trainingTest = new Training(1,1,"Test Training", 1, new Date(),1);
        List<Training> trainings = new ArrayList<>();
        trainings.add(trainingTest);
        System.out.println(daoConnection);
        when(daoConnection.writeEntities(anyString(), anyList())).thenReturn(trainings);
        Training trainingsCreated = trainingService.createTrainingProfile(trainingTest);
        assertNotNull(trainingsCreated);
        verify(daoConnection, times(1)).writeEntities(anyString(), anyList());

//        when(mock.createTrainingProfile(any(Training.class))).thenReturn(trainingTest);
//
//        Training createdTraining = mock.createTrainingProfile(trainingTest);
//        assertNotNull(createdTraining.getTrainingName());
//        verify(mock, times(1)).createTrainingProfile(trainingTest);
    }

    @Test
    void selectTrainingProfile() {
        Optional<Training> trainingTest = Optional.of(new Training(1, 1, "Test Training", 1, new Date(), 1));
        trainingTest.get().setId(1);
        TrainingDao storageMock = (TrainingDao) mock(storage.getDao("trainings").getClass());

        when(storageMock.get(1)).thenReturn(trainingTest);
//        when(mock.selectTrainingProfile(trainingTest.getId())).thenReturn(Optional.of(trainingTest));
        Optional<Training> selectedTraining = storageMock.get(trainingTest.get().getId());
        System.out.println(selectedTraining.get());
        assertNotNull(selectedTraining.get());
//        System.out.println(storage.getDao("trainings").getClass());
    }

    @Test
    void selectAll() {
        List<Training> trainingTests = new ArrayList<>();
        trainingTests.add(new Training(1,1,"Test Training", 1, new Date(),1));
        trainingTests.add(new Training(2,2,"Test Training 2", 2, new Date(),1));

        when(mock.selectAll()).thenReturn(trainingTests);
        List<Training> trainings = mock.selectAll();
        assertNotNull(trainings);
    }
}