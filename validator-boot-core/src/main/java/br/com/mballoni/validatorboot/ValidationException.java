package br.com.mballoni.validatorboot;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Collections;
import java.util.List;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid Parameters")
public class ValidationException extends RuntimeException {

    private final List<Error> errors;

    public ValidationException(final List<Error> fieldsErrors) {
        this(null, null, fieldsErrors);
    }

    public ValidationException(String message, Throwable cause, final List<Error> fieldsErrors) {
        super(message, cause);
        this.errors = Collections.unmodifiableList(fieldsErrors);
    }

    @Override
    public String toString() {
        return StringUtils.join(errors, StringUtils.LF);
    }

    public List<Error> getErrors() {
        return Collections.unmodifiableList(errors);
    }
}
