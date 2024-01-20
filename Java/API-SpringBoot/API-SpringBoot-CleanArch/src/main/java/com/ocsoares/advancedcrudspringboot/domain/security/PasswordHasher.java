package com.ocsoares.advancedcrudspringboot.domain.security;

public abstract class PasswordHasher {
    public abstract String hash(String plainPassword);

    public abstract String hash(String plainPassword, Integer salt);

    public abstract Boolean compare(String plainPassword, String hashedPassword);
}
