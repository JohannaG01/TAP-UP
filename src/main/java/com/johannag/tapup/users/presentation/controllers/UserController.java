package com.johannag.tapup.users.presentation.controllers;

import com.johannag.tapup.auth.presentation.annotations.SelfAccessControl;
import com.johannag.tapup.globals.presentation.errors.ErrorResponse;
import com.johannag.tapup.users.application.dtos.AddUserFundsDTO;
import com.johannag.tapup.users.application.dtos.CreateUserDTO;
import com.johannag.tapup.users.application.dtos.LogInUserDTO;
import com.johannag.tapup.users.application.exceptions.InvalidLoginCredentialsException;
import com.johannag.tapup.users.application.exceptions.UserAlreadyExistsException;
import com.johannag.tapup.users.application.exceptions.UserNotFoundException;
import com.johannag.tapup.users.application.mappers.UserApplicationMapper;
import com.johannag.tapup.users.application.services.UserService;
import com.johannag.tapup.users.domain.models.UserModel;
import com.johannag.tapup.users.domain.models.UserWithAuthTokenModel;
import com.johannag.tapup.users.presentation.dtos.requests.AddUserFundsRequestDTO;
import com.johannag.tapup.users.presentation.dtos.requests.CreateUserRequestDTO;
import com.johannag.tapup.users.presentation.dtos.requests.LogInUserRequestDTO;
import com.johannag.tapup.users.presentation.dtos.responses.UserResponseDTO;
import com.johannag.tapup.users.presentation.dtos.responses.UserWithAuthTokenResponseDTO;
import com.johannag.tapup.users.presentation.mappers.UserPresentationMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@ResponseBody
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "Users", description = "Operations related to users")
public class UserController {

    private final UserApplicationMapper userApplicationMapper;
    private final UserPresentationMapper userPresentationMapper;
    private final UserService userService;

    @Operation(summary = "Creates user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "409", description = "Conflict: User already exists", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))})
    })
    @PostMapping("/users")
    public ResponseEntity<UserResponseDTO> signIn(@Valid @RequestBody CreateUserRequestDTO createUserRequestDTO)
            throws UserAlreadyExistsException {

        CreateUserDTO createUserDTO = userApplicationMapper.toCreateDTO(createUserRequestDTO);
        UserModel user = userService.signIn(createUserDTO);
        UserResponseDTO response = userPresentationMapper.toResponseDTO(user);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "LogIn user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User logIn successfully"),
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
    @PostMapping("/login")
    public ResponseEntity<UserWithAuthTokenResponseDTO> logIn(@Valid @RequestBody LogInUserRequestDTO createUserRequestDTO)
            throws InvalidLoginCredentialsException {

        LogInUserDTO logInUserDTO = userApplicationMapper.toLogInDTO(createUserRequestDTO);
        UserWithAuthTokenModel userWithToken = userService.logIn(logInUserDTO);
        UserWithAuthTokenResponseDTO response = userPresentationMapper.toResponseDTO(userWithToken);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Add funds to user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Add funds to user successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = {
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
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))})
    })
    @SecurityRequirement(name = "bearerAuth")
    @SelfAccessControl
    @PreAuthorize("hasAnyAuthority({'REGULAR'})")
    @PostMapping("/users/{userUuid}/transactions")
    public ResponseEntity<UserResponseDTO> addFunds(@PathVariable UUID userUuid,
                                                    @Valid @RequestBody AddUserFundsRequestDTO createUserRequestDTO)
            throws UserNotFoundException {

        AddUserFundsDTO addUserFundsDTO = userApplicationMapper.toAddFundsDTO(userUuid, createUserRequestDTO);
        UserModel user = userService.addFunds(addUserFundsDTO);
        UserResponseDTO response = userPresentationMapper.toResponseDTO(user);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
