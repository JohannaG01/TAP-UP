package com.johannag.tapup.horseRaces.application.utils;

import com.johannag.tapup.notifications.application.dtos.SendNotificationsInternalProcessDTO;
import com.johannag.tapup.notifications.application.services.NotificationService;
import org.mockito.ArgumentCaptor;

import static org.mockito.Mockito.verify;

public class TestUtils {

    public static SendNotificationsInternalProcessDTO obtainNotificationsToSendForInnerProcess(NotificationService notificationService) {
        ArgumentCaptor<SendNotificationsInternalProcessDTO> captor =
                ArgumentCaptor.forClass(SendNotificationsInternalProcessDTO.class);

        verify(notificationService).sendForInternalProcess(captor.capture());

        return captor.getValue();
    }
}
