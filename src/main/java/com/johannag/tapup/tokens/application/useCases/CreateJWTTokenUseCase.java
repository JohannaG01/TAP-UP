package com.johannag.tapup.tokens.application.useCases;

import com.johannag.tapup.configurations.JwtConfig;
import com.johannag.tapup.tokens.domain.models.TokenModel;
import com.johannag.tapup.users.domain.models.UserModel;
import com.johannag.tapup.utils.DateTimeUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
@AllArgsConstructor
public class CreateJWTTokenUseCase {

    private static final Logger logger = LogManager.getLogger(CreateJWTTokenUseCase.class);
    private final JwtConfig jwtConfig;

    public TokenModel execute(UserModel user) {
        logger.info("Creating JWT Authentication Token for user [{}]", user.getName());

        SecretKey secretKey = Keys.hmacShaKeyFor(jwtConfig.getSecretWord().getBytes());
        Claims claims = buildClaims(user);

        String jwtToken = Jwts.builder()
                .claims(claims)
                .signWith(secretKey)
                .compact();

        TokenModel tokenModel = TokenModel.builder()
                .value(jwtToken)
                .type("Bearer")
                .issuedAt(DateTimeUtils.toLocalDateTime(claims.getIssuedAt()))
                .expiresAt(DateTimeUtils.toLocalDateTime(claims.getExpiration()))
                .build();

        logger.info("JWT Authentication Token has been created successfully");

        return tokenModel;
    }

    private Claims buildClaims(UserModel user) {

        Date now = DateTimeUtils.nowAsDate();
        Date expirationDate = new Date(now.getTime() + jwtConfig.getExpiresInSeconds() * 1000L);

        return Jwts.claims()
                .subject(user.getUuid().toString())
                .issuedAt(now)
                .expiration(expirationDate)
                .add("uuid", user.getUuid())
                .add("firstName", user.getName())
                .add("lastName", user.getLastName())
                .add("email", user.getEmail())
                .add("isAdmin", user.getIsAdmin())
                .build();
    }

}
