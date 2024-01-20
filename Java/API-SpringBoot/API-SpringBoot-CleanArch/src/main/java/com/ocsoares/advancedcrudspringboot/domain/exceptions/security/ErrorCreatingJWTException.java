package com.ocsoares.advancedcrudspringboot.domain.exceptions.security;

public class ErrorCreatingJWTException extends Exception {
    public static final String EXCEPTION_MESSAGE = "An error occurred while creating JWT";

    public ErrorCreatingJWTException() {
        super(ErrorCreatingJWTException.EXCEPTION_MESSAGE);
    }
}
