package com.johannag.tapup.horses.presentation.controllers;

import com.johannag.tapup.horses.application.dtos.CreateHorseDTO;
import com.johannag.tapup.horses.application.mappers.HorseApplicationMapper;
import com.johannag.tapup.horses.application.services.HorseService;
import com.johannag.tapup.horses.domain.models.HorseModel;
import com.johannag.tapup.horses.presentation.dtos.requests.CreateHorseRequestDTO;
import com.johannag.tapup.horses.presentation.dtos.responses.HorseResponseDTO;
import com.johannag.tapup.horses.presentation.mappers.HorsePresentationMapper;
import com.johannag.tapup.users.application.dtos.CreateUserDTO;
import com.johannag.tapup.users.application.exceptions.UserAlreadyExistsException;
import com.johannag.tapup.users.domain.models.UserModel;
import com.johannag.tapup.users.presentation.dtos.requests.CreateUserRequestDTO;
import com.johannag.tapup.users.presentation.dtos.responses.UserResponseDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@ResponseBody
@RestController
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class HorseController {

    private final HorseApplicationMapper horseApplicationMapper;
    private final HorseService horseService;
    private final HorsePresentationMapper horsePresentationMapper;

    @PreAuthorize("hasAnyAuthority({'ADMIN'})")
    @PostMapping("/horses")
    public ResponseEntity<HorseResponseDTO> create(@Valid @RequestBody CreateHorseRequestDTO createHorseRequestDTO)
            throws UserAlreadyExistsException {

        CreateHorseDTO createHorseDTO = horseApplicationMapper.toCreateHorseDTO(createHorseRequestDTO);
        HorseModel horse = horseService.create(createHorseDTO);
        HorseResponseDTO response = horsePresentationMapper.toHorseResponseDTO(horse);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
