package com.ocsoares.advancedcrudspringboot.domain.exceptions.user;

public class InvalidUserByEmailException extends Exception {
    public static final String EXCEPTION_MESSAGE = "The user with the provided email does not exist";

    public InvalidUserByEmailException() {
        super(InvalidUserByEmailException.EXCEPTION_MESSAGE);
    }
}
