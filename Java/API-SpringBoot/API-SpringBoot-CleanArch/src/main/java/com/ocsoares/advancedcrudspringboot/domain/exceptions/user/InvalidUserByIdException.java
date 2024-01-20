package com.ocsoares.advancedcrudspringboot.domain.exceptions.user;

public class InvalidUserByIdException extends Exception {
    public static final String EXCEPTION_MESSAGE = "The user with the provided ID does not exist";

    public InvalidUserByIdException() {
        super(InvalidUserByIdException.EXCEPTION_MESSAGE);
    }
}
