package com.johannag.tapup.auth.application.useCases;

import com.johannag.tapup.auth.application.configs.JwtConfig;
import com.johannag.tapup.auth.domain.models.AuthTokenModel;
import com.johannag.tapup.globals.application.utils.DateTimeUtils;
import com.johannag.tapup.globals.infrastructure.utils.Logger;
import com.johannag.tapup.users.domain.models.UserModel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
@AllArgsConstructor
public class CreateJwtTokenUseCase {

    private static final Logger logger = Logger.getLogger(CreateJwtTokenUseCase.class);
    private final JwtConfig jwtConfig;

    public AuthTokenModel execute(Long id, UserModel user) {
        logger.info("Creating JWT Authentication Token for user [{}]", user.getEmail());

        Claims claims = buildClaims(id, user);
        String jwtToken = buildJwtToken(claims);
        AuthTokenModel authTokenModel = buildAuthTokenModel(jwtToken, claims);

        logger.info("JWT Authentication Token has been created successfully");
        return authTokenModel;
    }

    private Claims buildClaims(Long id, UserModel user) {
        Date now = DateTimeUtils.nowAsDate();
        Date expirationDate = new Date(now.getTime() + jwtConfig.getExpiresInSeconds() * 1000L);

        return Jwts.claims()
                .subject(user.getUuid().toString())
                .issuedAt(now)
                .expiration(expirationDate)
                .add("id", id)
                .add("uuid", user.getUuid())
                .add("firstName", user.getName())
                .add("lastName", user.getLastName())
                .add("email", user.getEmail())
                .add("isAdmin", user.getIsAdmin())
                .build();
    }

    private String buildJwtToken(Claims claims) {
        SecretKey secretKey = Keys.hmacShaKeyFor(jwtConfig.getSecretWord().getBytes());

        return Jwts.builder()
                .claims(claims)
                .signWith(secretKey)
                .compact();
    }

    private AuthTokenModel buildAuthTokenModel(String jwtToken, Claims claims) {
        return AuthTokenModel.builder()
                .value(jwtToken)
                .type("Bearer")
                .issuedAt(DateTimeUtils.toLocalDateTime(claims.getIssuedAt()))
                .expiresAt(DateTimeUtils.toLocalDateTime(claims.getExpiration()))
                .build();
    }

}
