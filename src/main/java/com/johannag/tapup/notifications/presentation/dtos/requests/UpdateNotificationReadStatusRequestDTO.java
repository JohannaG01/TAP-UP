package com.johannag.tapup.notifications.presentation.dtos.requests;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderClassName = "Builder")
public class UpdateNotificationReadStatusRequestDTO {

    @NotNull
    private Boolean read;
}
