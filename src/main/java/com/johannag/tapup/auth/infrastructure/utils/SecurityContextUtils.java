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

public class SecurityContextUtils {

    public static Optional<UUID> maybeUserUuid(){
        return maybeUserOnContext()
                .map(UserOnContext::getUuid);
    }

    public static Optional<UserOnContext> maybeUserOnContext() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .flatMap(context -> Optional.ofNullable(context.getAuthentication()))
                .flatMap(authentication -> Optional.ofNullable(authentication.getDetails()))
                .filter(UserOnContext.class::isInstance)
                .map(UserOnContext.class::cast);
    }

    public static Optional<List<RoleOnContext>> maybeRolesOnContext() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .flatMap(context -> Optional.ofNullable(context.getAuthentication()))
                .map(Authentication::getAuthorities)
                .map(authorities -> authorities.stream().map(SecurityContextUtils::convertToRoleOnContext))
                .map(Stream::toList);
    }

    private static RoleOnContext convertToRoleOnContext(GrantedAuthority authority) {
        return RoleOnContext.valueOf(authority.getAuthority());
    }
}
