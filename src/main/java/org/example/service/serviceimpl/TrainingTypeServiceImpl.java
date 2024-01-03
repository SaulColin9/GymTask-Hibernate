package org.example.service.serviceimpl;

import org.example.dao.Dao;
import org.example.model.TrainingType;
import org.example.service.TrainingTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class TrainingTypeServiceImpl implements TrainingTypeService {
    protected static final Logger logger = LoggerFactory.getLogger(TrainingTypeService.class);
    private Dao<TrainingType> trainingTypeDao;

    @Override

    public List<TrainingType> selectTrainingTypeList() {
        return trainingTypeDao.getAll();
    }

    public void setTrainingTypeDao(Dao<TrainingType> trainingTypeDao) {
        this.trainingTypeDao = trainingTypeDao;
    }
}
