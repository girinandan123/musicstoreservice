package com.cx.uioc.dto.validator;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * ItemURL Interface for URL in list validation annotation
 */
@Constraint(validatedBy = ItemURLValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface ItemURL {
    String message() default "item in list requires be URL string";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
