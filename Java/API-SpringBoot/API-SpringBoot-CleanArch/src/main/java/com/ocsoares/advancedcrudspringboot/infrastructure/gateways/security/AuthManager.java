package com.ocsoares.advancedcrudspringboot.infrastructure.gateways.security;

import com.ocsoares.advancedcrudspringboot.application.gateways.security.IAuthManagerGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;

@RequiredArgsConstructor
public class AuthManager implements IAuthManagerGateway<Authentication> {
    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication authenticate(Authentication authentication) {
        return this.authenticationManager.authenticate(authentication);
    }
}
