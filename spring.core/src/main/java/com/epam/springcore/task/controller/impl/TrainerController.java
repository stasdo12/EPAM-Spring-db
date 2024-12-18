package com.epam.springcore.task.controller.impl;

import com.epam.springcore.task.controller.ITrainerController;
import com.epam.springcore.task.dto.JwtResponse;
import com.epam.springcore.task.dto.PassUsernameDTO;
import com.epam.springcore.task.dto.TrainerDTO;
import com.epam.springcore.task.dto.TrainingDTO;
import com.epam.springcore.task.facade.GymFacade;
import com.epam.springcore.task.service.impl.JwtService;
import com.epam.springcore.task.service.impl.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/trainers")
public class TrainerController implements ITrainerController {

    private final GymFacade gymFacade;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtService jwtTokenUtils;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public JwtResponse registerTrainer(@RequestBody TrainerDTO trainerDTO){
        PassUsernameDTO passUsernameDTO = gymFacade.saveTrainer(trainerDTO);
        UserDetails userDetails = userDetailsService.loadUserByUsername(passUsernameDTO.getUsername());
        String token = jwtTokenUtils.generateToken(userDetails);
        return new JwtResponse(token);

    }

    @GetMapping("/{username}")
    @Override
    public TrainerDTO getTrainerProfileByUsername(@PathVariable String username) {
        return gymFacade.findTrainerByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trainer not found"));
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
        gymFacade.activateDeactivateTrainer(username, isActive);
    }
}
