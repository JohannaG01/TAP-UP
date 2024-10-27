package com.johannag.tapup.horses.application.configs;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class HorseConfig {

    @Value("${horse.rest.recovery-time-in-days}")
    private Long recoveryTimeInDays;
}
