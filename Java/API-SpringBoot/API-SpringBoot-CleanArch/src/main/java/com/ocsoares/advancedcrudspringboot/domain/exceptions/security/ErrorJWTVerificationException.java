package com.ocsoares.advancedcrudspringboot.domain.exceptions.security;

public class ErrorJWTVerificationException extends Exception {
    public ErrorJWTVerificationException() {
        super("An error occurred when checking JWT");
    }
}
