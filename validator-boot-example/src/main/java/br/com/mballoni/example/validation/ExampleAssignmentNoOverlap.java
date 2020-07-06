package br.com.mballoni.example.validation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = {ExampleAssignmentNoOverlapValidator.class})
@Documented
public @interface ExampleAssignmentNoOverlap {

  String message() default
      "{br.com.mballoni.example.validation.ExampleAssignmentNoOverlap.message}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
