package com.johannag.tapup.notifications.application.useCases;

import com.johannag.tapup.notifications.application.dtos.UpdateNotificationReadStatusDTO;
import com.johannag.tapup.notifications.application.mappers.NotificationApplicationMapper;
import com.johannag.tapup.notifications.application.useCases.stubs.NotificationStubs;
import com.johannag.tapup.notifications.domain.dtos.UpdateNotificationReadStatusForEntityDTO;
import com.johannag.tapup.notifications.domain.models.NotificationModel;
import com.johannag.tapup.notifications.infrastructure.adapters.NotificationRepository;
import com.johannag.tapup.users.application.exceptions.UserNotFoundException;
import com.johannag.tapup.users.application.services.UserService;
import com.johannag.tapup.users.application.useCases.stubs.UserStubs;
import com.johannag.tapup.users.domain.models.UserModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UpdateNotificationReadStatusForUserUseCaseTests {

    private final UserModel user = UserStubs.userModel();
    private final NotificationModel notification = NotificationStubs.notificationModel();
    private final UpdateNotificationReadStatusDTO updateDTO = NotificationStubs.updateNotificationReadStatusDTO();
    private final UpdateNotificationReadStatusForEntityDTO updateEntitiesDTO =
            NotificationStubs.updateNotificationReadStatusForEntityDTO();
    @Mock
    private NotificationApplicationMapper notificationApplicationMapper;
    @Mock
    private NotificationRepository notificationRepository;
    @Mock
    private UserService userService;
    @Spy
    @InjectMocks
    private UpdateNotificationReadStatusForUserUseCase updateNotificationReadStatusForUserUseCase;

    @Test
    public void userNotFound() {
        UserNotFoundException exception = new UserNotFoundException(UUID.randomUUID().toString());

        doThrow(exception)
                .when(userService)
                .findOneByUuid(any());

        assertThrows(UserNotFoundException.class, () -> updateNotificationReadStatusForUserUseCase.execute(updateDTO));
    }

    @Test
    public void notificationReadStatusUpdatedSuccessfully() {

        doReturn(user)
                .when(userService)
                .findOneByUuid(any());

        doReturn(updateEntitiesDTO)
                .when(notificationApplicationMapper)
                .toUpdateEntityDTO(any());

        doReturn(notification)
                .when(notificationRepository)
                .updateReadStatus(any());

        assertEquals(notification, updateNotificationReadStatusForUserUseCase.execute(updateDTO));
    }
}
