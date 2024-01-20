package com.ocsoares.advancedcrudspringboot.infrastructure.controllers.user.mappers;

import com.ocsoares.advancedcrudspringboot.domain.entity.UserDomainEntity;
import com.ocsoares.advancedcrudspringboot.infrastructure.controllers.user.dtos.UserDTO;

public class UserControllerMapper {
    public UserDomainEntity toDomain(UserDTO userDTO) {
        return new UserDomainEntity(userDTO.name(), userDTO.email(), userDTO.password());
    }
}
