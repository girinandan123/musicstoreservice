package com.cx.uioc.dto.validator;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * ItemSize Interface for Size in list validation annotation
 */
@Constraint(validatedBy = ItemSizeValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface ItemSize {
    String message() default "The length of item in list is overflow";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    /** Max constraint */
    int max();
}
