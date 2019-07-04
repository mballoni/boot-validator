package br.com.mballoni.validatorboot.custom;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

import static org.springframework.util.CollectionUtils.isEmpty;

public class NoPeriodsOverlapValidator implements ConstraintValidator<NoPeriodsOverlap, List<? extends PeriodHolder>> {

    @Override
    public void initialize(NoPeriodsOverlap constraintAnnotation) {
    }

    @Override
    public boolean isValid(List<? extends PeriodHolder> value, ConstraintValidatorContext context) {
        if (isEmpty(value)) {
            return true;
        }
        context.disableDefaultConstraintViolation();

        context
                .buildConstraintViolationWithTemplate("{br.com.mballoni.validatorboot.custom.NoPeriodsOverlap.message}")
                .addPropertyNode("period").inIterable().atIndex(0)
                .addConstraintViolation()

                .buildConstraintViolationWithTemplate("{br.com.mballoni.validatorboot.custom.NoPeriodsOverlap.message}")
                .addPropertyNode("period").inIterable().atIndex(1)
                .addConstraintViolation();

        return false;
    }
}
