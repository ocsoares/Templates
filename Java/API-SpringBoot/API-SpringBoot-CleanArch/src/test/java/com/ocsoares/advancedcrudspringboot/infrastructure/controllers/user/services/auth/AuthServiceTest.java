package com.ocsoares.advancedcrudspringboot.infrastructure.controllers.user.services.auth;

import com.ocsoares.advancedcrudspringboot.application.gateways.security.ITokenServiceGateway;
import com.ocsoares.advancedcrudspringboot.application.gateways.user.IUserRepositoryGateway;
import com.ocsoares.advancedcrudspringboot.domain.entity.UserDomainEntity;
import com.ocsoares.advancedcrudspringboot.domain.exceptions.security.ErrorCreatingJWTException;
import com.ocsoares.advancedcrudspringboot.domain.exceptions.user.InvalidUserByEmailException;
import com.ocsoares.advancedcrudspringboot.infrastructure.mappers.UserPersistenceEntityMapper;
import com.ocsoares.advancedcrudspringboot.infrastructure.persistence.entity.UserPersistenceEntity;
import com.ocsoares.advancedcrudspringboot.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

class AuthServiceTest {
    private final UserDomainEntity testUser = TestUtils.createUser();

    @Mock
    private IUserRepositoryGateway userRepositoryGateway;

    @Mock
    private UserPersistenceEntityMapper userPersistenceEntityMapper;

    @Mock
    private ITokenServiceGateway tokenServiceGateway;

    @Mock
    private ApplicationContext applicationContext;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("It SHOULD NOT be possible to load a user by email if it does not exists by email")
    void loadUserByUsername_Fail_WhenUserDoesNotExistsByEmail() {
        when(this.userRepositoryGateway.findUserByEmail(testUser.email())).thenReturn(Optional.empty());

        UsernameNotFoundException authException = Assertions.assertThrows(UsernameNotFoundException.class,
                () -> this.authService.loadUserByUsername(testUser.email())
        );

        Assertions.assertEquals("Invalid credentials", authException.getMessage());
    }

    @Test
    @DisplayName("It should be possible to load a user by email")
    void loadUserByUsername() {
        when(this.userRepositoryGateway.findUserByEmail(testUser.email())).thenReturn(Optional.of(testUser));

        UserPersistenceEntity testUserPersistence = TestUtils.toPersistence(testUser);
        when(this.userPersistenceEntityMapper.toPersistence(testUser)).thenReturn(testUserPersistence);

        UserDetails loggedUser = this.authService.loadUserByUsername(testUser.email());

        verify(this.userRepositoryGateway, times(1)).findUserByEmail(testUser.email());
        verify(this.userPersistenceEntityMapper, times(1)).toPersistence(testUser);
        Assertions.assertEquals(testUserPersistence, loggedUser);
    }

