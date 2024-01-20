package com.ocsoares.advancedcrudspringboot.infrastructure.gateways.user.jpa;

import com.ocsoares.advancedcrudspringboot.application.gateways.user.IUserRepositoryGateway;
import com.ocsoares.advancedcrudspringboot.domain.entity.UserDomainEntity;
import com.ocsoares.advancedcrudspringboot.infrastructure.mappers.UserPersistenceEntityMapper;
import com.ocsoares.advancedcrudspringboot.infrastructure.persistence.entity.UserPersistenceEntity;
import com.ocsoares.advancedcrudspringboot.infrastructure.persistence.repository.jpa.JpaUserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

// Classe que vai de fato IMPLEMENTAR os Métodos como SALVAR o Usuário, por exemplo, e como está na Camada de
// "Infrastructure" PODE usar recursos de Frameworks!!!
public class JpaUserRepositoryGateway implements IUserRepositoryGateway {
    private final JpaUserRepository jpaUserRepository;
    private final UserPersistenceEntityMapper userPersistenceEntityMapper;

    public JpaUserRepositoryGateway(
            JpaUserRepository jpaUserRepository, UserPersistenceEntityMapper userPersistenceEntityMapper
    ) {
        this.jpaUserRepository = jpaUserRepository;
        this.userPersistenceEntityMapper = userPersistenceEntityMapper;
    }

    @Override
    public UserDomainEntity createUser(UserDomainEntity userDomainEntity) {
        UserPersistenceEntity userPersistenceEntity = this.userPersistenceEntityMapper.toPersistence(userDomainEntity);

        UserPersistenceEntity savedUser = this.jpaUserRepository.save(userPersistenceEntity);

        return this.userPersistenceEntityMapper.toDomain(savedUser);
    }

    @Override
    public Optional<UserDomainEntity> findUserByEmail(String email) {
        Optional<UserPersistenceEntity> userByEmail = this.jpaUserRepository.findByEmail(email);

        if (userByEmail.isPresent()) {
            UserPersistenceEntity userPersistence = userByEmail.get();
            UserDomainEntity userDomain = this.userPersistenceEntityMapper.toDomain(userPersistence);

            return Optional.of(userDomain);
        }

        return Optional.empty();
    }

    @Override
    public List<UserDomainEntity> findAllUsers() {
        List<UserPersistenceEntity> allUsersFound = this.jpaUserRepository.findAll();

        return this.userPersistenceEntityMapper.toDomainList(allUsersFound);
    }

    @Override
    public Optional<UserDomainEntity> findUserById(UUID id) {
        Optional<UserPersistenceEntity> userFoundById = this.jpaUserRepository.findById(id);

        if (userFoundById.isPresent()) {
            UserDomainEntity userDomainFoundById = this.userPersistenceEntityMapper.toDomain(userFoundById.get());

            return Optional.of(userDomainFoundById);
        }

        return Optional.empty();
    }

    @Override
    public Void deleteUserById(UUID id) {
        this.jpaUserRepository.deleteById(id);

        return null;
    }

    @Override
    public Void updateUserById(UUID id, UserDomainEntity userDomainEntity) {
        Optional<UserPersistenceEntity> foundUserById = this.jpaUserRepository.findById(id);

        if (foundUserById.isPresent()) {
            UserPersistenceEntity user = foundUserById.get();
            user.setName(userDomainEntity.name());
            user.setEmail(userDomainEntity.email());
            user.setPassword(userDomainEntity.password());

            this.jpaUserRepository.save(user);
        }

        return null;
    }

    @Override
    public Optional<String> getUserIdByEmail(String email) {
        Optional<UserPersistenceEntity> userByEmail = this.jpaUserRepository.findByEmail(email);

        if (userByEmail.isPresent()) {
            UserPersistenceEntity userPersistence = userByEmail.get();
            String userId = userPersistence.getId().toString();

            return Optional.of(userId);
        }

        return Optional.empty();
    }

    @Override
    public Void deleteAll() {
        this.jpaUserRepository.deleteAll();

        return null;
    }
}