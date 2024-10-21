package com.johannag.tapup.notifications.application.services;

import com.johannag.tapup.notifications.application.dtos.CreateNotificationDTO;
import com.johannag.tapup.notifications.application.useCases.CreateNotificationsUseCase;
import com.johannag.tapup.notifications.domain.models.NotificationModel;
import com.johannag.tapup.users.application.exceptions.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final CreateNotificationsUseCase createNotificationsUseCase;

    @Override
    public List<NotificationModel> createNotifications(List<CreateNotificationDTO> dtos) throws UserNotFoundException {
        return createNotificationsUseCase.execute(dtos);
    }
}
