package com.johannag.tapup.notifications.presentation.schemas;

import com.johannag.tapup.globals.presentation.dtos.responses.PageResponse;
import com.johannag.tapup.notifications.presentation.dtos.responses.NotificationResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.List;

@Value
@EqualsAndHashCode(callSuper = true)
@Schema(description = "Pageable response containing notifications")
public class PageNotificationResponseDTO extends PageResponse {
    @Schema(description = "List of notification responses")
    List<NotificationResponseDTO> content;
}
