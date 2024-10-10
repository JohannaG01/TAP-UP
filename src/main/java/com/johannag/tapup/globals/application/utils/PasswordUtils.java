package com.johannag.tapup.globals.application.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Utility class for hashing and matching passwords.
 */
public class PasswordUtils {
    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * Hashes a plain text password using the configured password encoder.
     *
     * @param password the plain text password to be hashed
     * @return the hashed password
     */
    public static String hash(String password) {
        return passwordEncoder.encode(password);
    }

    /**
     * Checks if a plain text password matches a previously hashed password.
     *
     * @param password the plain text password to check
     * @param hashedPassword the hashed password to compare against
     * @return true if the passwords match, false otherwise
     */
    public static boolean match(String password, String hashedPassword) {
        return passwordEncoder.matches(password, hashedPassword);
    }
}
