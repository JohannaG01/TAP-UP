package com.johannag.tapup.auth.application.useCases;

import com.johannag.tapup.auth.application.configs.JwtConfig;
import com.johannag.tapup.auth.application.exceptions.JwtTokenInvalidException;
import com.johannag.tapup.globals.infrastructure.utils.Logger;
import com.johannag.tapup.users.application.exceptions.UserNotFoundException;
import com.johannag.tapup.users.application.services.UserService;
import com.johannag.tapup.users.infrastructure.framework.context.UserOnContext;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.johannag.tapup.users.infrastructure.framework.context.RoleOnContext.ADMIN;
import static com.johannag.tapup.users.infrastructure.framework.context.RoleOnContext.REGULAR;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Lazy})
public class ValidateJwtTokenUseCase {

    private static final Logger logger = Logger.getLogger(ValidateJwtTokenUseCase.class);
    private final UserService userService;
    private final JwtConfig jwtConfig;

    //TODO Should only extract claims, object should e made by filter
    public void execute(String bearerToken) throws JwtTokenInvalidException {
        logger.debug("Authenticating JWT token {}", bearerToken);

        Claims jwtClaims = extractClaims(bearerToken);
        UserOnContext userOnContext = buildUserOnContext(jwtClaims);
        validateUserExistsOrThrow(userOnContext);
        List<GrantedAuthority> authorities = buildAuthorities(jwtClaims);
        setAuthenticationOnContext(userOnContext, authorities);

        logger.info("Successfully authenticated user [{}]", jwtClaims.get("email"));
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

    private UserOnContext buildUserOnContext(Claims jwtClaims) {
        return UserOnContext.builder()
                .uuid(UUID.fromString(jwtClaims.getSubject()))
                .email(jwtClaims.get("email").toString())
                .name(jwtClaims.get("firstName").toString())
                .lastName(jwtClaims.get("lastName").toString())
                .build();
    }

    private void validateUserExistsOrThrow(UserOnContext userOnContext) throws JwtTokenInvalidException {
        logger.debug("Validating weather user [{}] exists or not", userOnContext.getEmail());

        try {
            userService.findByEmail(userOnContext.getEmail());
        } catch (UserNotFoundException ex) {
            throw buildException(String.format("User [%s] does not exist", userOnContext.getEmail()), ex);
        }
    }

    private List<GrantedAuthority> buildAuthorities(Claims jwtClaims) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        if (jwtClaims.get("isAdmin").toString().equals("true")) {
            authorities.add(new SimpleGrantedAuthority(ADMIN.toString()));
        } else {
            authorities.add(new SimpleGrantedAuthority(REGULAR.toString()));
        }

        return authorities;
    }

    private void setAuthenticationOnContext(UserOnContext userOnContext, List<GrantedAuthority> authorities) {
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userOnContext.getEmail(), null, authorities);

        authentication.setDetails(userOnContext);

        SecurityContextHolder
                .getContext()
                .setAuthentication(authentication);
    }

    private JwtTokenInvalidException buildException(String errorMessage, Exception ex) {
        logger.error(errorMessage, ex);
        return new JwtTokenInvalidException(errorMessage);
    }

}
