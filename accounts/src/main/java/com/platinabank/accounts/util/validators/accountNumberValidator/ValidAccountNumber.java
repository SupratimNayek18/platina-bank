package com.platinabank.accounts.util.validators.accountNumberValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = AccountNumberValidator.class)
public @interface ValidAccountNumber {
    String message() default "Invalid account number. Must be of 12 digits";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
