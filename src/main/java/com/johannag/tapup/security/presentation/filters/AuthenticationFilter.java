package com.johannag.tapup.security.presentation.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private RequestMappingHandlerMapping requestMappingHandlerMapping;
    private static final Logger logger = LogManager.getLogger(AuthenticationFilter.class);
    private static final List<String> AUTH_HEADER_NAMES = List.of("Authorization", "authorization");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        maybeAuthorizationToken(request)
                .ifPresent(token -> attemptAuthentication(request, token));

        filterChain.doFilter(request, response);
    }

    private Optional<String> maybeAuthorizationToken(HttpServletRequest request) {
        return AUTH_HEADER_NAMES.stream()
                .map(request::getHeader)
                .filter(Objects::nonNull)
                .findFirst();
    }

    private void attemptAuthentication(HttpServletRequest request, String token) {
        if (pathExists(request)) {
            authenticateOnContextForJwt(token);
        }
    }

    private boolean pathExists(HttpServletRequest request) {
        boolean exists;

        try {
            exists = requestMappingHandlerMapping.getHandler(request) != null;
        } catch (Exception e) {
            logger.error("Error verifying path existence for {}: {}", request.getServletPath(), e.getMessage(), e);
            return false;
        }

        if (!exists) {
            logger.warn("Accessed path {} does not exist", request.getServletPath());
        }

        return exists;
    }

    //TODO this in tokenService
    private void authenticateOnContextForJwt(String bearerToken) {
        try {
            String token = extractBearerToken(bearerToken);
            Claims jwtClaims = extractClaims(token);

            String jwtEmail = jwtClaims.getSubject();
            UUID jwtUUUID = UUID.fromString(jwtClaims.get("uuid", String.class));

            setAuthentication(token, jwtEmail, jwtUUUID);
            logger.info(String.format("Successfully authenticated user %s", jwtEmail));
        } catch (UnauthorizedException ex) {
            logger.error(
                    String.format("An error occurred during JWT authentication. Error %s", ex.getMessage()));
        }
    }

    private String extractBearerToken(String bearerToken) {
        if (bearerToken.startsWith("Bearer ")) {
            return bearerToken.split(" ")[1].trim();
        } else {
            throw new UnauthorizedException("Authorization must be Bearer");
        }
    }

    public Claims extractClaims(String token) throws UnauthorizedException {
        try {
            SecretKey secretKey = Keys.hmacShaKeyFor(jwtConfiguration.getSecretWord().getBytes());
            return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
        } catch (Exception ex) {
            throw new UnauthorizedException(ex.getMessage());
        }
    }

    private void setAuthentication(String token, String jwtEmail, UUID jwtUUID) {
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(jwtEmail, "", new ArrayList<>());

        AuthenticationDetails authDetails = new AuthenticationDetails();
        authDetails.setUuid(jwtUUID);
        authDetails.setToken(token);

        authentication.setDetails(authDetails);

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
