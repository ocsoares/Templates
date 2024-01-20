package com.ocsoares.advancedcrudspringboot.infrastructure.security.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.ocsoares.advancedcrudspringboot.application.gateways.security.ITokenServiceGateway;
import com.ocsoares.advancedcrudspringboot.domain.entity.UserDomainEntity;
import com.ocsoares.advancedcrudspringboot.domain.exceptions.security.ErrorCreatingJWTException;
import com.ocsoares.advancedcrudspringboot.domain.exceptions.security.ErrorJWTVerificationException;
import com.ocsoares.advancedcrudspringboot.infrastructure.mappers.UserPersistenceEntityMapper;
import com.ocsoares.advancedcrudspringboot.infrastructure.persistence.entity.UserPersistenceEntity;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@RequiredArgsConstructor
public class JwtService implements ITokenServiceGateway {
    // Usei a Secret "TvhTvDZ5ryTrcj5X" para Funcionar nos TESTES, porque nos Testes o "JWT_SECRET" NÃO tem como
    // ser Setado (retorna NULL), então usando assim Permite FUNCIONAR nos Testes!!!
    private static final String JWT_SECRET = System.getenv("JWT_SECRET") != null ? System.getenv(
            "JWT_SECRET") : "TvhTvDZ5ryTrcj5X";
    private static final String JWT_ISSUER = "advanced-crud-spring-boot-auth";
    private final UserPersistenceEntityMapper userPersistenceEntityMapper;

    @Override
    public String generateToken(String id, UserDomainEntity userDomainEntity) throws ErrorCreatingJWTException {
        UserPersistenceEntity userPersistenceEntity = this.userPersistenceEntityMapper.toPersistence(userDomainEntity);

        try {
            Algorithm algorithm = Algorithm.HMAC256(JwtService.JWT_SECRET);

            return JWT.create()
                    .withIssuer(JwtService.JWT_ISSUER) // Nome do EMISSOR
                    .withSubject(userPersistenceEntity.getEmail()) // É o "sub" do JWT, a quem o Token PERTENCE
                    .withClaim("id", id)
                    .withClaim("name", userPersistenceEntity.getName())
                    .withExpiresAt(this.getExpirationDate())
                    .sign(algorithm);

        } catch (JWTCreationException exception) {
            throw new ErrorCreatingJWTException();
        }
    }

    @Override
    public String validateToken(String token) throws ErrorJWTVerificationException {
        try {
            Algorithm algorithm = Algorithm.HMAC256(JwtService.JWT_SECRET);

            return JWT.require(algorithm).withIssuer(JwtService.JWT_ISSUER).build().verify(token).getSubject();
        } catch (JWTVerificationException exception) {
            throw new ErrorJWTVerificationException();
        }
    }

    @Override
    public Instant getExpirationDate() {
        // "-03:00" = Brasil
        return LocalDateTime.now().plusHours(24).toInstant(ZoneOffset.of("-03:00"));
    }
}