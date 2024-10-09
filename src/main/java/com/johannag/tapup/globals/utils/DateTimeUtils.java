package com.johannag.tapup.globals.utils;

import java.time.OffsetDateTime;
import java.time.ZoneId;

public class DateTimeUtils {

    public static ZoneId buenosAiresZoneId() {
        return ZoneId.of("America/Buenos_Aires");
    }

    public static OffsetDateTime nowAsOffsetDateTime() {
        return OffsetDateTime.now(buenosAiresZoneId());
    }
}
