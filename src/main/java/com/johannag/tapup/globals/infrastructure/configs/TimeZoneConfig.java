package com.johannag.tapup.globals.infrastructure.configs;

import com.johannag.tapup.globals.application.utils.DateTimeUtils;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.util.TimeZone;

@Configuration
public class TimeZoneConfig {

    @PostConstruct
    public void init() {
        TimeZone.setDefault(DateTimeUtils.buenosAiresTimeZone());
    }
}
