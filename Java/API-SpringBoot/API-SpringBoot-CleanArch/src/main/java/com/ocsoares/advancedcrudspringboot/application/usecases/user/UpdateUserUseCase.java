package com.ocsoares.advancedcrudspringboot.application.usecases.user;

import com.ocsoares.advancedcrudspringboot.application.gateways.security.PasswordHasherGateway;
import com.ocsoares.advancedcrudspringboot.application.gateways.user.IUserRepositoryGateway;
import com.ocsoares.advancedcrudspringboot.application.usecases.interfaces.IUseCaseWithTwoArguments;
import com.ocsoares.advancedcrudspringboot.domain.entity.UserDomainEntity;
import com.ocsoares.advancedcrudspringboot.domain.exceptions.user.InvalidUserByIdException;

import java.util.Optional;
import java.util.UUID;

public class UpdateUserUseCase implements IUseCaseWithTwoArguments<Void, UUID, UserDomainEntity, Exception> {
    private final IUserRepositoryGateway userRepositoryGateway;
    private final PasswordHasherGateway passwordHasherGateway;

    public UpdateUserUseCase(
            IUserRepositoryGateway userRepositoryGateway, PasswordHasherGateway passwordHasherGateway
    ) {
        this.userRepositoryGateway = userRepositoryGateway;
        this.passwordHasherGateway = passwordHasherGateway;
    }

    @Override
    public Void execute(UUID id, UserDomainEntity userDomainEntity) throws InvalidUserByIdException {
        Optional<UserDomainEntity> userFoundById = this.userRepositoryGateway.findUserById(id);

        if (userFoundById.isEmpty()) {
            throw new InvalidUserByIdException();
        }

        String hashedPassword = this.passwordHasherGateway.hash(userDomainEntity.password());

        UserDomainEntity userDomainWithHashedPassword = new UserDomainEntity(userDomainEntity.name(),
                userDomainEntity.email(), hashedPassword
        );

        return this.userRepositoryGateway.updateUserById(id, userDomainWithHashedPassword);
    }
}
