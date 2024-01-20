package com.ocsoares.advancedcrudspringboot.main.config;

import com.ocsoares.advancedcrudspringboot.application.gateways.security.IAuthServiceGateway;
import com.ocsoares.advancedcrudspringboot.application.gateways.security.PasswordHasherGateway;
import com.ocsoares.advancedcrudspringboot.application.gateways.user.IUserRepositoryGateway;
import com.ocsoares.advancedcrudspringboot.application.usecases.mapper.UserUseCaseMapper;
import com.ocsoares.advancedcrudspringboot.application.usecases.user.*;
import com.ocsoares.advancedcrudspringboot.infrastructure.controllers.user.LoginUserController;
import com.ocsoares.advancedcrudspringboot.infrastructure.controllers.user.mappers.UserControllerMapper;
import com.ocsoares.advancedcrudspringboot.infrastructure.gateways.user.jpa.JpaUserRepositoryGateway;
import com.ocsoares.advancedcrudspringboot.infrastructure.mappers.UserPersistenceEntityMapper;
import com.ocsoares.advancedcrudspringboot.infrastructure.persistence.repository.jpa.JpaUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {
    @Bean
    public CreateUserUseCase createUserUseCase(
            IUserRepositoryGateway userRepositoryGateway, PasswordHasherGateway passwordHasherGateway,
            UserUseCaseMapper userUseCaseMapper
    ) {
        return new CreateUserUseCase(userRepositoryGateway, passwordHasherGateway, userUseCaseMapper);
    }

    @Bean
    public FindAllUsersUseCase findAllUsersUseCase(
            IUserRepositoryGateway userRepositoryGateway, UserUseCaseMapper userUseCaseMapper
    ) {
        return new FindAllUsersUseCase(userRepositoryGateway, userUseCaseMapper);
    }

    @Bean
    public FindUserUseCase findUserUseCase(
            IUserRepositoryGateway userRepositoryGateway, UserUseCaseMapper userUseCaseMapper
    ) {
        return new FindUserUseCase(userRepositoryGateway, userUseCaseMapper);
    }

    @Bean
    public UpdateUserUseCase updateUserUseCase(
            IUserRepositoryGateway userRepositoryGateway, PasswordHasherGateway passwordHasherGateway
    ) {
        return new UpdateUserUseCase(userRepositoryGateway, passwordHasherGateway);
    }

    @Bean
    public DeleteUserUseCase deleteUserUseCase(IUserRepositoryGateway userRepositoryGateway) {
        return new DeleteUserUseCase(userRepositoryGateway);
    }

    @Bean
    public LoginUserController loginUserController(IAuthServiceGateway authServiceGateway) {
        return new LoginUserController(authServiceGateway);
    }

    @Bean
    public IUserRepositoryGateway userRepositoryGateway(
            JpaUserRepository jpaUserRepository, UserPersistenceEntityMapper userPersistenceEntityMapper
    ) {
        return new JpaUserRepositoryGateway(jpaUserRepository, userPersistenceEntityMapper);
    }

    @Bean
    public UserPersistenceEntityMapper userPersistenceEntityMapper() {
        return new UserPersistenceEntityMapper();
    }

    @Bean
    public UserUseCaseMapper userUseCaseMapper() {
        return new UserUseCaseMapper();
    }

    @Bean
    public UserControllerMapper userControllerMapper() {
        return new UserControllerMapper();
    }
}
