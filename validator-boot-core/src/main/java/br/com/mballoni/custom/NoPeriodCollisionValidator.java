package br.com.mballoni.custom;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class NoPeriodCollisionValidator implements ConstraintValidator<NoPeriodCollision, List<? extends PeriodHolder>> {


    @Override
    public void initialize(NoPeriodCollision constraintAnnotation) {
    }

    @Override
    public boolean isValid(List<? extends PeriodHolder> value, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();

        context
                .buildConstraintViolationWithTemplate("{br.com.mballoni.custom.NoPeriodCollision.message}")
                .addPropertyNode("period").inIterable().atIndex(0)
                .addConstraintViolation()

                .buildConstraintViolationWithTemplate("{br.com.mballoni.custom.NoPeriodCollision.message}")
                .addPropertyNode("period").inIterable().atIndex(1)
                .addConstraintViolation();

        return false;
    }
}
