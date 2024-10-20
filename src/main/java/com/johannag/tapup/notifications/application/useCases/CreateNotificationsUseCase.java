package com.johannag.tapup.notifications.application.useCases;

import com.johannag.tapup.globals.infrastructure.utils.Logger;
import com.johannag.tapup.notifications.application.dtos.CreateNotificationDTO;
import com.johannag.tapup.notifications.application.mappers.NotificationApplicationMapper;
import com.johannag.tapup.notifications.domain.dtos.CreateNotificationEntityDTO;
import com.johannag.tapup.notifications.domain.models.NotificationModel;
import com.johannag.tapup.notifications.infrastructure.adapters.NotificationRepository;
import com.johannag.tapup.users.application.exceptions.UserNotFoundException;
import com.johannag.tapup.users.application.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CreateNotificationsUseCase {

    private static final Logger logger = Logger.getLogger(CreateNotificationsUseCase.class);
    private final NotificationApplicationMapper notificationApplicationMapper;
    private final NotificationRepository notificationRepository;
    private final UserService userService;

    public List<NotificationModel> execute(List<CreateNotificationDTO> dtos) throws UserNotFoundException {
        logger.info("Starting createNotifications process for {} notifications", dtos.size());

        List<UUID> userUuids = obtainsUserUuids(dtos);
        userService.validateExistance(userUuids);
        List<CreateNotificationEntityDTO> createEntityDTOS = notificationApplicationMapper.toCreateDTO(dtos);
        List<NotificationModel> notifications = notificationRepository.create(createEntityDTOS);

        logger.info("Finished createNotifications process for {} notifications", dtos.size());
        return notifications;
    }

    private List<UUID> obtainsUserUuids(List<CreateNotificationDTO> dtos){
        return dtos.stream()
                .map(CreateNotificationDTO::getUserUuid)
                .toList();
    }
}
