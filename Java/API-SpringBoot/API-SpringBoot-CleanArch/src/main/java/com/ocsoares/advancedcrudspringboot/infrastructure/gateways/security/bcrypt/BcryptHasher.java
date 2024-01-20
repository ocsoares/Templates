package com.ocsoares.advancedcrudspringboot.infrastructure.gateways.security.bcrypt;

import com.ocsoares.advancedcrudspringboot.application.gateways.security.PasswordHasherGateway;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BcryptHasher extends PasswordHasherGateway {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public BcryptHasher(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public String hash(String plainPassword) {
        return this.bCryptPasswordEncoder.encode(plainPassword);
    }

    @Override
    public String hash(String plainPassword, Integer salt) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Hashing with manual salt is not supported !");
    }

    @Override
    public Boolean compare(String plainPassword, String hashedPassword) {
        return this.bCryptPasswordEncoder.matches(plainPassword, hashedPassword);
    }
}