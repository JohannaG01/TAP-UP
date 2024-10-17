package com.johannag.tapup.bets.presentation.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.johannag.tapup.auth.presentation.annotations.SelfAccessControl;
import com.johannag.tapup.bets.application.dtos.CreateBetDTO;
import com.johannag.tapup.bets.application.dtos.FindBetsDTO;
import com.johannag.tapup.bets.application.exceptions.InsufficientBalanceException;
import com.johannag.tapup.bets.application.mappers.BetApplicationMapper;
import com.johannag.tapup.bets.application.services.BetService;
import com.johannag.tapup.bets.domain.models.BetModel;
import com.johannag.tapup.bets.presentation.dtos.queries.FindBetsQuery;
import com.johannag.tapup.bets.presentation.dtos.requests.CreateBetRequestDTO;
import com.johannag.tapup.bets.presentation.dtos.responses.BetResponseDTO;
import com.johannag.tapup.bets.presentation.mappers.BetPresentationMapper;
import com.johannag.tapup.bets.presentation.schemas.PageBetResponseDTO;
import com.johannag.tapup.globals.presentation.errors.ErrorResponse;
import com.johannag.tapup.horseRaces.application.exceptions.InvalidHorseRaceStateException;
import com.johannag.tapup.horseRaces.application.exceptions.ParticipantNotFoundException;
import com.johannag.tapup.horseRaces.presentation.dtos.responses.views.ParticipantView;
import com.johannag.tapup.users.application.exceptions.UserNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
public class BetController {

    private final BetApplicationMapper betApplicationMapper;
    private final BetPresentationMapper betPresentationMapper;
    private final BetService betService;

    @Operation(summary = "Creates bet for user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Bet created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized: Invalid credentials", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "403", description = "Forbidden: Not enough privileges", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Not Found: User Not Found", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "409", description = "Conflict", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))})
    })
    @SelfAccessControl
    @PreAuthorize("hasAnyAuthority({'REGULAR'})")
    @JsonView(ParticipantView.ParticipantWithRace.class)
    @PostMapping("/users/{userUuid}/bets")
    public ResponseEntity<BetResponseDTO> create(@PathVariable UUID userUuid,
                                                 @Valid @RequestBody CreateBetRequestDTO createBetRequestDTO)
            throws UserNotFoundException, ParticipantNotFoundException, InvalidHorseRaceStateException,
            InsufficientBalanceException {

        CreateBetDTO createBetDTO = betApplicationMapper.toCreateDTO(userUuid, createBetRequestDTO);
        BetModel betModel = betService.create(createBetDTO);
        BetResponseDTO response = betPresentationMapper.toResponseDTO(betModel);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Find all bets for user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bets found successfully", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = PageBetResponseDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized: Invalid credentials", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Not Found: User Not Found", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))})
    })
    @SelfAccessControl
    @PreAuthorize("hasAnyAuthority({'ADMIN','REGULAR'})")
    @JsonView(ParticipantView.ParticipantWithRace.class)
    @GetMapping("/users/{userUuid}/bets")
    public ResponseEntity<Page<BetResponseDTO>> findUserAll(@PathVariable UUID userUuid,
                                                            @Valid @ParameterObject @ModelAttribute FindBetsQuery findBetsQuery) {

        FindBetsDTO dto = betApplicationMapper.toFindDTO(userUuid, findBetsQuery);
        Page<BetModel> bets = betService.findUserAll(dto);
        Page<BetResponseDTO> response = betPresentationMapper.toResponseDTO(bets);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
