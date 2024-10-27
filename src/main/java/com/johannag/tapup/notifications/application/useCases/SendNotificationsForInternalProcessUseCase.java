package com.johannag.tapup.notifications.application.useCases;

import com.johannag.tapup.globals.infrastructure.utils.Logger;
import com.johannag.tapup.notifications.application.dtos.CreateNotificationDTO;
import com.johannag.tapup.notifications.application.dtos.SendNotificationsInternalProcessDTO;
import com.johannag.tapup.notifications.domain.models.NotificationModelType;
import com.johannag.tapup.users.application.services.UserService;
import com.johannag.tapup.users.domain.models.UserModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SendNotificationsForInternalProcessUseCase {

    private static final Logger logger = Logger.getLogger(FindNotificationsForUserUseCase.class);
    private final UserService userService;
    private final CreateNotificationsUseCase createNotificationsUseCase;

    public void execute(SendNotificationsInternalProcessDTO dto) {
        logger.info("Starting sendNotificationForInternalProcess process for all admin users");

        List<UserModel> admins = userService.findAllAdmins();
        List<CreateNotificationDTO> dtos = buildAndLogNotificationMessage(admins, dto);
        createNotificationsUseCase.execute(dtos);

        logger.info("Finished sendNotificationForInternalProcess process for all admin users");
    }

    private List<CreateNotificationDTO> buildAndLogNotificationMessage(List<UserModel> admins,
                                                                       SendNotificationsInternalProcessDTO dto) {
        String message;
        NotificationModelType notificationType;

        if (dto.isFailed()) {
            message = dto.getFailureMessage();
            notificationType = NotificationModelType.ERROR;
        } else {
            message = dto.getSuccessfulMessage();
            notificationType = NotificationModelType.SUCCESS;
        }

        logger.info("Internal Process Results: {}", message);

        return admins.stream()
                .map(admin -> new CreateNotificationDTO(admin.getUuid(), message, notificationType))
                .toList();
    }

}
