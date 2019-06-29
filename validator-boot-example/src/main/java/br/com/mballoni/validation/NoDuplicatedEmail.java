package br.com.mballoni.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD, METHOD, PARAMETER, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = {NoDuplicatedEmailValidator.class})
@Documented
public @interface NoDuplicatedEmail {

    String message() default "{br.com.mballoni.validation.NoDuplicatedEmail.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
