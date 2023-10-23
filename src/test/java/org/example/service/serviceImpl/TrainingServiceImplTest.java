package org.example.service.serviceImpl;


import org.example.configuration.Storage;
import org.example.configuration.StorageImpl;
import org.example.dao.Dao;
import org.example.dao.DaoConnection;
import org.example.dao.DaoConnectionImpl;
import org.example.dao.DaoImpl;
import org.example.model.Training;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class TrainingServiceImplTest {

    private TrainingServiceImpl trainingService;
    private Storage storage = new StorageImpl();
    private TrainingServiceImpl mock = mock(TrainingServiceImpl.class);
    private DaoConnectionImpl<Training> daoConnection = new DaoConnectionImpl<>(Training.class);
    private List<Training> trainings = new ArrayList<>();
    @BeforeEach
    public void setUp(){
        Map<String, Dao> daos = new HashMap<>();
        daos.put("trainings", new DaoImpl<>(Training.class));
        storage.setDaos(daos);
        trainingService = new TrainingServiceImpl(storage);

        Training trainingTest = new Training(1,1,"Test Training", 1, new Date(),1);
        trainingTest.setId(1);
        Training trainingTest2 = new Training(2,2,"Test Training 2", 2, new Date(),1.5);
        trainingTest2.setId(2);
        trainings.add(trainingTest);
        trainings.add(trainingTest2);

        daoConnection = mock(DaoConnectionImpl.class);
        when(daoConnection.getEntities(anyString())).thenReturn(trainings);
        when(daoConnection.writeEntities(anyString(), anyList())).thenReturn(trainings);
        daos.get("trainings").setDaoConnection(daoConnection);
        daos.get("trainings").setFilePath("mockFilePath");

    }
    @Test
    void createTrainingProfileTest() {
        Training trainingTest = new Training(1,1,"Test Training", 1, new Date(),1);

        Training trainingsCreated = trainingService.createTrainingProfile(trainingTest);

        assertNotNull(trainingsCreated);
        verify(daoConnection, times(1)).writeEntities(anyString(), anyList());
    }

    @Test
    void selectTrainingProfileTest() {
        Training trainingTest = new Training(1,1,"Test Training", 1, new Date(),1);
        trainingTest.setId(1);

        Optional<Training> trainingSelected = trainingService.selectTrainingProfile(1);

        assertNotNull(trainingSelected);
        assertEquals(trainingTest.getId(), trainingSelected.get().getId());
        assertEquals(trainingTest.getTrainingName(), trainingSelected.get().getTrainingName());
        verify(daoConnection, times(1)).getEntities(anyString());



    }

    @Test
    void selectAllTest() {
        List<Training> selectedTrainings = trainingService.selectAll();
        assertNotNull(trainings);
        assertEquals(selectedTrainings, trainings);
    }
}