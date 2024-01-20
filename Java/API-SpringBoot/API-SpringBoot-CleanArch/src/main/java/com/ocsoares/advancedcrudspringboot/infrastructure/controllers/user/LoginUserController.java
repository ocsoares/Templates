package com.ocsoares.advancedcrudspringboot.infrastructure.controllers.user;

import com.ocsoares.advancedcrudspringboot.application.gateways.security.IAuthServiceGateway;
import com.ocsoares.advancedcrudspringboot.domain.exceptions.response.MessageAndStatusCodeResponse;
import com.ocsoares.advancedcrudspringboot.infrastructure.controllers.interfaces.IControllerWithArgument;
import com.ocsoares.advancedcrudspringboot.infrastructure.controllers.user.dtos.LoginDTO;
import com.ocsoares.advancedcrudspringboot.infrastructure.controllers.user.response.TokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginUserController implements IControllerWithArgument<TokenResponse, LoginDTO, Exception> {
    private final IAuthServiceGateway authServiceGateway;

    @Operation(summary = "Login user", tags = "Authentication")
    @ApiResponse(responseCode = "200")
    @ApiResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = MessageAndStatusCodeResponse.class)))
    @ApiResponse(responseCode = "500")
    @SecurityRequirement(name = "none")
    @PostMapping("auth/login")
    @Override
    public TokenResponse handle(@RequestBody @Valid LoginDTO loginDTO) throws Exception {
        String token = this.authServiceGateway.login(loginDTO.email(), loginDTO.password());

        return new TokenResponse(token);
    }
}
