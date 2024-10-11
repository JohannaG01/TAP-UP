package com.johannag.tapup.auth.infrastructure.utils;

import com.johannag.tapup.users.infrastructure.framework.context.RoleOnContext;
import com.johannag.tapup.users.infrastructure.framework.context.UserOnContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * Utility class for retrieving user information from the security context.
 */
public class SecurityContextUtils {

    /**
     * Retrieves the UUID of the user on the current security context, if present.
     *
     * @return An {@link Optional} containing the user's UUID if present, otherwise an empty {@link Optional}.
     */
    public static Optional<UUID> maybeUserUuid() {
        return maybeUserOnContext()
                .map(UserOnContext::getUuid);
    }

    /**
     * Retrieves the {@link UserOnContext} from the current security context, if present.
     *
     * @return An {@link Optional} containing the {@link UserOnContext} if present, otherwise an empty {@link Optional}.
     */
    public static Optional<UserOnContext> maybeUserOnContext() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .flatMap(context -> Optional.ofNullable(context.getAuthentication()))
                .flatMap(authentication -> Optional.ofNullable(authentication.getDetails()))
                .filter(UserOnContext.class::isInstance)
                .map(UserOnContext.class::cast);
    }

    /**
     * Retrieves the list of roles associated with the user in the current security context, if present.
     *
     * @return An {@link Optional} containing a list of {@link RoleOnContext} if roles are present, otherwise an
     * empty {@link Optional}.
     */
    public static Optional<List<RoleOnContext>> maybeRolesOnContext() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .flatMap(context -> Optional.ofNullable(context.getAuthentication()))
                .map(Authentication::getAuthorities)
                .map(authorities -> authorities.stream().map(SecurityContextUtils::convertToRoleOnContext))
                .map(Stream::toList);
    }


    /**
     * Converts a {@link GrantedAuthority} to a {@link RoleOnContext}.
     *
     * @param authority The {@link GrantedAuthority} to convert.
     * @return The corresponding {@link RoleOnContext}.
     */
    private static RoleOnContext convertToRoleOnContext(GrantedAuthority authority) {
        return RoleOnContext.valueOf(authority.getAuthority());
    }
}
