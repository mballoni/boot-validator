package br.com.mballoni.autoconfigure;

import br.com.mballoni.validatorboot.BeanValidation;
import br.com.mballoni.validatorboot.ValidationErrorAdvice;
import javax.validation.Validator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@Import(org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration.class)
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
