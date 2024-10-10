package com.johannag.tapup.utils;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateTimeUtils {

    public static ZoneId buenosAiresZoneId() {
        return ZoneId.of("America/Buenos_Aires");
    }

    public static OffsetDateTime nowAsOffsetDateTime() {
        return OffsetDateTime.now(buenosAiresZoneId());
    }

    public static Date nowAsDate() {
        return Date.from(nowAsOffsetDateTime().toInstant());
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        return date.toInstant()
                .atZone(buenosAiresZoneId())
                .toLocalDateTime();
    }
}
