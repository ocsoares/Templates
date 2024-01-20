package com.ocsoares.advancedcrudspringboot.application.usecases.user;

import com.ocsoares.advancedcrudspringboot.application.gateways.security.PasswordHasherGateway;
import com.ocsoares.advancedcrudspringboot.application.gateways.user.IUserRepositoryGateway;
import com.ocsoares.advancedcrudspringboot.domain.entity.UserDomainEntity;
import com.ocsoares.advancedcrudspringboot.domain.exceptions.user.InvalidUserByIdException;
import com.ocsoares.advancedcrudspringboot.infrastructure.persistence.entity.UserPersistenceEntity;
import com.ocsoares.advancedcrudspringboot.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;

class UpdateUserUseCaseTest {
    private final UserPersistenceEntity testUser = TestUtils.createUserWithId();
    private final UserDomainEntity testUserDomain = TestUtils.toDomain(testUser);

    @Mock
    private IUserRepositoryGateway userRepositoryGateway;

    @Mock
    private PasswordHasherGateway passwordHasherGateway;

    @InjectMocks
    private UpdateUserUseCase updateUserUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("It SHOULD NOT be possible update a user if it does not exists by Id")
    void execute_Fail_WhenUserDoesNotExistsById() {
        when(this.userRepositoryGateway.findUserById(testUser.getId())).thenReturn(Optional.empty());

        InvalidUserByIdException useCaseException = Assertions.assertThrows(InvalidUserByIdException.class,
                () -> this.updateUserUseCase.execute(testUser.getId(), testUserDomain)
        );

        Assertions.assertEquals(InvalidUserByIdException.EXCEPTION_MESSAGE, useCaseException.getMessage());
    }

    @Test
    @DisplayName("It should be possible to update a user")
    void execute() throws InvalidUserByIdException {
        when(this.userRepositoryGateway.findUserById(testUser.getId())).thenReturn(Optional.of(testUserDomain));

        var newUserData = new UserDomainEntity("Guilherme Carvalho", "guilherme8010@gmail.com", "guilherme123");

        String hashedPassword = String.valueOf(Math.random());
        when(this.passwordHasherGateway.hash(newUserData.password())).thenReturn(hashedPassword);

        var newUserWithHashedPassword = new UserDomainEntity(newUserData.name(), newUserData.email(), hashedPassword);

        // Usar dessa Maneira com "doNothing()" e "when" para Métodos que Retornam "Void" !!!!
        doNothing().when(this.userRepositoryGateway).updateUserById(testUser.getId(), newUserWithHashedPassword);

        Void updatedUser = this.updateUserUseCase.execute(testUser.getId(), newUserData);

        verify(this.userRepositoryGateway, times(1)).findUserById(testUser.getId());
        verify(this.passwordHasherGateway, times(1)).hash(newUserData.password());
        verify(this.userRepositoryGateway, times(1)).updateUserById(testUser.getId(), newUserWithHashedPassword);
        Assertions.assertNull(updatedUser);
    }
}