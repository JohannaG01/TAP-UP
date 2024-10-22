package com.johannag.tapup.notifications.application.useCases;

import com.johannag.tapup.globals.infrastructure.utils.Logger;
import com.johannag.tapup.notifications.application.dtos.UpdateNotificationReadStatusDTO;
import com.johannag.tapup.notifications.application.exceptions.NotificationNotFoundException;
import com.johannag.tapup.notifications.application.mappers.NotificationApplicationMapper;
import com.johannag.tapup.notifications.domain.dtos.UpdateNotificationReadStatusForEntityDTO;
import com.johannag.tapup.notifications.domain.models.NotificationModel;
import com.johannag.tapup.notifications.infrastructure.adapters.NotificationRepository;
import com.johannag.tapup.users.application.exceptions.UserNotFoundException;
import com.johannag.tapup.users.application.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UpdateNotificationReadStatusForUserUseCase {

    private static final Logger logger = Logger.getLogger(UpdateNotificationReadStatusForUserUseCase.class);
    private final NotificationApplicationMapper notificationApplicationMapper;
    private final NotificationRepository notificationRepository;
    private final UserService userService;

    public NotificationModel execute(UpdateNotificationReadStatusDTO dto) throws UserNotFoundException,
            NotificationNotFoundException {
        logger.info("Starting updateNotificationReadStatus process for user {}", dto.getUserUuid());

        userService.findOneByUuid(dto.getUserUuid());
        var updateNotificationReadStatusDTO = notificationApplicationMapper.toUpdateEntityDTO(dto);
        NotificationModel notification = notificationRepository.updateReadStatus(updateNotificationReadStatusDTO);

        logger.info("Finished updateNotificationReadStatus process for user {}", dto.getUserUuid());
        return notification;
    }
}
