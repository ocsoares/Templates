package com.ocsoares.advancedcrudspringboot.application.gateways.user;

import com.ocsoares.advancedcrudspringboot.domain.entity.UserDomainEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

// Esse "UserGateway" vai ser um PORTÃO de ENTRADA (uma Abstração) para a Classe em uma Camada mais
// EXTERNA que de Fato vai ser Responsável pelos Métodos de SALVAR o Usuário, por exemplo!!
public interface IUserRepositoryGateway {
    UserDomainEntity createUser(UserDomainEntity userDomainEntity);
    Optional<UserDomainEntity> findUserByEmail(String email);
    List<UserDomainEntity> findAllUsers();
    Optional<UserDomainEntity> findUserById(UUID id);
    Void deleteUserById(UUID id);
    Void updateUserById(UUID id, UserDomainEntity userDomainEntity);
    Optional<String> getUserIdByEmail(String email);
    Void deleteAll();
}
