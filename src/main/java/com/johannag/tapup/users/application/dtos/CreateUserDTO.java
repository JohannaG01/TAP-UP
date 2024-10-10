package com.johannag.tapup.users.application.dtos;


import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@Builder(builderClassName = "Builder")
public class CreateUserDTO {
    String email;
    String name;
    String lastName;
    String password;
}
