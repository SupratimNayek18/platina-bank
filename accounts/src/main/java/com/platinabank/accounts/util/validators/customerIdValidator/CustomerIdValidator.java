package com.platinabank.accounts.util.validators.customerIdValidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CustomerIdValidator implements ConstraintValidator<ValidCustomerId, Integer> {

    @Override
    public boolean isValid(Integer customerId, ConstraintValidatorContext context) {
        return isValidCustomerId(customerId);
    }

    private boolean isValidCustomerId(Integer customerId) {
        return customerId != null && customerId >= 100000 && customerId <= 999999;
    }

}
