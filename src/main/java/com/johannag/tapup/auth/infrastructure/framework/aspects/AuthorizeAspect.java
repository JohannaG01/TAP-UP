package com.johannag.tapup.auth.infrastructure.framework.aspects;

import com.johannag.tapup.auth.infrastructure.framework.exceptions.AuthorizeAspectException;
import com.johannag.tapup.auth.infrastructure.framework.exceptions.ForbiddenException;
import com.johannag.tapup.auth.presentation.annotations.Authorize;
import com.johannag.tapup.globals.infrastructure.utils.Logger;
import com.johannag.tapup.auth.infrastructure.utils.SecurityContextUtils;
import com.johannag.tapup.users.infrastructure.framework.context.RoleOnContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.List;
import java.util.UUID;

@Aspect
@Component
public class AuthorizeAspect {

    private static final String USER_UUID_PARAM_NAME = "userUuid";
    private final Logger logger = Logger.getLogger(this.getClass());

    @Before("@annotation(com.johannag.tapup.auth.presentation.annotations.Authorize) && args(userUuid,..)")
    public void authorize(JoinPoint joinPoint, UUID userUuid) throws ForbiddenException, AuthorizeAspectException {

        logger.debug("Starting authorize process for user {}", userUuid);

        boolean isAdminAllowed = isAdminAllowed(joinPoint);
        UUID userOnContextUuid = userOnContextUUID();
        authorizeForAdminUser(isAdminAllowed, userUuid, userOnContextUuid);
        authorizeForRegularUser(userUuid, userOnContextUuid);

        logger.debug("Finished authorize process for user {}", userUuid);

    }

    private boolean isAdminAllowed(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Authorize authorize = method.getAnnotation(Authorize.class);
        return authorize.allowAdmin();
    }

    private UUID userOnContextUUID() {
        return SecurityContextUtils.maybeUserUuid()
                .orElseThrow(() -> new AuthorizeAspectException("UserOnContext must be present for @Authorize"));
    }

    private void authorizeForAdminUser(boolean isAdminAllowed, UUID userUuid, UUID userOnContextUuid) throws AuthorizeAspectException,
            ForbiddenException {
        if (!isAdminAllowed && isUserOnContextAdmin() && !userUuid.equals(userOnContextUuid)) {
            logger.error("Access denied for user with UUID {}: insufficient privileges. The user is an admin, but the" +
                    " current method only allows actions on their own account.", userOnContextUuid);
            throw new ForbiddenException("User does not have permission to access this resource");
        }
    }

    private void authorizeForRegularUser(UUID userUuid, UUID userOnContextUuid) throws AuthorizeAspectException,
            ForbiddenException {
        if (!isUserOnContextAdmin() && !userUuid.equals(userOnContextUuid)) {
            logger.error("Access denied for user with UUID {}: insufficient privileges. The user attempted to perform" +
                    " actions on another user with UUID {}.", userOnContextUuid, userUuid);
            throw new ForbiddenException("User does not have permission to access this resource");
        }
    }

    private boolean isUserOnContextAdmin() throws AuthorizeAspectException {
        List<RoleOnContext> rolesOnContexts = SecurityContextUtils.maybeRolesOnContext()
                .orElseThrow(() -> new AuthorizeAspectException("UserOnContext must be present for @Authorize"));

        return rolesOnContexts.contains(RoleOnContext.ADMIN);
    }

    /*
    @Before("@annotation(Authorize) && args(userUuid,..)")
    public void authorize(JoinPoint joinPoint, UUID userUuid) throws AuthorizeAspectException {

        Method method = obtainMethod(joinPoint);
        validateUserHasRequiredRolesOrThrow(method);
        Optional<String> maybeUserUuid =  maybeUserUuid(joinPoint, method);
        boolean isAdminAllowed = isAdminAllowed(method);

        handleAuthorization(userContext, requiredUserRole, maybeAccountId, maybeUserId);
    }

    private Method obtainMethod(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return signature.getMethod();
    }

    private void validateUserHasRequiredRolesOrThrow(Method method) throws AuthorizeAspectException {

        List<RoleOnContext> rolesOnContexts = SecurityContextUtils.maybeRolesOnContext()
                .orElseThrow(() -> new AuthorizeAspectException("UserOnContext must be present for @Authorize"));

        List<RoleOnContext> requiredRoles = requiredRole(method);

        if (rolesOnContexts.stream().noneMatch(requiredRoles::contains)) {
            logger.error("Access denied for user {}: insufficient privileges. User roles: {}, Required roles: {}",
                    SecurityContextUtils.getUserUuid(), rolesOnContexts, requiredRoles);
            throw new ForbiddenException("User has not enough privileges to accesses this resource");
        }
    }

    private List<RoleOnContext> requiredRole(Method method) {
        Authorize authorize = method.getAnnotation(Authorize.class);
        return Arrays.stream(authorize.roles()).toList();
    }

    private Optional<String> maybeUserUuid(JoinPoint joinPoint, Method method) {
        List<Parameter> parameters = Arrays.asList(method.getParameters());
        return parameters.stream()
                .filter(parameter -> parameter.getName().equals(AuthorizeAspect.USER_UUID_PARAM_NAME))
                .findFirst()
                .map(parameter -> joinPoint.getArgs()[parameters.indexOf(parameter)].toString());
    }

    private boolean isAdminAllowed(Method method) {
        Authorize authorize = method.getAnnotation(Authorize.class);
        return authorize.allowAdmin();
    }

    private void authorizedOrThrow(Optional<String> maybeUserUuid, boolean isAdminAllowed) throws ForbiddenException {

        if (isAdminAllowed && u) {}
    }

     */

}
