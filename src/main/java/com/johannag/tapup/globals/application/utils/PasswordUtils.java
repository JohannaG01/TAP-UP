package com.johannag.tapup.globals.application.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtils {
    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static String hash(String password) {
        return passwordEncoder.encode(password);
    }

    public static boolean match(String password, String hashedPassword) {
        return passwordEncoder.matches(password, hashedPassword);
    }
}
