package com.johannag.tapup.authentication.infrastructure.framework.filters;

import com.johannag.tapup.authentication.application.services.AuthenticationService;
import com.johannag.tapup.globals.utils.Logger;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@AllArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = Logger.getLogger(AuthenticationFilter.class);
    private static final List<String> AUTH_HEADER_NAMES = List.of("Authorization", "authorization");
    private final RequestMappingHandlerMapping requestMappingHandlerMapping;
    private final AuthenticationService authenticationService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        logger.info("{} {}", request.getMethod(), request.getServletPath());

        maybeAuthorizationToken(request)
                .ifPresent(authenticationService::validateJwtToken);

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

}
