package com.epam.springcore.task.service.impl;

import com.epam.springcore.task.dao.TrainingDAO;
import com.epam.springcore.task.model.Training;
import com.epam.springcore.task.service.TrainingService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class TrainingServiceImpl implements TrainingService {

    private final TrainingDAO trainingDAO;

    public TrainingServiceImpl(TrainingDAO trainingDAO) {
        this.trainingDAO = trainingDAO;
    }

    @Override
    public Optional<Training> create(Training training) {
        long maxId = trainingDAO.getMaxId();
        training.setTrainingId(maxId);
        return trainingDAO.create(maxId, training);
    }

    @Override
    public Optional<Training> findById(long trainingId) {
        return trainingDAO.getById(trainingId);
    }

    @Override
    public List<Training> getAllTrainings() {
        return trainingDAO.getAllTrainings();
    }

}
