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

        Optional<String> maybeToken = maybeAuthorizationToken(request);

        if (!pathExists(request) || (maybeToken.isEmpty())) {
            filterChain.doFilter(request, response);
            return;
        }

        authenticateOnContextForJwt(maybeToken.get());

        filterChain.doFilter(request, response);
    }

    private Optional<String> maybeAuthorizationToken(HttpServletRequest request) {
        return AUTH_HEADER_NAMES.stream()
                .map(request::getHeader)
                .filter(Objects::nonNull)
                .findFirst();
    }

    private boolean pathExists(HttpServletRequest request) {
        try {
            if (requestMappingHandlerMapping.getHandler(request) == null) {
                logger.warn("Accessed path {} does not exists", request.getServletPath());
                return false;
            }
            return true;
        } catch (Exception e) {
            logger.error("An error occurred while verifying whether or not the path exists. Error: {}", e.getMessage());
            return false;
        }
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
