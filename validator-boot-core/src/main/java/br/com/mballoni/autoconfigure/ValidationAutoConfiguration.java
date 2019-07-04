package br.com.mballoni.autoconfigure;

import br.com.mballoni.validatorboot.BeanValidation;
import br.com.mballoni.validatorboot.ValidationErrorAdvice;
import org.springframework.context.annotation.Bean;

import javax.validation.Validator;

public class ValidationAutoConfiguration {

    @Bean
    public BeanValidation beanValidation(Validator validator) {
        return new BeanValidation(validator);
    }

    @Bean
    public ValidationErrorAdvice validationErrorAdvice() {
        return new ValidationErrorAdvice();
    }
}
