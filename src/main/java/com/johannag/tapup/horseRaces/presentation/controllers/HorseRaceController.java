package com.johannag.tapup.horseRaces.presentation.controllers;

import com.johannag.tapup.globals.presentation.errors.ErrorResponse;
import com.johannag.tapup.horseRaces.application.dtos.CreateHorseRaceDTO;
import com.johannag.tapup.horseRaces.application.mappers.HorseRaceApplicationMapper;
import com.johannag.tapup.horseRaces.application.services.HorseRaceService;
import com.johannag.tapup.horseRaces.domain.models.HorseRaceModel;
import com.johannag.tapup.horseRaces.presentation.dtos.requests.CreateHorseRaceRequestDTO;
import com.johannag.tapup.horseRaces.presentation.dtos.responses.HorseRaceResponseDTO;
import com.johannag.tapup.horseRaces.presentation.mappers.HorseRacePresentationMapper;
import com.johannag.tapup.horses.application.exceptions.HorseNotAvailableException;
import com.johannag.tapup.horses.application.exceptions.HorseNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
public class HorseRaceController {

    private final HorseRaceApplicationMapper horseRaceApplicationMapper;
    private final HorseRacePresentationMapper horseRacePresentationMapper;
    private final HorseRaceService horseRaceService;

    @Operation(summary = "Creates horse race")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Horse Race created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized: Invalid credentials", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "403", description = "Forbidden: Not enough privileges", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "409", description = "Conflict", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))})
    })
    @PreAuthorize("hasAnyAuthority({'ADMIN'})")
    @PostMapping("/horse-races")
    public ResponseEntity<HorseRaceResponseDTO> create(@Valid @RequestBody CreateHorseRaceRequestDTO createHorseRaceRequestDTO) throws
            HorseNotAvailableException, HorseNotFoundException {

        CreateHorseRaceDTO createHorseRaceDTO = horseRaceApplicationMapper.toCreateDTO(createHorseRaceRequestDTO);
        HorseRaceModel horseRace = horseRaceService.create(createHorseRaceDTO);
        HorseRaceResponseDTO response = horseRacePresentationMapper.toResponseDTO(horseRace);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