    @Test
    @DisplayName("It SHOULD NOT be possible to login user if it does not exists by email")
    void login_Fail_WhenUserDoesNotExistsByEmail() {
        // INJETA o "ApplicationContext" no TESTE !!!!
        ReflectionTestUtils.setField(authService, "applicationContext", applicationContext);

        // INJETA o "AuthenticationManager" no TESTE !!!
        when(applicationContext.getBean(AuthenticationManager.class)).thenReturn(authenticationManager);

        Authentication mockedAuthentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(
                mockedAuthentication);

        // Cria o "persistenceAuthenticatedUser" !!!
        var authenticatedUser = new UserPersistenceEntity(testUser.name(), testUser.email(), testUser.password());
        when(mockedAuthentication.getPrincipal()).thenReturn(authenticatedUser);

        when(this.userPersistenceEntityMapper.toDomain(authenticatedUser)).thenReturn(testUser);

        when(this.userRepositoryGateway.getUserIdByEmail(testUser.email())).thenReturn(Optional.empty());

        InvalidUserByEmailException authException = Assertions.assertThrows(InvalidUserByEmailException.class,
                () -> this.authService.login(testUser.email(), testUser.password())
        );

        verify(this.applicationContext, times(1)).getBean(AuthenticationManager.class);
        verify(this.authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(mockedAuthentication, times(1)).getPrincipal();
        verify(this.userPersistenceEntityMapper, times(1)).toDomain(authenticatedUser);
        verify(this.userRepositoryGateway, times(1)).getUserIdByEmail(testUser.email());
        Assertions.assertEquals(InvalidUserByEmailException.EXCEPTION_MESSAGE, authException.getMessage());
    }

    @Test
    @DisplayName("It SHOULD NOT be possible to login user if throw an exception when creating JWT")
    void login_Fail_WhenErrorCreatingJWT() throws ErrorCreatingJWTException {
        ReflectionTestUtils.setField(authService, "applicationContext", applicationContext);

        when(applicationContext.getBean(AuthenticationManager.class)).thenReturn(authenticationManager);

        Authentication mockedAuthentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(
                mockedAuthentication);

        var authenticatedUser = new UserPersistenceEntity(testUser.name(), testUser.email(), testUser.password());
        when(mockedAuthentication.getPrincipal()).thenReturn(authenticatedUser);

        when(this.userPersistenceEntityMapper.toDomain(authenticatedUser)).thenReturn(testUser);

        String userId = UUID.randomUUID().toString();

        when(this.userRepositoryGateway.getUserIdByEmail(testUser.email())).thenReturn(Optional.of(userId));
        when(this.tokenServiceGateway.generateToken(eq(userId), any(UserDomainEntity.class))).thenThrow(
                new ErrorCreatingJWTException());

        ErrorCreatingJWTException authException = Assertions.assertThrows(ErrorCreatingJWTException.class,
                () -> this.authService.login(testUser.email(), testUser.password())
        );

        verify(this.applicationContext, times(1)).getBean(AuthenticationManager.class);
        verify(this.authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(mockedAuthentication, times(1)).getPrincipal();
        verify(this.userPersistenceEntityMapper, times(1)).toDomain(authenticatedUser);
        verify(this.userRepositoryGateway, times(1)).getUserIdByEmail(testUser.email());
        verify(this.tokenServiceGateway, times(1)).generateToken(eq(userId), any(UserDomainEntity.class));
        Assertions.assertEquals(ErrorCreatingJWTException.EXCEPTION_MESSAGE, authException.getMessage());
    }

    @Test
    @DisplayName("It should be possible to login user")
    void login() throws InvalidUserByEmailException, ErrorCreatingJWTException {
        // INJETA o "ApplicationContext" no TESTE !!!!
        ReflectionTestUtils.setField(authService, "applicationContext", applicationContext);

        // INJETA o "AuthenticationManager" no TESTE !!!
        when(applicationContext.getBean(AuthenticationManager.class)).thenReturn(authenticationManager);

        Authentication mockedAuthentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(
                mockedAuthentication);

        // Cria o "persistenceAuthenticatedUser" !!!
        var authenticatedUser = new UserPersistenceEntity(testUser.name(), testUser.email(), testUser.password());
        when(mockedAuthentication.getPrincipal()).thenReturn(authenticatedUser);

        when(this.userPersistenceEntityMapper.toDomain(authenticatedUser)).thenReturn(testUser);

        String userId = UUID.randomUUID().toString();
        String token = "any-token-to-test";

        when(this.userRepositoryGateway.getUserIdByEmail(testUser.email())).thenReturn(Optional.of(userId));
        when(this.tokenServiceGateway.generateToken(eq(userId), any(UserDomainEntity.class))).thenReturn(token);

        String login = this.authService.login(testUser.email(), testUser.password());

        verify(this.applicationContext, times(1)).getBean(AuthenticationManager.class);
        verify(this.authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(mockedAuthentication, times(1)).getPrincipal();
        verify(this.userPersistenceEntityMapper, times(1)).toDomain(authenticatedUser);
        verify(this.userRepositoryGateway, times(1)).getUserIdByEmail(testUser.email());
        verify(this.tokenServiceGateway, times(1)).generateToken(eq(userId), any(UserDomainEntity.class));
        Assertions.assertEquals(token, login);
    }
}