package com.johannag.tapup.globals.presentation.validations.annotations;

import com.johannag.tapup.globals.presentation.validations.validator.NullOrNotBlankConstraint;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Retention(RUNTIME)
@Target({ElementType.FIELD})
@Constraint(validatedBy = NullOrNotBlankConstraint.class)
public @interface NullOrNotBlank {
    String message() default "Field cannot be blank";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
