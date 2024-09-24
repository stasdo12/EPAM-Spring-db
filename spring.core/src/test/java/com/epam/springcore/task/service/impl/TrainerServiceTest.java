package com.epam.springcore.task.service.impl;

import com.epam.springcore.task.mapper.TrainerMapper;
import com.epam.springcore.task.mapper.TrainingMapper;
import com.epam.springcore.task.mapper.TrainingTypeMapper;
import com.epam.springcore.task.repo.TraineeRepository;
import com.epam.springcore.task.repo.TrainerRepository;
import com.epam.springcore.task.repo.TrainingRepository;
import com.epam.springcore.task.repo.UserRepository;
import com.epam.springcore.task.dto.PassUsernameDTO;
import com.epam.springcore.task.dto.TraineeDTO;
import com.epam.springcore.task.dto.TrainerDTO;
import com.epam.springcore.task.dto.TrainingDTO;
import com.epam.springcore.task.dto.TrainingTypeDTO;
import com.epam.springcore.task.dto.UserDTO;
import com.epam.springcore.task.model.Trainee;
import com.epam.springcore.task.model.Trainer;
import com.epam.springcore.task.model.Training;
import com.epam.springcore.task.model.TrainingType;
import com.epam.springcore.task.model.User;
import com.epam.springcore.task.utils.NameGenerator;
import com.epam.springcore.task.utils.PasswordGenerator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.*;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TrainerServiceTest {

    @Mock
    private NameGenerator nameGenerator;
    @Mock
    private PasswordGenerator passwordGenerator;
    @Mock
    private TrainerRepository trainerRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private TraineeRepository traineeRepository;
    @Mock
    private TrainingRepository trainingRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private TrainerMapper trainerMapper;

    @Mock
    private TrainingTypeMapper trainingTypeMapper;

    @Mock
    private TrainingMapper trainingMapper;

    @InjectMocks
    private TrainerService trainerService;
    private TrainerDTO trainerDTO;
    private Trainer trainer;
    private User user;
    private PassUsernameDTO passUsernameDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);


        String rawPassword = "testPassword";
        user = new User();
        user.setUsername("testTrainer");
        user.setPassword(passwordEncoder.encode(rawPassword));


        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("testTrainer");

        trainer = new Trainer();
        trainer.setUser(user);

        trainerDTO = new TrainerDTO();
        trainerDTO.setUser(userDTO);

        passUsernameDTO = new PassUsernameDTO("testUser", "newPassword");

        when(nameGenerator.generateUniqueUsername(any(User.class))).thenReturn("generatedTrainerUsername");
        when(passwordGenerator.generatePassword()).thenReturn("generatedTrainerPassword");
        when(passwordEncoder.encode("generatedTrainerPassword")).thenReturn("encodedPassword");
        when(trainerMapper.trainerToEntity(any(TrainerDTO.class))).thenReturn(trainer);
        when(trainerMapper.trainerToDTO(any(Trainer.class))).thenReturn(trainerDTO);
    }

    @Test
    void testSaveTrainer() {
        PassUsernameDTO result = trainerService.saveTrainer(trainerDTO);

        verify(nameGenerator).generateUniqueUsername(any(User.class));
        verify(passwordGenerator).generatePassword();

        ArgumentCaptor<Trainer> trainerCaptor = ArgumentCaptor.forClass(Trainer.class);
        verify(trainerRepository).save(trainerCaptor.capture());
        Trainer savedTrainer = trainerCaptor.getValue();

        assertEquals("encodedPassword", savedTrainer.getUser().getPassword());
        assertEquals("generatedTrainerUsername", savedTrainer.getUser().getUsername());
        assertNotNull(result);
        assertEquals("generatedTrainerUsername", result.getUsername());
        assertEquals("generatedTrainerPassword", result.getPassword());
    }

    @Test
    void testMatchTrainerCredentials_Success() {
        String username = "generatedTrainerUsername";
        String password = "generatedTrainerPassword";

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, user.getPassword())).thenReturn(true);

        PassUsernameDTO passUsernameDTO = new PassUsernameDTO(username, password);

        boolean result = trainerService.matchTrainerCredentials(passUsernameDTO);

        assertTrue(result);
    }

    @Test
    void testFindTrainerByUsername_ValidUsername() {

        when(trainerRepository.findTrainerByUserUsername("testUser")).thenReturn(Optional.of(trainer));

        Optional<TrainerDTO> result = trainerService.findByUsername("testUser");

        assertTrue(result.isPresent());

        TrainerDTO actualDTO = result.get();
        assertEquals(trainerDTO.getUser().getUsername(), actualDTO.getUser().getUsername());
        assertEquals(trainerDTO.getTrainees(), actualDTO.getTrainees());
        assertEquals(trainerDTO.getSpecialization(), actualDTO.getSpecialization());
        assertEquals(trainerDTO.getTrainings(), actualDTO.getTrainings());
    }

    @Test
    void testFindTrainerByUsername_NullUsername() {
        assertThrows(IllegalArgumentException.class, () -> trainerService.findByUsername(null));
    }

    @Test
    void testFindTraineeByUsername_EmptyUsername() {
        assertThrows(IllegalArgumentException.class, () -> trainerService.findByUsername(""));
    }

    @Test
    void testChangeTrainerPassword_Success(){
        when(trainerRepository.findTrainerByUserUsername("testUser")).thenReturn(Optional.of(trainer));
        when(passwordEncoder.encode("newPassword")).thenReturn("hashedNewPassword");


        PassUsernameDTO result = trainerService.changeTrainerPassword(passUsernameDTO);

        assertEquals("hashedNewPassword", trainer.getUser().getPassword());
        assertEquals(passUsernameDTO, result);
    }

    @Test
    void testChangeTrainerPassword_TrainerNotFound(){
        when(trainerRepository.findTrainerByUserUsername("testUser")).thenReturn(Optional.empty());

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () ->
                trainerService.changeTrainerPassword(passUsernameDTO)
        );
        assertEquals("Trainer with username testUser not found", thrown.getMessage());
    }

    @Test
    void testChangeTrainerPassword_UserNull(){
        trainer.setUser(null);

        when(trainerRepository.findTrainerByUserUsername("testUser")).thenReturn(Optional.of(trainer));

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () ->
                trainerService.changeTrainerPassword(passUsernameDTO)
        );

        assertEquals("User associated with trainer is null", thrown.getMessage());
    }

    @Test
    void testUpdateTrainerProfile_Success() {
        trainer.setTrainerId(1L);
        trainer.setTrainees(Collections.emptySet());
        trainer.setSpecialization(new TrainingType(1L, "TestSpec"));
        trainer.setTrainings(Collections.emptyList());

        trainerDTO.setTrainees(Collections.emptySet());
        trainerDTO.setSpecialization(new TrainingTypeDTO("UpdatedSpec"));
        trainerDTO.setTrainings(Collections.emptyList());

        when(trainingTypeMapper.trainingTypeToEntity(any(TrainingTypeDTO.class))).thenReturn(new TrainingType(1L, "UpdatedSpec"));
        when(trainingMapper.dtoListToEntityList(anyList())).thenReturn(Collections.emptyList()); // Настройте по необходимости
        when(trainingMapper.entityListToDTOList(anyList())).thenReturn(Collections.emptyList()); // Настройте по необходимости

        when(trainerRepository.findTrainerByUserUsername("testUser")).thenReturn(Optional.of(trainer));
        when(trainerRepository.save(any(Trainer.class))).thenReturn(trainer);

        TrainerDTO result = trainerService.updateTrainerProfile("testUser", trainerDTO);

        verify(trainerRepository).findTrainerByUserUsername("testUser");
        ArgumentCaptor<Trainer> trainerCaptor = ArgumentCaptor.forClass(Trainer.class);
        verify(trainerRepository).save(trainerCaptor.capture());
        Trainer savedTrainer = trainerCaptor.getValue();

        assertEquals("UpdatedSpec", savedTrainer.getSpecialization().getName());
        assertEquals(trainerDTO.getSpecialization().getName(), result.getSpecialization().getName());
    }

    @Test
    void testActivateDeactivateTrainer_Success(){

        trainer.setUser(user);
        user.setActive(false);

        when(trainerRepository.findTrainerByUserUsername("testUser")).thenReturn(Optional.of(trainer));

        trainerService.activateDeactivateTrainer("testUser", true);

        assertTrue(user.isActive());
        verify(trainerRepository).save(trainer);
    }

    @Test
    void testGetTrainerTrainingsByCriteria() {
        LocalDate fromDate = LocalDate.of(2024, 1, 1);
        LocalDate toDate = LocalDate.of(2024, 12, 31);
        String trainerUsername = "trainerUser";
        String traineeUsername = "testUser";
        String trainingName = "Yoga";

        User trainerUser = new User();
        trainerUser.setUsername(trainerUsername);
        Trainer trainer = new Trainer();
        trainer.setUser(trainerUser);

        User traineeUser = new User();
        traineeUser.setUsername(traineeUsername);
        Trainee trainee = new Trainee();
        trainee.setUser(traineeUser);

        TrainingType trainingType = new TrainingType();
        trainingType.setName(trainingName);

        Training training = new Training();
        training.setTrainer(trainer);
        training.setTrainee(trainee);
        training.setDate(LocalDate.of(2024, 6, 15));
        training.setTrainingType(trainingType);

        TrainerDTO trainerDTO = new TrainerDTO();
        trainerDTO.setUser(new UserDTO(trainerUsername, null, null));

        TraineeDTO traineeDTO = new TraineeDTO();
        traineeDTO.setUser(new UserDTO(traineeUsername, null, null));

        TrainingDTO trainingDTO = new TrainingDTO();
        trainingDTO.setTrainer(trainerDTO);
        trainingDTO.setTrainee(traineeDTO);
        trainingDTO.setDate(LocalDate.of(2024, 6, 15));
        trainingDTO.setTrainingType(new TrainingTypeDTO(trainingName));

        when(trainingRepository.findByTrainee_User_UsernameAndDateBetweenAndTrainer_User_UsernameAndTrainingType_Name(
                trainerUsername, fromDate, toDate, traineeUsername, trainingName)).thenReturn(Collections.singletonList(training));

        when(trainingMapper.entityListToDTOList(anyList())).thenReturn(Collections.singletonList(trainingDTO));

        List<TrainingDTO> result = trainerService.getTrainerTrainingsByCriteria(trainerUsername, fromDate, toDate, traineeUsername, trainingName);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(trainingDTO, result.get(0));
    }

    @Test
    void testGetTrainersNotAssignedToTrainee_Success() {
        String traineeUsername = "testTrainee";

        User traineeUser = new User();
        traineeUser.setUsername(traineeUsername);

        Trainee trainee = new Trainee();
        trainee.setUser(traineeUser);

        User trainerUser1 = new User();
        trainerUser1.setUsername("trainer1");
        Trainer trainer1 = new Trainer();
        trainer1.setUser(trainerUser1);

        User trainerUser2 = new User();
        trainerUser2.setUsername("trainer2");
        Trainer trainer2 = new Trainer();
        trainer2.setUser(trainerUser2);

        List<Trainer> trainers = List.of(trainer1, trainer2);

        when(traineeRepository.findTraineeByUserUsername(traineeUsername)).thenReturn(Optional.of(trainee));
        when(trainerRepository.findTrainersNotAssignedToTrainee(traineeUsername)).thenReturn(trainers);

        // Настройка мока для преобразования Trainer в TrainerDTO
        when(trainerMapper.trainerToDTO(any(Trainer.class))).thenAnswer(invocation -> {
            Trainer trainer = invocation.getArgument(0);
            return new TrainerDTO(
                    new UserDTO(trainer.getUser().getUsername(), null, null),
                    new TrainingTypeDTO(),
                    new ArrayList<>(),
                    new HashSet<>()
            );
        });

        List<TrainerDTO> result = trainerService.getTrainersNotAssignedToTrainee(traineeUsername);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("trainer1", result.get(0).getUser().getUsername());
        assertEquals("trainer2", result.get(1).getUser().getUsername());
    }
    @Test
    void testFindById_TrainerFound() {
        Long trainerId = 1L;

        Trainer trainer = new Trainer();
        trainer.setTrainerId(trainerId);

        User trainerUser = new User();
        trainerUser.setUsername("testTrainer");
        trainer.setUser(trainerUser);

        TrainerDTO trainerDTO = new TrainerDTO();
        trainerDTO.setUser(new UserDTO("testTrainer", null, null));

        when(trainerRepository.findById(trainerId)).thenReturn(Optional.of(trainer));

        Optional<TrainerDTO> result = trainerService.findById(trainerId);

        assertTrue(result.isPresent());
        assertEquals(trainerDTO.getUser().getUsername(), result.get().getUser().getUsername());

    }
}
