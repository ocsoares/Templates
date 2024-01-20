package com.ocsoares.advancedcrudspringboot.application.usecases.user;

import com.ocsoares.advancedcrudspringboot.application.gateways.security.PasswordHasherGateway;
import com.ocsoares.advancedcrudspringboot.application.gateways.user.IUserRepositoryGateway;
import com.ocsoares.advancedcrudspringboot.application.usecases.interfaces.IUseCaseWithArgument;
import com.ocsoares.advancedcrudspringboot.application.usecases.mapper.UserUseCaseMapper;
import com.ocsoares.advancedcrudspringboot.application.usecases.response.UserResponse;
import com.ocsoares.advancedcrudspringboot.domain.entity.UserDomainEntity;
import com.ocsoares.advancedcrudspringboot.domain.exceptions.user.UserAlreadyExistsByEmailException;

import java.util.Optional;

// NÃO usar nenhum Recurso de Frameworks, como "annotations" de algum Framework, porque essa Pasta é
// apenas a REGRA DE NEGÓCIO da Aplicação, APENAS com Código JAVA!!!
// ----------------------------------------------------------
// Esse é o SERVICE que vai ser usado pelo Controller!!!
// ----------------------------------------------------------
// TROCAR esse SEGUNDO "UserDomainEntity" por um DTO!!
public class CreateUserUseCase implements IUseCaseWithArgument<UserResponse, UserDomainEntity, Exception> {
    private final IUserRepositoryGateway userRepositoryGateway;
    private final PasswordHasherGateway passwordHasherGateway;
    private final UserUseCaseMapper userUseCaseMapper;

    public CreateUserUseCase(
            IUserRepositoryGateway userRepositoryGateway, PasswordHasherGateway passwordHasherGateway,
            UserUseCaseMapper userUseCaseMapper
    ) {
        this.userRepositoryGateway = userRepositoryGateway;
        this.passwordHasherGateway = passwordHasherGateway;
        this.userUseCaseMapper = userUseCaseMapper;
    }

    @Override
    public UserResponse execute(UserDomainEntity userEntity) throws UserAlreadyExistsByEmailException {
        Optional<UserDomainEntity> userAlreadyExists = this.userRepositoryGateway.findUserByEmail(userEntity.email());

        if (userAlreadyExists.isPresent()) {
            throw new UserAlreadyExistsByEmailException();
        }

        String hashedPassword = this.passwordHasherGateway.hash(userEntity.password());

        UserDomainEntity userWithHashedPassword = new UserDomainEntity(userEntity.name(), userEntity.email(),
                hashedPassword
        );

        UserDomainEntity createdUser = this.userRepositoryGateway.createUser(userWithHashedPassword);

        // O "createdUser" é um "UserDomainEntity", então PRECISA CONVERTER ele para o Retorno do Método,
        // que é "UserResponse" !!!
        return this.userUseCaseMapper.toResponse(createdUser);
    }
}
