package com.johannag.tapup.users.presentation.dtos.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder(builderClassName = "Builder")
@AllArgsConstructor(onConstructor = @__(@JsonCreator))
public class AddUserFundsRequestDTO {
    @NotNull
    @Positive
    BigDecimal amount;
}
