package com.ocsoares.advancedcrudspringboot.application.gateways.security;

import com.ocsoares.advancedcrudspringboot.domain.entity.UserDomainEntity;
import com.ocsoares.advancedcrudspringboot.domain.exceptions.security.ErrorCreatingJWTException;
import com.ocsoares.advancedcrudspringboot.domain.exceptions.security.ErrorJWTVerificationException;

import java.time.Instant;

public interface ITokenServiceGateway {
    String generateToken(String id, UserDomainEntity userDomainEntity) throws ErrorCreatingJWTException;
    String validateToken(String token) throws ErrorJWTVerificationException;
    Instant getExpirationDate();
}
