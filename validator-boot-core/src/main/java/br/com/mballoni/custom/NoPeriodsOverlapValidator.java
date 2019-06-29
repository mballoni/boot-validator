package br.com.mballoni.custom;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class NoPeriodsOverlapValidator implements ConstraintValidator<NoPeriodsOverlap, List<? extends PeriodHolder>> {

    @Override
    public void initialize(NoPeriodsOverlap constraintAnnotation) {
    }

    @Override
    public boolean isValid(List<? extends PeriodHolder> value, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();

        context
                .buildConstraintViolationWithTemplate("{br.com.mballoni.custom.NoPeriodsOverlap.message}")
                .addPropertyNode("period").inIterable().atIndex(0)
                .addConstraintViolation()

                .buildConstraintViolationWithTemplate("{br.com.mballoni.custom.NoPeriodsOverlap.message}")
                .addPropertyNode("period").inIterable().atIndex(1)
                .addConstraintViolation();

        return false;
    }
}
