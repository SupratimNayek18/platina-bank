package com.platinabank.accounts.util.validators.accountNumberValidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AccountNumberValidator implements ConstraintValidator<ValidAccountNumber, Long> {

    @Override
    public boolean isValid(Long accountNumber, ConstraintValidatorContext context) {
        return isValidAccountNumber(accountNumber);
    }

    private boolean isValidAccountNumber(Long accountNumber) {
        return accountNumber != null && accountNumber >= 100000000000L && accountNumber <= 999999999999L;
    }
}
