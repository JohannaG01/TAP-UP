package com.johannag.tapup.authorization.presentation.annotations;

import com.johannag.tapup.users.infrastructure.framework.context.RoleOnContext;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Authorize {
    RoleOnContext[] roles() default {RoleOnContext.ADMIN};
    boolean allowAdmin() default true;
}
