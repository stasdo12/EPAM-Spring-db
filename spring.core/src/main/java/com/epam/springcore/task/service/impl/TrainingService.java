package com.epam.springcore.task.service.impl;

import com.epam.springcore.task.client.MicroserviceClient;
import com.epam.springcore.task.dto.TrainingRequest;
import com.epam.springcore.task.model.Trainee;
import com.epam.springcore.task.model.Trainer;
import com.epam.springcore.task.model.TrainingType;
import com.epam.springcore.task.producer.RabbitMQSender;
import com.epam.springcore.task.repo.TraineeRepository;
import com.epam.springcore.task.repo.TrainerRepository;
import com.epam.springcore.task.repo.TrainingRepository;
import com.epam.springcore.task.dto.TrainingDTO;
import com.epam.springcore.task.mapper.TrainingMapper;
import com.epam.springcore.task.model.Training;
import com.epam.springcore.task.repo.TrainingTypeRepository;
import com.epam.springcore.task.service.ITrainingService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainingService implements ITrainingService {

    private static final Logger log = LoggerFactory.getLogger(TrainingService.class);
    public final TrainingRepository trainingRepository;
    public final TrainingMapper trainingMapper;
    private final TraineeRepository traineeRepository;
    private final TrainerRepository trainerRepository;
    private final TrainingTypeRepository trainingTypeRepository;
    private final MicroserviceClient microserviceClient;
    private final AuthService authService;

    private final RabbitMQSender rabbitMQSender;

    @Autowired
    public TrainingService(TrainingRepository trainingRepository, TrainingMapper trainingMapper,
                           TraineeRepository traineeRepository, TrainerRepository trainerRepository,
                           TrainingTypeRepository trainingTypeRepository,
                           MicroserviceClient microserviceClient, AuthService authService,
                           RabbitMQSender rabbitMQSender) {
        this.trainingRepository = trainingRepository;
        this.trainingMapper = trainingMapper;
        this.traineeRepository = traineeRepository;
        this.trainerRepository = trainerRepository;
        this.trainingTypeRepository = trainingTypeRepository;
        this.microserviceClient = microserviceClient;
        this.authService = authService;
        this.rabbitMQSender = rabbitMQSender;
    }

    @Override
    @CircuitBreaker(name = "trainingService", fallbackMethod = "fallbackActionTraining")
    public TrainingDTO addTraining(TrainingDTO trainingDTO) {
        if (trainingDTO == null || trainingDTO.getTrainee() == null || trainingDTO.getTrainer() == null) {
            throw new IllegalArgumentException("Training and associated Trainee/Trainer must not be null");
        }

        TrainingType trainingType = trainingTypeRepository.findByName(trainingDTO.getTrainingType().getName());
        Trainee trainee = traineeRepository.findTraineeByUserUsername(trainingDTO.getTrainee().getUser().getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Trainee not found"));

        Trainer trainer = trainerRepository.findTrainerByUserUsername(trainingDTO.getTrainer().getUser().getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Trainer not found"));

        Training training = trainingMapper.trainingToEntity(trainingDTO);
        training.setTrainee(trainee);
        training.setTrainer(trainer);
        training.setTrainingType(trainingType);
        Training savedTraining = trainingRepository.save(training);
        TrainingRequest trainingRequest = createTrainingRequest(trainingDTO, trainer, "ADD");
        String jwtToken = authService.getJwtToken();
//        microserviceClient.actionTraining(trainingRequest, MDC.get("transactionId"), "Bearer " + jwtToken);
        rabbitMQSender.sendTrainingRequest(trainingRequest, MDC.get("transactionId"), "Bearer " + jwtToken);
        return trainingMapper.trainingToDTO(savedTraining);
    }

    public TrainingDTO fallbackActionTraining(TrainingDTO trainingDTO, Throwable throwable) {
        log.error("Error calling training workload service: {}", throwable.getMessage());
        return new TrainingDTO();
    }

    public void deleteTraining(TrainingDTO trainingDTO) {
        if (trainingDTO == null || trainingDTO.getTrainee() == null || trainingDTO.getTrainer() == null) {
            throw new IllegalArgumentException("Training and associated Trainee/Trainer must not be null");
        }

        traineeRepository.findTraineeByUserUsername(trainingDTO.getTrainee().getUser().getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Trainee not found"));
        Trainer trainer = trainerRepository.findTrainerByUserUsername(trainingDTO.getTrainer().getUser().getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Trainer not found"));

        TrainingRequest trainingRequest = createTrainingRequest(trainingDTO, trainer, "DELETE");

        String jwtToken = authService.getJwtToken();
        rabbitMQSender.sendTrainingRequest(trainingRequest, MDC.get("transactionId"), "Bearer " + jwtToken);
//        microserviceClient.actionTraining(trainingRequest, MDC.get("transactionId"), "Bearer " + jwtToken);

        Training training = trainingMapper.trainingToEntity(trainingDTO);
        trainingRepository.delete(training);
        log.info("Deleted training work for username: {} with Transaction ID: {}", trainingRequest.getUsername(),
                MDC.get("transactionId"));
    }

    private TrainingRequest createTrainingRequest(TrainingDTO trainingDTO, Trainer trainer, String action) {
        return TrainingRequest.builder()
                .username(trainer.getUser().getUsername())
                .firstName(trainer.getUser().getFirstName())
                .lastName(trainer.getUser().getLastName())
                .isActive(trainer.getUser().isActive())
                .date(trainingDTO.getDate())
                .duration(trainingDTO.getDurationMinutes())
                .action(action)
                .build();
    }


}
