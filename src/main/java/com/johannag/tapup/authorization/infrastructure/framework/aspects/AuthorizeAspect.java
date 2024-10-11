package com.johannag.tapup.authorization.infrastructure.framework.aspects;

import com.johannag.tapup.authorization.infrastructure.framework.exceptions.AuthorizeAspectException;
import com.johannag.tapup.authorization.infrastructure.framework.exceptions.ForbiddenException;
import com.johannag.tapup.authorization.presentation.annotations.Authorize;
import com.johannag.tapup.globals.infrastructure.utils.Logger;
import com.johannag.tapup.globals.infrastructure.utils.SecurityContextUtils;
import com.johannag.tapup.users.infrastructure.framework.context.RoleOnContext;
import com.johannag.tapup.users.infrastructure.framework.context.UserOnContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class AuthorizeAspect {

    private static final String USER_UUID_PARAM_NAME = "userUuid";
    private final Logger logger = Logger.getLogger(this.getClass());

    @Before("@annotation(Authorize)")
    public void authorize(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        validateUserHasRequiredRolesOrThrow(method);

        Optional<String> maybeUserId = maybeUserId(joinPoint, method);

        handleAuthorization(userContext, requiredUserRole, maybeAccountId, maybeUserId);
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

    private Optional<String> maybeUserId(JoinPoint joinPoint, Method method) {
        Optional<String> maybeUserid = maybeParameter(joinPoint, method, USER_UUID_PARAM_NAME);
        boolean allowAdmin = allowAdmin(method);

        if (maybeUserid.isPresent() && allowAdmin) {
            throw new AuthorizeAspectException(
                    String.format(
                            "%s param not found but @Authorize annotation has been set with allowSelfUser",
                            USER_UUID_PARAM_NAME));
        }

        return allowOnlySelf ? maybeUserid : Optional.empty();
    }

    private boolean allowAdmin(Method method) {
        Authorize authorize = method.getAnnotation(Authorize.class);
        return authorize.allowAdmin();
    }

    private Optional<String> maybeParameter(JoinPoint joinPoint, Method method, String paramName) {
        List<Parameter> parameters = Arrays.asList(method.getParameters());
        return parameters.stream()
                .filter(parameter -> parameter.getName().equals(paramName))
                .findFirst()
                .map(parameter -> joinPoint.getArgs()[parameters.indexOf(parameter)].toString());
    }
}
