package com.ocsoares.advancedcrudspringboot.application.usecases.user;

import com.ocsoares.advancedcrudspringboot.application.gateways.security.PasswordHasherGateway;
import com.ocsoares.advancedcrudspringboot.application.gateways.user.IUserRepositoryGateway;
import com.ocsoares.advancedcrudspringboot.application.usecases.mapper.UserUseCaseMapper;
import com.ocsoares.advancedcrudspringboot.application.usecases.response.UserResponse;
import com.ocsoares.advancedcrudspringboot.domain.entity.UserDomainEntity;
import com.ocsoares.advancedcrudspringboot.domain.exceptions.user.UserAlreadyExistsByEmailException;
import com.ocsoares.advancedcrudspringboot.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ActiveProfiles("test")
class CreateUserUseCaseTest {
    private final UserDomainEntity testUser = TestUtils.createUser();

    @Mock
    private IUserRepositoryGateway userRepositoryGateway;

    @Mock
    private PasswordHasherGateway passwordHasherGateway;

    @Mock
    private UserUseCaseMapper userUseCaseMapper;

    @InjectMocks
    private CreateUserUseCase createUserUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("It SHOULD NOT be possible to create a user if it already exists by email")
        // RECOMENDAÇÃO de Nome para os Testes = Método + Condição desejada + CAUSA da Condição
    void execute_Fail_WhenUserAlreadyExistsByEmail() {
        when(this.userRepositoryGateway.findUserByEmail(testUser.email())).thenReturn(Optional.of(testUser));

        UserAlreadyExistsByEmailException useCaseException = Assertions.assertThrows(
                UserAlreadyExistsByEmailException.class, () -> this.createUserUseCase.execute(testUser));

        Assertions.assertEquals(UserAlreadyExistsByEmailException.EXCEPTION_MESSAGE, useCaseException.getMessage());
        verify(this.userRepositoryGateway, times(1)).findUserByEmail(testUser.email());
    }

    @Test
    @DisplayName("It should be possible to create a user")
    void execute() throws UserAlreadyExistsByEmailException {
        String hashedPassword = String.valueOf(Math.random());
        when(this.passwordHasherGateway.hash(testUser.password())).thenReturn(hashedPassword);

        var userWithHashedPassword = new UserDomainEntity(testUser.name(), testUser.email(), hashedPassword);
        when(this.userRepositoryGateway.createUser(userWithHashedPassword)).thenReturn(userWithHashedPassword);

        var userResponse = TestUtils.toResponse(userWithHashedPassword);
        when(this.userUseCaseMapper.toResponse(userWithHashedPassword)).thenReturn(userResponse);

        UserResponse createdUser = this.createUserUseCase.execute(testUser);

        verify(this.userRepositoryGateway, times(1)).findUserByEmail(testUser.email());
        verify(this.passwordHasherGateway, times(1)).hash(testUser.password());
        verify(this.userRepositoryGateway, times(1)).createUser(userWithHashedPassword);
        verify(this.userUseCaseMapper, times(1)).toResponse(userWithHashedPassword);
        Assertions.assertEquals(createdUser, userResponse);
    }
}