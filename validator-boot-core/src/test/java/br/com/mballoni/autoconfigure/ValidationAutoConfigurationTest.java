package br.com.mballoni.autoconfigure;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.mballoni.validatorboot.BeanValidation;
import br.com.mballoni.validatorboot.ValidationErrorAdvice;
import javax.validation.Validation;
import javax.validation.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class ValidationAutoConfigurationTest {

  private final ApplicationContextRunner contextRunner =
      new ApplicationContextRunner()
          .withConfiguration(AutoConfigurations.of(ValidationAutoConfiguration.class));

  @Test
  @DisplayName("When setting up without customizations should setup the default beans")
  void setupDefaultBeans() {
    contextRunner
        .withUserConfiguration(DefaultConfiguration.class)
        .run(
            (context) -> {
              assertThat(context).hasSingleBean(BeanValidation.class);
              assertThat(context).hasSingleBean(ValidationErrorAdvice.class);
            });
  }

  @Configuration
  static class DefaultConfiguration {

    @Bean
    public Validator validator() {
      return Validation.buildDefaultValidatorFactory().getValidator();
    }
  }
}
