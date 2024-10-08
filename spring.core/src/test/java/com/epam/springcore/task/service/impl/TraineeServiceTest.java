package com.epam.springcore.task.service.impl;

import com.epam.springcore.task.mapper.TraineeMapper;
import com.epam.springcore.task.repo.TraineeRepository;
import com.epam.springcore.task.repo.TrainingRepository;
import com.epam.springcore.task.dto.PassUsernameDTO;
import com.epam.springcore.task.dto.TraineeDTO;
import com.epam.springcore.task.dto.TrainerDTO;
import com.epam.springcore.task.dto.TrainingDTO;
import com.epam.springcore.task.dto.TrainingTypeDTO;
import com.epam.springcore.task.dto.UserDTO;
import com.epam.springcore.task.mapper.TrainerMapper;
import com.epam.springcore.task.mapper.TrainingMapper;
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
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class TraineeServiceTest {


    @Mock
    private NameGenerator nameGenerator;
    @Mock
    private PasswordGenerator passwordGenerator;
    @Mock
    private TrainingMapper trainingMapper;
    @Mock
    private TraineeRepository traineeRepository;
    @Mock
    private TrainingRepository trainingRepository;
    @Mock
    private TrainerMapper trainerMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private TraineeMapper traineeMapper;
    @Mock
    private UserService userService;

    @InjectMocks
    private TraineeService traineeService;
    private TraineeDTO traineeDTO;
    private Trainee trainee;
    private User user;
    private PassUsernameDTO passUsernameDTO;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setUsername("testUser");
        user.setPassword("testPassword");

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("testUser");

        trainee = new Trainee();
        trainee.setUser(user);

        traineeDTO = new TraineeDTO();
        traineeDTO.setUser(userDTO);

        trainee = new Trainee();
        trainee.setUser(user);
        trainee.setBirthday(LocalDate.now());
        trainee.setAddress("Old Address");
        trainee.setTrainings(Collections.emptyList());
        trainee.setTrainers(Collections.emptySet());

        traineeDTO = new TraineeDTO();
        traineeDTO.setUser(new UserDTO());
        traineeDTO.getUser().setUsername("testUser");
        traineeDTO.setBirthday(LocalDate.now());
        traineeDTO.setAddress("New Address");
        traineeDTO.setTrainings(Collections.emptyList());
        traineeDTO.setTrainers(Collections.emptyList());

        passUsernameDTO = new PassUsernameDTO("testUser", "newPassword");

        when(nameGenerator.generateUniqueUsername(any(User.class))).thenReturn("generatedUsername");
        when(passwordGenerator.generatePassword()).thenReturn("generatedPassword");
        when(passwordEncoder.encode("generatedPassword")).thenReturn("encodedPassword");
        when(traineeMapper.traineeToEntity(any(TraineeDTO.class))).thenReturn(trainee);
        when(traineeMapper.traineeToDTO(any(Trainee.class))).thenReturn(traineeDTO);


    }

    @Test
    void testSaveTrainee() {
        TraineeDTO traineeDTO = new TraineeDTO();
        UserDTO userDTO = new UserDTO();
        traineeDTO.setUser(userDTO);

        User user = new User();
        user.setPassword("encodedPassword");
        user.setUsername("generatedUsername");

        PassUsernameDTO passUsernameDTO = new PassUsernameDTO("generatedUsername", "generatedPassword");
        when(userService.generateAndSaveUser(any(User.class))).thenReturn(passUsernameDTO);

        PassUsernameDTO result = traineeService.saveTrainee(traineeDTO);

        ArgumentCaptor<Trainee> traineeCaptor = ArgumentCaptor.forClass(Trainee.class);
        verify(traineeRepository).save(traineeCaptor.capture());
        Trainee savedTrainee = traineeCaptor.getValue();

        assertEquals("testPassword", savedTrainee.getUser().getPassword());
        assertEquals("testUser", savedTrainee.getUser().getUsername());

        assertNotNull(result);
        assertEquals("generatedUsername", result.getUsername());
        assertEquals("generatedPassword", result.getPassword());
    }

    @Test
    void testMatchTraineeCredentials_Success() {
        PassUsernameDTO passUsernameDTO = new PassUsernameDTO("generatedUsername", "generatedPassword");
        when(userService.matchUserCredentials(any(PassUsernameDTO.class))).thenReturn(true);
        boolean result = traineeService.matchTraineeCredentials(passUsernameDTO);
        assertTrue(result, "Credentials should match");
    }

    @Test
    void testFindTraineeByUsername_ValidUsername() {

        when(traineeRepository.findTraineeByUserUsername("testUser")).thenReturn(Optional.of(trainee));

        Optional<TraineeDTO> result = traineeService.findByUsername("testUser");

        assertTrue(result.isPresent());

        TraineeDTO actualDTO = result.get();
        assertEquals(traineeDTO.getUser().getUsername(), actualDTO.getUser().getUsername());
        assertEquals(traineeDTO.getBirthday(), actualDTO.getBirthday());
        assertEquals(traineeDTO.getTrainings(), actualDTO.getTrainings());
        assertEquals(traineeDTO.getTrainers(), actualDTO.getTrainers());
    }

    @Test
    void testFindTraineeByUsername_UsernameNotFound() {
        when(traineeRepository.findTraineeByUserUsername("nonExistingUsername")).thenReturn(Optional.of(trainee));

        Optional<TraineeDTO> result = traineeService.findByUsername("nonExistingUsername");

        assertTrue(result.isPresent());
    }

    @Test
    void testFindTraineeByUsername_NullUsername() {
        assertThrows(IllegalArgumentException.class, () -> traineeService.findByUsername(null));
    }

    @Test
    void testFindTraineeByUsername_EmptyUsername() {
        assertThrows(IllegalArgumentException.class, () -> traineeService.findByUsername(""));
    }

    @Test
    void testChangeTraineePassword() {
        user.setUsername("testUsername");
        trainee.setUser(user);

        PassUsernameDTO passUsernameDTO = new PassUsernameDTO("testUsername", "newPassword");

        when(traineeRepository.findTraineeByUserUsername("testUsername")).thenReturn(Optional.of(trainee));

        PassUsernameDTO result = traineeService.changeTraineePassword(passUsernameDTO);
        verify(userService).changeUserPassword("testUsername", "newPassword");
        assertNotNull(result);
        assertEquals("testUsername", result.getUsername());
        assertEquals("newPassword", result.getPassword());
    }
    @Test
    void testChangeTraineePassword_TraineeNotFound() {
        when(traineeRepository.findTraineeByUserUsername("nonExistingUser")).thenReturn(Optional.empty());

        PassUsernameDTO passUsernameDTO = new PassUsernameDTO("nonExistingUser", "newPassword");

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () ->
                traineeService.changeTraineePassword(passUsernameDTO)
        );

        assertEquals("Trainee with username nonExistingUser not found", thrown.getMessage());
    }

    @Test
    void testChangeTraineePassword_UserNull() {
        trainee.setUser(null);

        when(traineeRepository.findTraineeByUserUsername("testUser")).thenReturn(Optional.of(trainee));

        IllegalStateException thrown = assertThrows(IllegalStateException.class, () ->
                traineeService.changeTraineePassword(passUsernameDTO)
        );

        assertEquals("User associated with trainee is null", thrown.getMessage());
    }

    @Test
    void testUpdateTraineeProfile_Success() {
        // Настройка данных для теста
        trainee.setBirthday(LocalDate.now());
        trainee.setAddress("New Address");
        trainee.setTrainings(Collections.emptyList());
        trainee.setTrainers(Collections.emptySet());

        traineeDTO.setBirthday(LocalDate.now());
        traineeDTO.setAddress("New Address");
        traineeDTO.setTrainings(Collections.emptyList());
        traineeDTO.setTrainers(Collections.emptyList());

        when(traineeRepository.findTraineeByUserUsername("testUser")).thenReturn(Optional.of(trainee));
        when(trainingMapper.trainingToEntity(any())).thenReturn(new Training());
        when(trainerMapper.trainerToEntity(any())).thenReturn(new Trainer());
        when(traineeRepository.save(any(Trainee.class))).thenReturn(trainee);

        TraineeDTO result = traineeService.updateTraineeProfile("testUser", traineeDTO);

        assertEquals(LocalDate.now(), result.getBirthday());
        assertEquals("New Address", result.getAddress());
        assertEquals(Collections.emptyList(), result.getTrainings());
        assertEquals(Collections.emptyList(), result.getTrainers());
    }

    @Test
    void testActivateDeactivateTrainee_Success() {
        user.setUsername("testUsername");
        trainee.setUser(user);

        when(traineeRepository.findTraineeByUserUsername("testUsername")).thenReturn(Optional.of(trainee));

        traineeService.activateDeactivateTrainee("testUsername", true);
        verify(userService).activateDeactivateUser("testUsername", true);
    }

    @Test
    void testActivateDeactivateTrainee_TraineeNotFound() {

        when(traineeRepository.findTraineeByUserUsername("nonExistingUser")).thenReturn(Optional.empty());

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () ->
                traineeService.activateDeactivateTrainee("nonExistingUser", true)
        );

        assertEquals("Trainee with username nonExistingUser not found", thrown.getMessage());
    }

    @Test
    void testDeleteTrainee_Success() {

        trainee.setUser(user);

        when(traineeRepository.findTraineeByUserUsername("testUser")).thenReturn(Optional.of(trainee));

        traineeService.deleteTrainee("testUser");

        verify(traineeRepository).delete(trainee);
    }

    @Test
    void testDeleteTrainee_TraineeNotFound() {

        when(traineeRepository.findTraineeByUserUsername("nonExistingUser")).thenReturn(Optional.empty());

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () ->
                traineeService.deleteTrainee("nonExistingUser")
        );

        assertEquals("Trainee with username nonExistingUser not found", thrown.getMessage());
    }

    @Test
    void testGetTraineeTrainingsByCriteria() {
        LocalDate fromDate = LocalDate.of(2024, 1, 1);
        LocalDate toDate = LocalDate.of(2024, 12, 31);
        String traineeUsername = "testUser";
        String trainerUsername = "trainerUser";
        String trainingName = "Yoga";

        User user = new User();
        user.setUsername(traineeUsername);

        Trainee trainee = new Trainee();
        trainee.setUser(user);
        trainee.setBirthday(LocalDate.of(2024, 9, 18));
        trainee.setAddress("New Address");

        User trainerUser = new User();
        trainerUser.setUsername(trainerUsername);
        Trainer trainer = new Trainer();
        trainer.setUser(trainerUser);

        TrainingType trainingType = new TrainingType();
        trainingType.setName(trainingName);

        Training training = new Training();
        training.setTrainee(trainee);
        training.setDate(LocalDate.of(2024, 6, 15));
        training.setTrainer(trainer);
        training.setTrainingType(trainingType);

        TraineeDTO traineeDTO = new TraineeDTO();
        traineeDTO.setUser(new UserDTO(traineeUsername, null, null));
        traineeDTO.setBirthday(LocalDate.of(2024, 9, 18));
        traineeDTO.setAddress("New Address");

        TrainerDTO trainerDTO = new TrainerDTO();
        trainerDTO.setUser(new UserDTO(trainerUsername, null, null));

        TrainingDTO trainingDTO = new TrainingDTO();
        trainingDTO.setTrainee(traineeDTO);
        trainingDTO.setDate(LocalDate.of(2024, 6, 15));
        trainingDTO.setTrainer(trainerDTO);
        trainingDTO.setTrainingType(new TrainingTypeDTO(trainingName));

        when(trainingRepository.findByTraineeUserUsernameAndDateBetweenAndTrainerUserUsernameAndTrainingTypeName(
                traineeUsername, fromDate, toDate, trainerUsername, trainingName)).thenReturn(Collections.singletonList(training));

        when(trainingMapper.entityListToDTOList(anyList())).thenReturn(Collections.singletonList(trainingDTO));

        List<TrainingDTO> result = traineeService.getTraineeTrainingsByCriteria(traineeUsername, fromDate, toDate, trainerUsername, trainingName);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(trainingDTO, result.get(0));
    }

    @Test
    void testUpdateTraineeTrainers() {
        String traineeUsername = "testUser";

        Trainee existingTrainee = new Trainee();
        existingTrainee.setUser(new User());
        existingTrainee.getUser().setUsername(traineeUsername);
        existingTrainee.setTrainers(new HashSet<>());

        Set<TrainerDTO> newTrainerDTOs = new HashSet<>();
        TrainerDTO trainerDTO = new TrainerDTO();
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("newTrainerUser");
        trainerDTO.setUser(userDTO);
        newTrainerDTOs.add(trainerDTO);

        Trainer trainer = new Trainer();
        User trainerUser = new User();
        trainerUser.setUsername("newTrainerUser");
        trainer.setUser(trainerUser);

        when(traineeRepository.findTraineeByUserUsername(traineeUsername)).thenReturn(Optional.of(existingTrainee));
        when(trainerMapper.trainerToEntity(any(TrainerDTO.class))).thenReturn(trainer);
        when(traineeRepository.save(any(Trainee.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Trainee updatedTrainee = traineeService.updateTraineeTrainers(traineeUsername, newTrainerDTOs);

        assertNotNull(updatedTrainee.getTrainers(), "The trainers set in the updated Trainee should not be null");
        assertEquals(1, updatedTrainee.getTrainers().size(), "The number of trainers should be 1");
        assertTrue(updatedTrainee.getTrainers().contains(trainer), "The trainers set should contain the new trainer");

        ArgumentCaptor<Trainee> traineeCaptor = ArgumentCaptor.forClass(Trainee.class);
        verify(traineeRepository).save(traineeCaptor.capture());
        Trainee savedTrainee = traineeCaptor.getValue();
        assertNotNull(savedTrainee, "The saved Trainee should not be null");
        assertEquals(updatedTrainee, savedTrainee, "The saved Trainee should match the updated Trainee");
    }

    @Test
    void testFindById_TraineeFound(){
        long traineeId = 1L;
        trainee.setTraineeId(traineeId);

        User traineeUser = new User();
        traineeUser.setUsername("traineeUser");
        trainee.setUser(traineeUser);

        traineeDTO.setUser(new UserDTO("traineeUser", null, null));

        when(traineeRepository.findById(traineeId)).thenReturn(Optional.of(trainee));

        Optional<TraineeDTO> result = traineeService.findById(traineeId);

        assertTrue(result.isPresent());
        assertEquals(traineeDTO.getUser().getUsername(), result.get().getUser().getUsername());
    }
}