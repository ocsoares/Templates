package com.ocsoares.advancedcrudspringboot.infrastructure.persistence.repository.jpa;

import com.ocsoares.advancedcrudspringboot.infrastructure.persistence.entity.UserPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaUserRepository extends JpaRepository<UserPersistenceEntity, UUID> {
    Optional<UserPersistenceEntity> findByEmail(String email);
}
