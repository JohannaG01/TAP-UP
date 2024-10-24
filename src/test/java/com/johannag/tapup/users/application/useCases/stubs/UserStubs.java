package com.johannag.tapup.users.application.useCases.stubs;

import com.johannag.tapup.users.application.dtos.AddUserFundsDTO;
import com.johannag.tapup.users.application.dtos.CreateUserDTO;
import com.johannag.tapup.users.application.dtos.LogInUserDTO;
import com.johannag.tapup.users.domain.dtos.AddUserFundsToEntityDTO;
import com.johannag.tapup.users.domain.dtos.CreateUserEntityDTO;
import com.johannag.tapup.users.domain.models.UserModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

public class UserStubs {

    public static CreateUserDTO createUserDTO() {
        return CreateUserDTO.builder()
                .email("example@gmail.com")
                .name("John")
                .lastName("Rodriguez")
                .password("Password123$")
                .build();
    }

    public static CreateUserEntityDTO createUserEntityDTO() {
        return CreateUserEntityDTO.builder()
                .uuid(UUID.randomUUID())
                .email("example@gmail.com")
                .name("John")
                .lastName("Rodriguez")
                .isAdmin(false)
                .balance(BigDecimal.ZERO)
                .hashedPassword("hashed_password")
                .build();
    }

    public static UserModel userModel() {
        return UserModel.builder()
                .uuid(UUID.randomUUID())
                .bets(new ArrayList<>())
                .email("example@gmail.com")
                .name("John")
                .lastName("Rodriguez")
                .isAdmin(false)
                .balance(BigDecimal.ZERO)
                .hashedPassword("hashed_password")
                .build();
    }

    public static LogInUserDTO logInUserDTO() {
        return LogInUserDTO.builder()
                .email("example@gmail.com")
                .password("Password123$")
                .build();
    }

    public static AddUserFundsDTO addUserFundsDTO() {
        return AddUserFundsDTO
                .builder()
                .userUuid(UUID.randomUUID())
                .amount(BigDecimal.ZERO)
                .build();
    }

    public static AddUserFundsToEntityDTO addUserFundsToEntityDTO() {
        return AddUserFundsToEntityDTO
                .builder()
                .userUuid(UUID.randomUUID())
                .amount(BigDecimal.ZERO)
                .build();
    }
}
