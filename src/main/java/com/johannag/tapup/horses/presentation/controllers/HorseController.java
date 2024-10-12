package com.johannag.tapup.horses.presentation.controllers;

import com.johannag.tapup.globals.presentation.errors.ErrorResponse;
import com.johannag.tapup.horses.application.dtos.CreateHorseDTO;
import com.johannag.tapup.horses.application.dtos.FindHorsesDTO;
import com.johannag.tapup.horses.application.dtos.UpdateHorseDTO;
import com.johannag.tapup.horses.application.exceptions.CannotTransitionHorseStateException;
import com.johannag.tapup.horses.application.exceptions.HorseAlreadyExistsException;
import com.johannag.tapup.horses.application.exceptions.HorseNotFoundException;
import com.johannag.tapup.horses.application.exceptions.InvalidHorseStateException;
import com.johannag.tapup.horses.application.mappers.HorseApplicationMapper;
import com.johannag.tapup.horses.application.services.HorseService;
import com.johannag.tapup.horses.domain.models.HorseModel;
import com.johannag.tapup.horses.presentation.dtos.HorseStateDTO;
import com.johannag.tapup.horses.presentation.dtos.requests.CreateHorseRequestDTO;
import com.johannag.tapup.horses.presentation.dtos.requests.UpdateHorseRequestDTO;
import com.johannag.tapup.horses.presentation.dtos.responses.HorseResponseDTO;
import com.johannag.tapup.horses.presentation.mappers.HorsePresentationMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Set;
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

    @Operation(summary = "Creates horse")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Horse created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized: Invalid credentials", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "403", description = "Forbidden: Not enough privileges", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "409", description = "Conflict: Horse already exists", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))})
    })
    @PreAuthorize("hasAnyAuthority({'ADMIN'})")
    @PostMapping("/horses")
    public ResponseEntity<HorseResponseDTO> create(@Valid @RequestBody CreateHorseRequestDTO createHorseRequestDTO)
            throws HorseAlreadyExistsException {

        CreateHorseDTO createHorseDTO = horseApplicationMapper.toCreateDTO(createHorseRequestDTO);
        HorseModel horse = horseService.create(createHorseDTO);
        HorseResponseDTO response = horsePresentationMapper.toResponseDTO(horse);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Updates horse")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Horse updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized: Invalid credentials", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "403", description = "Forbidden: Not enough privileges", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Not Found: Horse not found", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity: Invalid state", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))})
    })
    @PreAuthorize("hasAnyAuthority({'ADMIN'})")
    @PatchMapping("/horses/{horseUuid}")
    public ResponseEntity<HorseResponseDTO> update(@PathVariable UUID horseUuid,
                                                   @Valid @RequestBody UpdateHorseRequestDTO updateHorseRequestDTO)
            throws HorseNotFoundException, CannotTransitionHorseStateException, InvalidHorseStateException {

        UpdateHorseDTO updateHorseDTO = horseApplicationMapper.toUpdateDTO(horseUuid, updateHorseRequestDTO);
        HorseModel horse = horseService.update(updateHorseDTO);
        HorseResponseDTO response = horsePresentationMapper.toResponseDTO(horse);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Deletes horse")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Horse deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized: Invalid credentials", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "403", description = "Forbidden: Not enough privileges", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Not Found: Horse Not Found", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity: Invalid state", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))})
    })
    @PreAuthorize("hasAnyAuthority({'ADMIN'})")
    @DeleteMapping("/horses/{horseUuid}")
    public ResponseEntity<HorseResponseDTO> delete(@PathVariable UUID horseUuid)
            throws HorseNotFoundException, CannotTransitionHorseStateException {

        HorseModel horse = horseService.delete(horseUuid);
        HorseResponseDTO response = horsePresentationMapper.toResponseDTO(horse);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Find all horses")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Horses found successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized: Invalid credentials", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))})
    })
    @PreAuthorize("hasAnyAuthority({'ADMIN','REGULAR'})")
    @GetMapping("/horses")
    public ResponseEntity<Page<HorseResponseDTO>> findAll(@RequestParam(defaultValue = "10") int size,
                                                         @RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(name = "state", required = false) Set<HorseStateDTO> states) {

        FindHorsesDTO dto = FindHorsesDTO.builder()
                .size(size)
                .page(page)
                .states(states != null ? horseApplicationMapper.toModel(states) : Collections.emptyList())
                .build();

        Page<HorseModel> horses = horseService.findAll(dto);
        Page<HorseResponseDTO> response = horsePresentationMapper.toResponseDTO(horses);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}