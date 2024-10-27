package com.johannag.tapup.notifications.presentation.dtos.queries;

import com.johannag.tapup.globals.presentation.dtos.query.PageQuery;
import com.johannag.tapup.notifications.presentation.dtos.NotificationTypeDTO;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.AssertFalse;
import jakarta.validation.constraints.PastOrPresent;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.BindParam;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Value
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class FindNotificationsQuery extends PageQuery {

    Set<NotificationTypeDTO> types;

    @Nullable
    @PastOrPresent
    LocalDateTime sentFrom;

    @Nullable
    @PastOrPresent
    LocalDateTime sentTo;

    @Nullable
    Boolean isRead;

    public FindNotificationsQuery(Integer size,
                                  Integer page,
                                  @Nullable @BindParam("type") Set<NotificationTypeDTO> types,
                                  @Nullable LocalDateTime sentFrom,
                                  @Nullable LocalDateTime sentTo,
                                  @Nullable Boolean isRead) {
        super(size, page);
        this.types = types != null ? types : new HashSet<>();
        this.sentFrom = sentFrom;
        this.sentTo = sentTo;
        this.isRead = isRead;
    }


    @Hidden
    @AssertFalse(message = "sentAfter must not be after sentBefore")
    public boolean isSentAtRangeInvalid() {
        if (sentFrom == null || sentTo == null) {
            return false;
        }
        return sentFrom.isAfter(sentTo);
    }
}
