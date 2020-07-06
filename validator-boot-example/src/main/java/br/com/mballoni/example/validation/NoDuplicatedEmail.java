package br.com.mballoni.example.validation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({FIELD, METHOD, PARAMETER, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = {NoDuplicatedEmailValidator.class})
@Documented
public @interface NoDuplicatedEmail {

  String message() default "{br.com.mballoni.example.validation.NoDuplicatedEmail.message}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
