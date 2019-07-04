package br.com.mballoni.example;

import br.com.mballoni.validatorboot.ValidationErrorAdvice;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfiguration {
    @Bean
    public ValidationErrorAdvice validationErrorAdvice() {
        return new ValidationErrorAdvice();
    }
}
