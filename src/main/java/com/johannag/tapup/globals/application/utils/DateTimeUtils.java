package com.johannag.tapup.globals.application.utils;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.TimeZone;

/**
 * Utility class for date and time operations in the Buenos Aires time zone.
 */
public class DateTimeUtils {

    /**
     * Returns the ZoneId for Buenos Aires.
     *
     * @return the ZoneId representing America/Buenos_Aires
     */
    public static ZoneId buenosAiresZoneId() {
        return ZoneId.of("America/Buenos_Aires");
    }

    /**
     * Returns the current date and time as an OffsetDateTime in the Buenos Aires time zone.
     *
     * @return the current date and time as an OffsetDateTime
     */
    public static OffsetDateTime nowAsOffsetDateTime() {
        return OffsetDateTime.now(buenosAiresZoneId());
    }


    /**
     * Returns the current date and time as a Date object, using the current
     * date and time in the Buenos Aires time zone.
     *
     * @return the current date and time as a Date
     */
    public static Date nowAsDate() {
        return Date.from(nowAsOffsetDateTime().toInstant());
    }

    /**
     * Converts a Date object to a LocalDateTime in the Buenos Aires time zone.
     *
     * @param date the Date object to convert
     * @return the converted LocalDateTime in the Buenos Aires time zone
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        return date.toInstant()
                .atZone(buenosAiresZoneId())
                .toLocalDateTime();
    }

    public static TimeZone buenosAiresTimeZone() {
        return TimeZone.getTimeZone(buenosAiresZoneId());
    }
}
