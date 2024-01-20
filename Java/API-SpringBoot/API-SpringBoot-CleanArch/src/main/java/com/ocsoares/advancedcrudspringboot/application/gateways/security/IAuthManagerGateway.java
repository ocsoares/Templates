package com.ocsoares.advancedcrudspringboot.application.gateways.security;

public interface IAuthManagerGateway<T> {
    T authenticate(T authentication);
}
