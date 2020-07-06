package br.com.mballoni.example.validation;

import static org.springframework.util.CollectionUtils.isEmpty;

import br.com.mballoni.example.ExampleRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ExampleAssignmentNoOverlapValidator
    implements ConstraintValidator<ExampleAssignmentNoOverlap, ExampleRequest> {

  @Override
  public boolean isValid(ExampleRequest value, ConstraintValidatorContext context) {
    if (isEmpty(value.getAssignments())) {
      return true;
    }
    context.disableDefaultConstraintViolation();

    context
        .buildConstraintViolationWithTemplate(
            "{br.com.mballoni.example.validation.ExampleAssignmentNoOverlap.message}")
        .addPropertyNode("assignments[0].period")
        .addConstraintViolation();

    return false;
  }
}
