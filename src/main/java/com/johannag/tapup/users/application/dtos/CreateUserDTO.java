package com.johannag.tapup.users.application.dtos;


import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateUserDTO {
    String email;
    String name;
    String lastName;
    String password;
}
