package com.ocsoares.advancedcrudspringboot.main.config;

import com.ocsoares.advancedcrudspringboot.application.gateways.security.ITokenServiceGateway;
import com.ocsoares.advancedcrudspringboot.application.gateways.security.PasswordHasherGateway;
import com.ocsoares.advancedcrudspringboot.application.gateways.user.IUserRepositoryGateway;
import com.ocsoares.advancedcrudspringboot.infrastructure.gateways.security.bcrypt.BcryptHasher;
import com.ocsoares.advancedcrudspringboot.infrastructure.mappers.UserPersistenceEntityMapper;
import com.ocsoares.advancedcrudspringboot.infrastructure.security.SecurityFilter;
import com.ocsoares.advancedcrudspringboot.infrastructure.security.services.JwtService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SecurityConfig {
    @Bean
    public PasswordHasherGateway passwordHasherGateway(BCryptPasswordEncoder bCryptPasswordEncoder) {
        return new BcryptHasher(bCryptPasswordEncoder);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ITokenServiceGateway tokenServiceGateway(UserPersistenceEntityMapper userPersistenceEntityMapper) {
        return new JwtService(userPersistenceEntityMapper);
    }

    @Bean
    public SecurityFilter securityFilter(
            ITokenServiceGateway tokenServiceGateway, IUserRepositoryGateway userRepositoryGateway,
            UserPersistenceEntityMapper userPersistenceEntityMapper
    ) {
        return new SecurityFilter(tokenServiceGateway, userRepositoryGateway, userPersistenceEntityMapper);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws
            Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}