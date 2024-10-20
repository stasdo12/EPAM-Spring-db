package com.epam.springcore.task.controller.impl;

import com.epam.springcore.task.controller.ITrainerController;
import com.epam.springcore.task.dto.JwtResponse;
import com.epam.springcore.task.dto.PassUsernameDTO;
import com.epam.springcore.task.dto.TrainerDTO;
import com.epam.springcore.task.dto.TrainingDTO;
import com.epam.springcore.task.facade.GymFacade;
import com.epam.springcore.task.service.impl.UserDetailsServiceImpl;
import com.epam.springcore.task.utils.impl.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/trainers")
public class TrainerController implements ITrainerController {

    private final GymFacade gymFacade;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtTokenUtils jwtTokenUtils;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public ResponseEntity<JwtResponse> registerTrainer(@RequestBody TrainerDTO trainerDTO){
        PassUsernameDTO passUsernameDTO = gymFacade.saveTrainer(trainerDTO);
        UserDetails userDetails = userDetailsService.loadUserByUsername(passUsernameDTO.getUsername());
        String token = jwtTokenUtils.generateToken(userDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(new JwtResponse(token));

    }

    @GetMapping("/{username}")
    @Override
    public ResponseEntity<TrainerDTO> getTrainerProfileByUsername(@PathVariable String username) {
        Optional<TrainerDTO> trainerDTOOptional = gymFacade.findTrainerByUsername(username);
        return trainerDTOOptional
                .map(trainerDTO -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(trainerDTO))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{username}")
    @Override
    public ResponseEntity<TrainerDTO> updateTrainerProfile(@PathVariable String username, TrainerDTO updatedTrainerDTO) {
        TrainerDTO updatedTrainer = gymFacade.updateTrainerProfile(username, updatedTrainerDTO);
        return ResponseEntity.ok(updatedTrainer);
    }

    @GetMapping("/{username}/trainings")
    @Override
    public ResponseEntity<List<TrainingDTO>> getTrainerTrainings(@PathVariable String username,
                                                                 LocalDate from,
                                                                 LocalDate to,
                                                                 String traineeUsername,
                                                                 String trainingName) {
        List<TrainingDTO> trainings = gymFacade.getTrainerTrainingsByCriteria(username, from, to, traineeUsername,
                trainingName);
        return ResponseEntity.ok(trainings);

    }

    @PatchMapping("/{username}/activate")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public void activateDeactivateTrainee(@PathVariable String username, @RequestParam("isActive") boolean isActive) {
        System.out.println("Activating/Deactivating Trainer: " + username + ", isActive: " + isActive);
        gymFacade.activateDeactivateTrainer(username, isActive);
    }
}
