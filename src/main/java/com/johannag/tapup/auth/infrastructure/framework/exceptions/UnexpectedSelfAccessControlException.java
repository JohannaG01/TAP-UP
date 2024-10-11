package com.johannag.tapup.auth.infrastructure.framework.exceptions;

public class UnexpectedSelfAccessControlException extends RuntimeException {

    public UnexpectedSelfAccessControlException(String message) {
        super(message);
    }
}
