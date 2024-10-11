package com.johannag.tapup.auth.infrastructure.framework.filters;

import com.johannag.tapup.auth.application.services.AuthenticationService;
import com.johannag.tapup.users.infrastructure.framework.context.UserOnContext;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;

import static com.johannag.tapup.users.infrastructure.framework.context.RoleOnContext.ADMIN;
import static com.johannag.tapup.users.infrastructure.framework.context.RoleOnContext.REGULAR;

@Component
@AllArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LogManager.getLogger(AuthenticationFilter.class);
    private static final List<String> AUTH_HEADER_NAMES = List.of("Authorization", "authorization");
    private final AuthenticationService authenticationService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        logger.info("{} {}", request.getMethod(), request.getServletPath());

        maybeAuthorizationToken(request)
                .map(authenticationService::validateJwtToken)
                .ifPresent(this::handleAuthentication);

        filterChain.doFilter(request, response);
    }

    private Optional<String> maybeAuthorizationToken(HttpServletRequest request) {
        return AUTH_HEADER_NAMES.stream()
                .map(request::getHeader)
                .filter(Objects::nonNull)
                .flatMap(token -> extractBearerToken(token).stream())
                .findFirst();
    }

    private Optional<String> extractBearerToken(String bearerToken) {
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return Optional.of(bearerToken.substring(7).trim());
        }
        return Optional.empty();
    }

    private void handleAuthentication(Claims claims) {
        UserOnContext userOnContext = buildUserOnContext(claims);
        List<GrantedAuthority> authorities = buildAuthorities(claims);
        setAuthenticationOnContext(userOnContext, authorities);
    }

    private UserOnContext buildUserOnContext(Claims jwtClaims) {
        return UserOnContext.builder()
                .uuid(UUID.fromString(jwtClaims.getSubject()))
                .email(jwtClaims.get("email").toString())
                .name(jwtClaims.get("firstName").toString())
                .lastName(jwtClaims.get("lastName").toString())
                .build();
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

}
