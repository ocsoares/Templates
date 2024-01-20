package com.ocsoares.advancedcrudspringboot.infrastructure.controllers.user;

import com.ocsoares.advancedcrudspringboot.application.usecases.user.UpdateUserUseCase;
import com.ocsoares.advancedcrudspringboot.domain.entity.UserDomainEntity;
import com.ocsoares.advancedcrudspringboot.infrastructure.controllers.interfaces.IControllerWithTwoArguments;
import com.ocsoares.advancedcrudspringboot.infrastructure.controllers.user.dtos.UserDTO;
import com.ocsoares.advancedcrudspringboot.infrastructure.controllers.user.mappers.UserControllerMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UpdateUserController implements IControllerWithTwoArguments<Void, UUID, UserDTO, Exception> {
    private final UpdateUserUseCase updateUserUseCase;
    private final UserControllerMapper userControllerMapper;

    @Operation(summary = "Update a user", tags = "User")
    @ApiResponse(responseCode = "204")
    @ApiResponse(responseCode = "400")
    @ApiResponse(responseCode = "403")
    @ApiResponse(responseCode = "500")
    @PatchMapping("user/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    @Override
    public Void handle(
            @PathVariable(value = "id") UUID id, @RequestBody @Valid UserDTO userDTO
    ) throws Exception {
        UserDomainEntity updateUserDomain = this.userControllerMapper.toDomain(userDTO);

        this.updateUserUseCase.execute(id, updateUserDomain);

        return null;
    }
}
