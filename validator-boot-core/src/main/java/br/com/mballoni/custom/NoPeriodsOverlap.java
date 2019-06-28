package br.com.mballoni.custom;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD, METHOD, PARAMETER, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = {NoPeriodsOverlapValidator.class})
@Documented
public @interface NoPeriodsOverlap {

    String message() default "{br.com.mballoni.custom.NoPeriodsOverlap.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
