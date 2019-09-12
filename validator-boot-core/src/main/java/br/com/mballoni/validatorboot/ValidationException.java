package br.com.mballoni.validatorboot;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Collections;
import java.util.List;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid Parameters")
public class ValidationException extends RuntimeException {

    private final List<Error> errors;

    public ValidationException(final List<Error> fieldsErrors) {
        this.errors = fieldsErrors;
    }

    public List<Error> getErrors() {
        return Collections.unmodifiableList(errors);
    }
}
