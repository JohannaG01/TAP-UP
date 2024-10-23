package com.johannag.tapup.notifications.application.dtos;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(builderClassName = "Builder")
public class SendNotificationsInternalProcessDTO {
    boolean failed;
    String successfulMessage;
    String failureMessage;
}
