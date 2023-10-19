package com.platinabank.loans.util.validator.mobileNumberValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = MobileNumberValidator.class)
public @interface ValidMobileNumber {
    String message() default "Invalid mobile number. Must be of 10 digits";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
