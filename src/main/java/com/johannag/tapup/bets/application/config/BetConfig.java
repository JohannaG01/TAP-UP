package com.johannag.tapup.bets.application.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class BetConfig {

    @Value("${bet.min-odds}")
    private Double minOdds;
}
