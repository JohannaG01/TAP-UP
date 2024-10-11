package com.johannag.tapup.globals.infrastructure.utils;

import com.johannag.tapup.auth.infrastructure.utils.SecurityContextUtils;
import com.johannag.tapup.users.infrastructure.framework.context.UserOnContext;
import org.apache.logging.log4j.LogManager;

public class Logger {
    private final org.apache.logging.log4j.Logger logger;

    private Logger(Class<?> clazz) {
        this.logger = LogManager.getLogger(clazz);
    }

    public static Logger getLogger(Class<?> clazz) {
        return new Logger(clazz);
    }

    public void debug(String message) {
        logger.debug(fullMessage(message));
    }

    public void debug(String message, Object... params) {
        logger.debug(fullMessage(message), params);
    }

    public void info(String message) {
        logger.info(fullMessage(message));
    }

    public void info(String message, Object... params) {
        logger.info(fullMessage(message), params);
    }

    public void warn(String message) {
        logger.warn(fullMessage(message));
    }

    public void warn(String message, Object... params) {
        logger.warn(fullMessage(message), params);
    }

    public void error(String message) {
        logger.error(fullMessage(message));
    }

    public void error(String message, Object... params) {
        logger.error(fullMessage(message), params);
    }

    public void error(String message, Throwable throwable) {
        logger.error(fullMessage(message), throwable);
    }

    public String fullMessage(String message) {
        return String.format("%s: %s", userContext(), message);
    }

    public String userContext() {
        return SecurityContextUtils.maybeUserOnContext()
                .map(UserOnContext::getContext)
                .orElse("anonymousUser");
    }
}
