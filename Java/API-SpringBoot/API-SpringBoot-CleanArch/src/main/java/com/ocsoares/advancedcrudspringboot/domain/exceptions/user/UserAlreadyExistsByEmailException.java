package com.ocsoares.advancedcrudspringboot.domain.exceptions.user;

public class UserAlreadyExistsByEmailException extends Exception {
    public static final String EXCEPTION_MESSAGE = "There is already a user registered with this email !";

    public UserAlreadyExistsByEmailException() {
        super(UserAlreadyExistsByEmailException.EXCEPTION_MESSAGE);
    }
}
