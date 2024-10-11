package com.johannag.tapup.auth.infrastructure.framework.aspects;

import com.johannag.tapup.auth.infrastructure.framework.exceptions.AuthorizeAspectException;
import com.johannag.tapup.auth.infrastructure.framework.exceptions.ForbiddenException;
import com.johannag.tapup.auth.infrastructure.utils.SecurityContextUtils;
import com.johannag.tapup.globals.infrastructure.utils.Logger;
import com.johannag.tapup.users.infrastructure.framework.context.RoleOnContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Aspect
@Component
public class AuthorizeAspect {

    private final Logger logger = Logger.getLogger(this.getClass());

    @Before("@annotation(com.johannag.tapup.auth.presentation.annotations.Authorize) && args(userUuid,..)")
    public void authorize(JoinPoint joinPoint, UUID userUuid) throws ForbiddenException, AuthorizeAspectException {

        logger.debug("Starting authorize process for user {}", userUuid);

        boolean isAdminAllowed = isAdminAllowed(joinPoint);
        boolean isUserOnContextAdmin = isUserOnContextAdmin();
        UUID userOnContextUuid = userOnContextUUID();
        authorizeUser(isUserOnContextAdmin, isAdminAllowed, userUuid, userOnContextUuid);

        logger.debug("Finished authorize process for user {}", userUuid);

    }

    private boolean isAdminAllowed(JoinPoint joinPoint) {
        logger.debug("Determining if admin is allowed in method");

        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        validatePreAuthorizeAnnotationIsPresentOrThrow(method);
        String annotationValue = method.getAnnotation(PreAuthorize.class).value();
        validateHasAnyAuthorityMethodIsPresentOrThrow(annotationValue);
        List<RoleOnContext> requiredRoles = extractRoles(annotationValue);

        return requiredRoles.contains(RoleOnContext.ADMIN);
    }

    private void validatePreAuthorizeAnnotationIsPresentOrThrow(Method method) throws AuthorizeAspectException {
        if (!method.isAnnotationPresent(PreAuthorize.class)) {
            throw new AuthorizeAspectException("@Authorize must be use with @PreAuthorize and hasAnyAuthority()");
        }
    }

    private void validateHasAnyAuthorityMethodIsPresentOrThrow(String rolesExpression) throws AuthorizeAspectException {
        if (!rolesExpression.startsWith("hasAnyAuthority(")) {
            throw new AuthorizeAspectException("@Authorize must be use with @PreAuthorize and hasAnyAuthority()");
        }
    }

    private List<RoleOnContext> extractRoles(String rolesExpression) {
        logger.debug("Extracting required roles for method");

        String rolesContent = rolesExpression
                .trim()
                .substring("hasAnyAuthority(".length(), rolesExpression.length() - 1)
                .replace("'", "")
                .replace("{", "")
                .replace("}", "")
                .trim();

        String[] rolesArray = rolesContent.split(",\\s*");

        return Arrays.stream(rolesArray)
                .map(RoleOnContext::valueOf)
                .toList();
    }

    private boolean isUserOnContextAdmin() throws AuthorizeAspectException {
        List<RoleOnContext> rolesOnContexts = SecurityContextUtils.maybeRolesOnContext()
                .orElseThrow(() -> new AuthorizeAspectException("UserOnContext must be present for @Authorize"));

        return rolesOnContexts.contains(RoleOnContext.ADMIN);
    }

    private UUID userOnContextUUID() {
        return SecurityContextUtils.maybeUserUuid()
                .orElseThrow(() -> new AuthorizeAspectException("UserOnContext must be present for @Authorize"));
    }

    private void authorizeUser(boolean isUserOnContextAdmin, boolean isAdminAllowed, UUID userUuid,
                               UUID userOnContextUuid) throws ForbiddenException {

        if (isUserOnContextAdmin) {
            validateAdminAccess(isAdminAllowed, userUuid, userOnContextUuid);
        } else {
            validateRegularUserAccess(userUuid, userOnContextUuid);
        }
    }

    private void validateAdminAccess(boolean isAdminAllowed, UUID userUuid, UUID userOnContextUuid)
            throws ForbiddenException {
        if (!isAdminAllowed && !userUuid.equals(userOnContextUuid)) {
            logger.error("Access denied for user with UUID {}: insufficient privileges. Admins" +
                    " can only act on their own account.", userOnContextUuid);
            throw new ForbiddenException("Access Denied");
        }
    }

    private void validateRegularUserAccess(UUID userUuid, UUID userOnContextUuid) throws ForbiddenException {
        if (!userUuid.equals(userOnContextUuid)) {
            logger.error("Access denied for user with UUID {}: insufficient privileges. " +
                    "Attempted to perform actions on another user with UUID {}.", userOnContextUuid, userUuid);
            throw new ForbiddenException("Access Denied");
        }
    }

}
