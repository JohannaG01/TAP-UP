package com.johannag.tapup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(exclude = UserDetailsServiceAutoConfiguration.class)
public class TapupApplication {
    public static void main(String[] args) {
        SpringApplication.run(TapupApplication.class, args);
    }
}