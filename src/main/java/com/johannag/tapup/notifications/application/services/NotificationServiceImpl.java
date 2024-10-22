package com.johannag.tapup.notifications.application.services;

import com.johannag.tapup.notifications.application.dtos.CreateNotificationDTO;
import com.johannag.tapup.notifications.application.dtos.FindNotificationsDTO;
import com.johannag.tapup.notifications.application.dtos.UpdateNotificationReadStatusDTO;
import com.johannag.tapup.notifications.application.exceptions.NotificationNotFoundException;
import com.johannag.tapup.notifications.application.useCases.CreateNotificationsUseCase;
import com.johannag.tapup.notifications.application.useCases.FindNotificationsForUserUseCase;
import com.johannag.tapup.notifications.application.useCases.UpdateNotificationReadStatusForUserUseCase;
import com.johannag.tapup.notifications.domain.models.NotificationModel;
import com.johannag.tapup.users.application.exceptions.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final CreateNotificationsUseCase createNotificationsUseCase;
    private final FindNotificationsForUserUseCase findNotificationsForUserUseCase;
    private final UpdateNotificationReadStatusForUserUseCase updateNotificationReadStatusForUserUseCase;

    @Override
    public List<NotificationModel> createNotifications(List<CreateNotificationDTO> dtos) throws UserNotFoundException {
        return createNotificationsUseCase.execute(dtos);
    }

    @Override
    public Page<NotificationModel> findByUser(FindNotificationsDTO dto) throws UserNotFoundException {
        return findNotificationsForUserUseCase.execute(dto);
    }

    @Override
    public NotificationModel updateReadStatusByUser(UpdateNotificationReadStatusDTO dto) throws UserNotFoundException
            , NotificationNotFoundException {
        return updateNotificationReadStatusForUserUseCase.execute(dto);
    }
}
