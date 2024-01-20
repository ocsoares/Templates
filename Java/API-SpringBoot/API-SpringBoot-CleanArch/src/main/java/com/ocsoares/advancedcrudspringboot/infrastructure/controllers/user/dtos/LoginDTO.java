package com.ocsoares.advancedcrudspringboot.infrastructure.controllers.user.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(example = "{\"email\":\"johndoe@example.com\",\"password\":\"johndoe123\"}")
public record LoginDTO(
        @NotBlank(message = "The email is required") @Email(message = "Must be a valid email address") String email,
        @NotBlank(message = "The password is required") String password) {
}
