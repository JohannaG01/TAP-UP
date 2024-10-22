package com.johannag.tapup.notifications.application.useCases;

import com.johannag.tapup.globals.infrastructure.utils.Logger;
import com.johannag.tapup.notifications.application.dtos.FindNotificationsDTO;
import com.johannag.tapup.notifications.application.mappers.NotificationApplicationMapper;
import com.johannag.tapup.notifications.domain.dtos.FindNotificationEntitiesDTO;
import com.johannag.tapup.notifications.domain.models.NotificationModel;
import com.johannag.tapup.notifications.infrastructure.adapters.NotificationRepository;
import com.johannag.tapup.users.application.exceptions.UserNotFoundException;
import com.johannag.tapup.users.application.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FindNotificationsForUserUseCase {

    private static final Logger logger = Logger.getLogger(FindNotificationsForUserUseCase.class);
    private final NotificationApplicationMapper notificationApplicationMapper;
    private final NotificationRepository notificationRepository;
    private final UserService userService;

    public Page<NotificationModel> execute(FindNotificationsDTO dto) throws UserNotFoundException {
        logger.info("Starting findNotifications process for user with uuid {}", dto.getUserUuid());

        userService.findOneByUuid(dto.getUserUuid());
        FindNotificationEntitiesDTO findNotificationEntitiesDTO = notificationApplicationMapper.toFindEntitiesDTO(dto);
        Page<NotificationModel> notifications = notificationRepository.findAll(findNotificationEntitiesDTO);

        logger.info("Finished findNotifications process for user with uuid {}", dto.getUserUuid());
        return notifications;
    }
}
