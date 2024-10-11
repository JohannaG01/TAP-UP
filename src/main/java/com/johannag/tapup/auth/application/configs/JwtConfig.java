package com.johannag.tapup.auth.application.configs;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class JwtConfig {

    @Value("${jwt.expiration-in-seconds}")
    private Integer expiresInSeconds;

    @Value("${jwt.secret-word}")
    private String secretWord;
}
