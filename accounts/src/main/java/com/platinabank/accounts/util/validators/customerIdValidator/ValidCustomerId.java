package com.platinabank.accounts.util.validators.customerIdValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = CustomerIdValidator.class)
public @interface ValidCustomerId {
    String message() default "Invalid customer id. Must be of 6 digits";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
