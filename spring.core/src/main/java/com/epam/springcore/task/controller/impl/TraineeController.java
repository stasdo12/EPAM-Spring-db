package com.epam.springcore.task.controller.impl;

import com.epam.springcore.task.controller.ITraineeController;
import com.epam.springcore.task.dto.JwtResponse;
import com.epam.springcore.task.dto.PassUsernameDTO;
import com.epam.springcore.task.dto.TraineeDTO;
import com.epam.springcore.task.dto.TrainerDTO;
import com.epam.springcore.task.dto.TrainingDTO;
import com.epam.springcore.task.facade.GymFacade;
import com.epam.springcore.task.health.metrics.ExecutionTimeMetrics;
import com.epam.springcore.task.health.metrics.RequestMetrics;
import com.epam.springcore.task.service.impl.JwtService;
import com.epam.springcore.task.service.impl.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/trainees")
public class TraineeController implements ITraineeController {

    private final GymFacade gymFacade;
    private final RequestMetrics requestMetrics;
    private final ExecutionTimeMetrics executionTimeMetrics;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtService jwtService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public JwtResponse registerTrainee(@RequestBody TraineeDTO traineeDTO){
        PassUsernameDTO passUsernameDTO = gymFacade.saveTrainee(traineeDTO);
        UserDetails userDetails = userDetailsService.loadUserByUsername(passUsernameDTO.getUsername());
        String token = jwtService.generateToken(userDetails);
        return new JwtResponse(token);
    }

    @GetMapping("/{username}")
    @Override
    public ResponseEntity<TraineeDTO> getTraineeProfileByUsername(@PathVariable String username) {
        requestMetrics.incrementRequestCount();
        Optional<TraineeDTO> trainee = gymFacade.findTraineeByUsername(username);
        return trainee.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{username}")
    @Override
    public ResponseEntity<TraineeDTO> updateTraineeProfile(@PathVariable String username, TraineeDTO trainee) {
        TraineeDTO updatedTrainee = gymFacade.updateTraineeProfile(username, trainee);
        return ResponseEntity.ok(updatedTrainee);
    }

    @DeleteMapping("/{username}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void deleteTrainee(@PathVariable String username) {
        executionTimeMetrics.recordExecutionTime(() -> gymFacade.deleteTrainee(username));
    }

    @GetMapping("/{username}/not-assigned-trainers")
    @Override
    public ResponseEntity<List<TrainerDTO>> getNotAssignedActiveTrainers(@PathVariable String username) {
        return ResponseEntity.ok(gymFacade.getTrainersNotAssignedToTrainee(username));
    }

    @GetMapping("{username}/trainings")
    @Override
    public ResponseEntity<List<TrainingDTO>> getTraineeTrainings(@PathVariable String username,
                                                                 LocalDate from,
                                                                 LocalDate to,
                                                                 String trainerUsername,
                                                                 String trainingName) {
        List<TrainingDTO> trainings = gymFacade.getTraineeTrainingsByCriteria(username, from, to, trainerUsername,
                trainingName);
        return ResponseEntity.ok(trainings);
    }

    @PatchMapping("/{username}/activate")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public void activateDeactivateTrainee(@PathVariable String username, boolean isActive) {
        gymFacade.activateDeactivateTraineeProfile(username, isActive);
    }

    @PutMapping("/{username}/trainers")
    @Override
    public ResponseEntity<TraineeDTO> updateTraineeTrainers(@PathVariable String username, Set<TrainerDTO> trainers) {
        TraineeDTO updatedTraineeDTO = gymFacade.updateTraineeTrainers(username, trainers);
        return ResponseEntity.ok(updatedTraineeDTO);
    }
}
