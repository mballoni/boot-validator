package br.com.mballoni.autoconfigure;

import br.com.mballoni.validatorboot.BeanValidation;
import br.com.mballoni.validatorboot.ValidationException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.validation.FieldError;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Validator;
import java.util.Map;

public class ValidationAutoConfiguration {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer addFieldErrorMixIn() {
        return jacksonObjectMapperBuilder -> jacksonObjectMapperBuilder.mixIn(FieldError.class, FieldErrorMixIn.class);
    }

    public interface FieldErrorMixIn {
        @JsonIgnore
        String[] getCodes();

        @JsonIgnore
        String getObjectName();

        @JsonIgnore
        Object[] getArguments();

        @JsonIgnore
        boolean isBindingFailure();

        @JsonProperty("message")
        String getDefaultMessage();
    }

    @Bean
    @Primary
    public DefaultErrorAttributes defaultErrorAttributes() {
        return new DefaultErrorAttributes() {
            @Override
            public Map<String, Object> getErrorAttributes(WebRequest request, boolean includeStackTrace) {

                Map<String, Object> errorAttributes = super.getErrorAttributes(request, includeStackTrace);
                errorAttributes.remove("exception");
                errorAttributes.remove("message");
                errorAttributes.remove("path");
                errorAttributes.remove("status");
                errorAttributes.remove("error");
                errorAttributes.remove("timestamp");

                Throwable exception = super.getError(request);
                if (exception instanceof ValidationException) {
                    ValidationException validationException = (ValidationException) exception;
                    if (!validationException.getErrors().isEmpty()) {
                        errorAttributes.put("errors", validationException.getErrors());
                    }
                }

                return errorAttributes;
            }

        };
    }

    @Bean
    public BeanValidation beanValidation(Validator validator) {
        return new BeanValidation(validator);
    }
}
