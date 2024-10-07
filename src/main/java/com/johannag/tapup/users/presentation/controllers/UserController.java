package com.johannag.tapup.users.presentation.controllers;

import com.johannag.tapup.users.application.dtos.CreateUserDTO;
import com.johannag.tapup.users.application.exceptions.UserAlreadyExistsException;
import com.johannag.tapup.users.application.mappers.UserApplicationMapper;
import com.johannag.tapup.users.application.services.UserService;
import com.johannag.tapup.users.domain.models.UserModel;
import com.johannag.tapup.users.presentation.dtos.CreateUserRequestDTO;
import com.johannag.tapup.users.presentation.dtos.UserResponseDTO;
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
    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity<UserResponseDTO> signIn(@Valid @RequestBody CreateUserRequestDTO createUserRequestDTO)
            throws UserAlreadyExistsException {

        CreateUserDTO createUserDTO = userApplicationMapper.toCreateUserDTO(createUserRequestDTO);
        UserModel user = userService.signIn(createUserDTO);
        UserResponseDTO userResponseDTO = userApplicationMapper.toUserResponseDTO(user);

        return new ResponseEntity<>(userResponseDTO, HttpStatus.CREATED);
    }
}
