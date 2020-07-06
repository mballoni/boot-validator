package br.com.mballoni.validatorboot;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;

public class BeanValidation implements Validator {

  private final javax.validation.Validator validator;

  public BeanValidation(javax.validation.Validator validator) {
    this.validator = validator;
  }

  @Override
  public <T> void validate(final T entity) {
    final Set<ConstraintViolation<T>> violations = validator.validate(entity);
    check(violations);
  }

  @Override
  public <T> void validate(final T entity, Class<?>... groups) {
    final Set<ConstraintViolation<T>> violations = validator.validate(entity, groups);
    check(violations);
  }

  private <T> void check(final Set<ConstraintViolation<T>> violations) {
    if (violations.isEmpty()) {
      return;
    }
    List<Error> errors = violations.stream().map(this::from).collect(toList());
    throw new ValidationException(errors);
  }

  private <T> Error from(ConstraintViolation<T> violation) {
    final String code =
        violation.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName();
    final Object rejected = violation.getInvalidValue();
    final String field = violation.getPropertyPath().toString();
    final String message = violation.getMessage();

    return new Error(field, code, message, rejected);
  }
}
