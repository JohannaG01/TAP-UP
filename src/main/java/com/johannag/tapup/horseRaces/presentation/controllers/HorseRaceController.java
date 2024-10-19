package com.johannag.tapup.horseRaces.presentation.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.johannag.tapup.globals.presentation.errors.ErrorResponse;
import com.johannag.tapup.horseRaces.application.dtos.CreateHorseRaceDTO;
import com.johannag.tapup.horseRaces.application.dtos.FindHorseRacesDTO;
import com.johannag.tapup.horseRaces.application.dtos.UpdateHorseRaceDTO;
import com.johannag.tapup.horseRaces.application.exceptions.HorseRaceNotFoundException;
import com.johannag.tapup.horseRaces.application.exceptions.InvalidHorseRaceStateException;
import com.johannag.tapup.horseRaces.application.mappers.HorseRaceApplicationMapper;
import com.johannag.tapup.horseRaces.application.services.HorseRaceService;
import com.johannag.tapup.horseRaces.domain.models.HorseRaceModel;
import com.johannag.tapup.horseRaces.presentation.dtos.queries.FindHorseRacesQuery;
import com.johannag.tapup.horseRaces.presentation.dtos.requests.CreateHorseRaceRequestDTO;
import com.johannag.tapup.horseRaces.presentation.dtos.requests.UpdateHorseRaceRequestDTO;
import com.johannag.tapup.horseRaces.presentation.dtos.responses.HorseRaceResponseDTO;
import com.johannag.tapup.horseRaces.presentation.dtos.responses.views.ParticipantView;
import com.johannag.tapup.horseRaces.presentation.mappers.HorseRacePresentationMapper;
import com.johannag.tapup.horseRaces.presentation.schemas.PageHorseRaceResponseDTO;
import com.johannag.tapup.horses.application.exceptions.HorseNotAvailableException;
import com.johannag.tapup.horses.application.exceptions.HorseNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
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
@Tag(name = "Horse Races", description = "Operations related to horse racing")
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
    @JsonView(ParticipantView.ParticipantWithoutRace.class)
    @PostMapping("/horse-races")
    public ResponseEntity<HorseRaceResponseDTO> create(@Valid @RequestBody CreateHorseRaceRequestDTO createHorseRaceRequestDTO) throws
            HorseNotAvailableException, HorseNotFoundException {

        CreateHorseRaceDTO createHorseRaceDTO = horseRaceApplicationMapper.toCreateDTO(createHorseRaceRequestDTO);
        HorseRaceModel horseRace = horseRaceService.create(createHorseRaceDTO);
        HorseRaceResponseDTO response = horseRacePresentationMapper.toResponseDTO(horseRace);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Updates horse race")
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
            @ApiResponse(responseCode = "404", description = "Not Found: Horse Race Not Found", content = {
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
    @JsonView(ParticipantView.ParticipantWithoutRace.class)
    @PatchMapping("/horse-races/{horseRaceUuid}")
    public ResponseEntity<HorseRaceResponseDTO> update(@PathVariable UUID horseRaceUuid,
                                                       @Valid @RequestBody UpdateHorseRaceRequestDTO updateHorseRaceRequestDTO)
            throws HorseRaceNotFoundException, InvalidHorseRaceStateException, HorseNotAvailableException {

        UpdateHorseRaceDTO updateHorseRaceDTo = horseRaceApplicationMapper
                .toUpdateDTO(horseRaceUuid, updateHorseRaceRequestDTO);
        HorseRaceModel horseRace = horseRaceService.update(updateHorseRaceDTo);
        HorseRaceResponseDTO response = horseRacePresentationMapper.toResponseDTO(horseRace);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Find horse race by uuid")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Horse Race found successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized: Invalid credentials", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "403", description = "Forbidden: Not enough privileges", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Not Found: Horse Race Not Found", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))})
    })
    @PreAuthorize("hasAnyAuthority({'ADMIN','REGULAR'})")
    @JsonView(ParticipantView.ParticipantWithoutRace.class)
    @GetMapping("/horse-races/{horseRaceUuid}")
    public ResponseEntity<HorseRaceResponseDTO> find(@PathVariable UUID horseRaceUuid) throws HorseRaceNotFoundException {
        HorseRaceModel horseRace = horseRaceService.findOneByUuid(horseRaceUuid);
        HorseRaceResponseDTO response = horseRacePresentationMapper.toResponseDTO(horseRace);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Find all horse races")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Horse Races found successfully", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = PageHorseRaceResponseDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized: Invalid credentials", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "403", description = "Forbidden: Not enough privileges", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))})
    })
    @PreAuthorize("hasAnyAuthority({'ADMIN','REGULAR'})")
    @JsonView(ParticipantView.ParticipantWithoutRace.class)
    @GetMapping("/horse-races")
    public ResponseEntity<Page<HorseRaceResponseDTO>> findAll(@Valid @ParameterObject @ModelAttribute FindHorseRacesQuery findHorsesRaceQuery) {
        FindHorseRacesDTO dto = horseRaceApplicationMapper.toFindDTO(findHorsesRaceQuery);
        Page<HorseRaceModel> horseRaces = horseRaceService.findAll(dto);
        Page<HorseRaceResponseDTO> response = horseRacePresentationMapper.toResponseDTO(horseRaces);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
