package com.johannag.tapup.notifications.presentation.dtos.responses;

import com.johannag.tapup.notifications.presentation.dtos.NotificationTypeDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.UUID;

@Value
@Builder(builderClassName = "Builder")
public class NotificationResponseDTO {

    @NotNull
    UUID uuid;

    @NotNull
    NotificationTypeDTO type;

    @NotBlank
    String message;

    @NotNull
    LocalDateTime sentAt;

    @NotNull
    Boolean read;
}
