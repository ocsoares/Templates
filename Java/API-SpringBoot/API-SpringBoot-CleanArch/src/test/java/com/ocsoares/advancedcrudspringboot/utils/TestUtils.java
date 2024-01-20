package com.ocsoares.advancedcrudspringboot.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ocsoares.advancedcrudspringboot.application.gateways.security.ITokenServiceGateway;
import com.ocsoares.advancedcrudspringboot.application.gateways.user.IUserRepositoryGateway;
import com.ocsoares.advancedcrudspringboot.application.usecases.response.UserResponse;
import com.ocsoares.advancedcrudspringboot.domain.entity.UserDomainEntity;
import com.ocsoares.advancedcrudspringboot.domain.exceptions.security.ErrorCreatingJWTException;
import com.ocsoares.advancedcrudspringboot.domain.exceptions.user.InvalidUserByEmailException;
import com.ocsoares.advancedcrudspringboot.infrastructure.persistence.entity.UserPersistenceEntity;

import java.util.List;
import java.util.UUID;

public class TestUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static UserDomainEntity createUser() {
        return new UserDomainEntity("Bernado", "bernado@gmail.com", "bernadinho123");
    }

    public static UserDomainEntity createUserDatabase(IUserRepositoryGateway userRepositoryGateway) {
        UserDomainEntity userDTO = TestUtils.createUser();

        return userRepositoryGateway.createUser(userDTO);
    }

    public static String JSONUserDTO() throws JsonProcessingException {
        UserDomainEntity user = TestUtils.createUser();

        return TestUtils.objectMapper.writeValueAsString(user);
    }

    public static UserPersistenceEntity createUserWithId() {
        var testUser = new UserPersistenceEntity("Bernado", "bernado@gmail.com", "bernadinho123");
        testUser.setId(UUID.randomUUID());

        return testUser;
    }

    public static UserDomainEntity toDomain(UserPersistenceEntity userPersistenceEntity) {
        return new UserDomainEntity(userPersistenceEntity.getName(), userPersistenceEntity.getEmail(),
                userPersistenceEntity.getPassword()
        );
    }

    public static UserPersistenceEntity toPersistence(UserDomainEntity userDomainEntity) {
        return new UserPersistenceEntity(userDomainEntity.name(), userDomainEntity.email(),
                userDomainEntity.password()
        );
    }

    public static UserResponse toResponse(UserDomainEntity userDomainEntity) {
        return new UserResponse(userDomainEntity.name(), userDomainEntity.email());
    }

    public static List<UserResponse> toResponseList(List<UserDomainEntity> userDomainEntityList) {
        return userDomainEntityList.stream().map(TestUtils::toResponse).toList();
    }

    public static String expectedUserResponse() throws JsonProcessingException {
        UserDomainEntity userDTO = TestUtils.createUser();
        var createdUserResponse = new UserResponse(userDTO.name(), userDTO.email());

        return TestUtils.objectMapper.writeValueAsString(createdUserResponse);
    }

    public static String generateToken(
            IUserRepositoryGateway userRepositoryGateway, ITokenServiceGateway tokenServiceGateway
    ) throws ErrorCreatingJWTException, InvalidUserByEmailException {
        var userDTO = new UserDomainEntity("Wesley", "wesley@gmail.com", "wesley123");

        UserDomainEntity createdUser = userRepositoryGateway.createUser(userDTO);
        String userId = userRepositoryGateway.getUserIdByEmail(createdUser.email())
                .orElseThrow(InvalidUserByEmailException::new);

        return tokenServiceGateway.generateToken(userId, createdUser);
    }

    public static String generateTokenWithOwnUser(
            IUserRepositoryGateway userRepositoryGateway, ITokenServiceGateway tokenServiceGateway,
            UserDomainEntity createdUser
    ) throws InvalidUserByEmailException, ErrorCreatingJWTException {
        String userId = userRepositoryGateway.getUserIdByEmail(createdUser.email())
                .orElseThrow(InvalidUserByEmailException::new);

        return tokenServiceGateway.generateToken(userId, createdUser);
    }

    public static String createUserAndGetId(
            IUserRepositoryGateway userRepositoryGateway, UserDomainEntity userDTO
    ) throws InvalidUserByEmailException {
        UserDomainEntity createdUser = userRepositoryGateway.createUser(userDTO);

        return userRepositoryGateway.getUserIdByEmail(createdUser.email())
                .orElseThrow(InvalidUserByEmailException::new);
    }
}
