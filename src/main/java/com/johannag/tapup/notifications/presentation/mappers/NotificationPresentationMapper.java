package com.johannag.tapup.notifications.presentation.mappers;

import com.johannag.tapup.notifications.domain.models.NotificationModel;
import com.johannag.tapup.notifications.presentation.dtos.responses.NotificationResponseDTO;
import org.springframework.data.domain.Page;

public interface NotificationPresentationMapper {

    /**
     * Converts a {@link Page} of {@link NotificationModel} to a {@link Page} of {@link NotificationResponseDTO}.
     *
     * <p>This method transforms a pageable collection of {@link NotificationModel} objects
     * into a pageable collection of {@link NotificationResponseDTO} objects, facilitating
     * the presentation layer by providing the necessary data structure for responses.</p>
     *
     * @param models the {@link Page} of {@link NotificationModel} to be converted,
     *               must not be null.
     * @return a {@link Page} of {@link NotificationResponseDTO} containing the converted notification data.
     */
    Page<NotificationResponseDTO> toResponseDTO(Page<NotificationModel> models);
}
