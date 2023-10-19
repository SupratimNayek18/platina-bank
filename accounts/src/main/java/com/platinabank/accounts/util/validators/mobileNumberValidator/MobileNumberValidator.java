package com.platinabank.accounts.util.validators.mobileNumberValidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MobileNumberValidator implements ConstraintValidator<ValidMobileNumber, Long> {

    @Override
    public boolean isValid(Long mobileNumber, ConstraintValidatorContext context) {
        return isValidMobileNumber(mobileNumber);
    }

    private boolean isValidMobileNumber(Long mobileNumber) {
        return mobileNumber != null && mobileNumber >= 1000000000L && mobileNumber <= 9999999999L;
    }
}
