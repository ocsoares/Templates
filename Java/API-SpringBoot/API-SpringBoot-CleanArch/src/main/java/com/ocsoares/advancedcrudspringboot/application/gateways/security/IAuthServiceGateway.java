package com.ocsoares.advancedcrudspringboot.application.gateways.security;

import com.ocsoares.advancedcrudspringboot.domain.exceptions.security.ErrorCreatingJWTException;
import com.ocsoares.advancedcrudspringboot.domain.exceptions.user.InvalidUserByEmailException;

public interface IAuthServiceGateway {
    String login(String email, String password) throws ErrorCreatingJWTException, InvalidUserByEmailException;
}
