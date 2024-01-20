package com.ocsoares.advancedcrudspringboot.infrastructure.controllers.user.services.auth;

import com.ocsoares.advancedcrudspringboot.application.gateways.security.IAuthServiceGateway;
import com.ocsoares.advancedcrudspringboot.application.gateways.security.ITokenServiceGateway;
import com.ocsoares.advancedcrudspringboot.application.gateways.user.IUserRepositoryGateway;
import com.ocsoares.advancedcrudspringboot.domain.entity.UserDomainEntity;
import com.ocsoares.advancedcrudspringboot.domain.exceptions.security.ErrorCreatingJWTException;
import com.ocsoares.advancedcrudspringboot.domain.exceptions.user.InvalidUserByEmailException;
import com.ocsoares.advancedcrudspringboot.infrastructure.mappers.UserPersistenceEntityMapper;
import com.ocsoares.advancedcrudspringboot.infrastructure.persistence.entity.UserPersistenceEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService, IAuthServiceGateway {
    private final IUserRepositoryGateway userRepositoryGateway;
    private final UserPersistenceEntityMapper userPersistenceEntityMapper;
    private final ITokenServiceGateway tokenServiceGateway;
    @Autowired
    private ApplicationContext applicationContext;
    private AuthenticationManager authenticationManager;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserDomainEntity> userFoundByEmail = this.userRepositoryGateway.findUserByEmail(email);

        if (userFoundByEmail.isEmpty()) {
            throw new UsernameNotFoundException("Invalid credentials");
        }

        UserDomainEntity userDomainEntity = userFoundByEmail.get();

        return this.userPersistenceEntityMapper.toPersistence(userDomainEntity);
    }

    @Override
    public String login(String email, String password) throws ErrorCreatingJWTException, InvalidUserByEmailException {
        authenticationManager = applicationContext.getBean(AuthenticationManager.class);

        var authenticationByUsernameAndPassword = new UsernamePasswordAuthenticationToken(email, password);
        Authentication authenticatedUser = this.authenticationManager.authenticate(authenticationByUsernameAndPassword);

        UserPersistenceEntity persistenceAuthenticatedUser = (UserPersistenceEntity) authenticatedUser.getPrincipal();
        UserDomainEntity domainAuthenticatedUser = this.userPersistenceEntityMapper.toDomain(
                persistenceAuthenticatedUser);

        // Esse trecho NÃO vai ser Executado se o Login for INVÁLIDO !!!!
        String userId = this.userRepositoryGateway.getUserIdByEmail(email)
                .orElseThrow(InvalidUserByEmailException::new);

        return this.tokenServiceGateway.generateToken(userId, domainAuthenticatedUser);
    }
}
