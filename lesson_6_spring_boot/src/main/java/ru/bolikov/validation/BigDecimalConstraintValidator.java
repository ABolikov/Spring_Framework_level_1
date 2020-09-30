package ru.bolikov.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class BigDecimalConstraintValidator implements ConstraintValidator<NotBlankBigDecimal, BigDecimal> {

    public void initialize(NotBlankBigDecimal constraint) {
    }

    public boolean isValid(BigDecimal obj, ConstraintValidatorContext context) {
        if (obj != null) {
            return obj.compareTo(BigDecimal.ZERO) > 0;
        }
        return false;
    }
}
