package com.johannag.tapup.users.application.dtos;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(builderClassName = "Builder")
public class LogInUserDTO {
    String email;
    String password;
}
