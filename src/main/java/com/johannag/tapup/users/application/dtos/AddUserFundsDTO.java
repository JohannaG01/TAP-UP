package com.johannag.tapup.users.application.dtos;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.UUID;

@Value
@Builder(builderClassName = "Builder")
public class AddUserFundsDTO {
    UUID userUuid;
    BigDecimal amount;
}
