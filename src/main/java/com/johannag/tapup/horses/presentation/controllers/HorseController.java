package com.johannag.tapup.horses.presentation.controllers;

import com.johannag.tapup.horses.application.dtos.CreateHorseDTO;
import com.johannag.tapup.horses.application.exceptions.HorseAlreadyExistsException;
import com.johannag.tapup.horses.application.exceptions.HorseNotFoundException;
import com.johannag.tapup.horses.application.mappers.HorseApplicationMapper;
import com.johannag.tapup.horses.application.services.HorseService;
import com.johannag.tapup.horses.application.dtos.UpdateHorseDTO;
import com.johannag.tapup.horses.domain.models.HorseModel;
import com.johannag.tapup.horses.presentation.dtos.requests.CreateHorseRequestDTO;
import com.johannag.tapup.horses.presentation.dtos.requests.UpdateHorseRequestDTO;
import com.johannag.tapup.horses.presentation.dtos.responses.HorseResponseDTO;
import com.johannag.tapup.horses.presentation.mappers.HorsePresentationMapper;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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
            throws HorseAlreadyExistsException {

        CreateHorseDTO createHorseDTO = horseApplicationMapper.toCreateHorseDTO(createHorseRequestDTO);
        HorseModel horse = horseService.create(createHorseDTO);
        HorseResponseDTO response = horsePresentationMapper.toHorseResponseDTO(horse);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyAuthority({'ADMIN'})")
    @PatchMapping("/horses/{horseUuid}")
    public ResponseEntity<HorseResponseDTO> update(@PathVariable UUID horseUuid,
                                                   @Valid @RequestBody UpdateHorseRequestDTO updateHorseRequestDTO)
            throws HorseNotFoundException, HorseAlreadyExistsException {

        UpdateHorseDTO updateHorseDTO = horseApplicationMapper.toUpdateHorseDTO(horseUuid, updateHorseRequestDTO);
        HorseModel horse = horseService.update(updateHorseDTO);
        HorseResponseDTO response = horsePresentationMapper.toHorseResponseDTO(horse);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
