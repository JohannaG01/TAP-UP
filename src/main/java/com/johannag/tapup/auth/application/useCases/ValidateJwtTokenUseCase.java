package com.johannag.tapup.auth.application.useCases;

import com.johannag.tapup.auth.application.configs.JwtConfig;
import com.johannag.tapup.auth.application.exceptions.JwtTokenInvalidException;
import com.johannag.tapup.users.application.exceptions.UserNotFoundException;
import com.johannag.tapup.users.application.services.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Lazy})
public class ValidateJwtTokenUseCase {

    private static final Logger logger = LogManager.getLogger(ValidateJwtTokenUseCase.class);
    private final UserService userService;
    private final JwtConfig jwtConfig;

    public Claims execute(String bearerToken) throws JwtTokenInvalidException {
        logger.debug("Authenticating JWT token {}", bearerToken);

        Claims jwtClaims = extractClaims(bearerToken);
        validateUserExistsOrThrow(jwtClaims);

        logger.info("Successfully authenticated user [{}]", jwtClaims.get("email"));

        return jwtClaims;
    }

    public Claims extractClaims(String token) throws JwtTokenInvalidException {
        logger.debug("Extracting claims from token");

        try {
            SecretKey secretKey = Keys.hmacShaKeyFor(jwtConfig.getSecretWord().getBytes());

            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

        } catch (Exception ex) {
            throw buildException(String.format("Failed to parse JWT token. Error: %s", ex.getMessage()), ex);
        }
    }

    private void validateUserExistsOrThrow(Claims jwtClaims) throws JwtTokenInvalidException {
        logger.debug("Validating weather user [{}] exists or not", jwtClaims.get("email"));

        try {
            userService.findByEmail(jwtClaims.get("email").toString());
        } catch (UserNotFoundException ex) {
            throw buildException(String.format("User [%s] does not exist", jwtClaims.get("email")), ex);
        }
    }

    private JwtTokenInvalidException buildException(String errorMessage, Exception ex) {
        logger.error(errorMessage, ex);
        return new JwtTokenInvalidException(errorMessage);
    }

}
