package br.com.mballoni.validation;

import br.com.mballoni.ExampleRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ExampleAssignmentNoOverlapValidator implements ConstraintValidator<ExampleAssignmentNoOverlap, ExampleRequest> {

    @Override
    public boolean isValid(ExampleRequest value, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();

        context
                .buildConstraintViolationWithTemplate("{br.com.mballoni.validation.ExampleAssignmentNoOverlap.message}")
                .addPropertyNode("assignments[0].period")
                .addConstraintViolation();

        return false;
    }
}
