package com.johannag.tapup.users.presentation.controllers;

import com.johannag.tapup.users.application.dtos.CreateUserDTO;
import com.johannag.tapup.users.application.dtos.LogInUserDTO;
import com.johannag.tapup.users.application.exceptions.UserAlreadyExistsException;
import com.johannag.tapup.users.application.mappers.UserApplicationMapper;
import com.johannag.tapup.users.application.services.UserService;
import com.johannag.tapup.users.domain.models.UserModel;
import com.johannag.tapup.users.domain.models.UserWithTokenModel;
import com.johannag.tapup.users.presentation.dtos.requests.CreateUserRequestDTO;
import com.johannag.tapup.users.presentation.dtos.requests.LogInUserRequestDTO;
import com.johannag.tapup.users.presentation.dtos.responses.UserResponseDTO;
import com.johannag.tapup.users.presentation.dtos.responses.UserWithTokenResponseDTO;
import com.johannag.tapup.users.presentation.mappers.UserPresentationMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@ResponseBody
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {

    private final UserApplicationMapper userApplicationMapper;
    private final UserPresentationMapper userPresentationMapper;
    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity<UserResponseDTO> signIn(@Valid @RequestBody CreateUserRequestDTO createUserRequestDTO)
            throws UserAlreadyExistsException {

        CreateUserDTO createUserDTO = userApplicationMapper.toCreateUserDTO(createUserRequestDTO);
        UserModel user = userService.signIn(createUserDTO);
        UserResponseDTO response = userPresentationMapper.toUserResponseDTO(user);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserWithTokenResponseDTO> logIn(@Valid @RequestBody LogInUserRequestDTO createUserRequestDTO) {

        LogInUserDTO logInUserDTO = userApplicationMapper.toLogInUserDTO(createUserRequestDTO);
        UserWithTokenModel userWithToken = userService.logIn(logInUserDTO);
        UserWithTokenResponseDTO response = userPresentationMapper.toUserWithTokenResponseDTO(userWithToken);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
