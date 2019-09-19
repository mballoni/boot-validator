package br.com.mballoni.validatorboot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@Slf4j
@RestControllerAdvice
public class ValidationErrorAdvice extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {

        Map<String, Object> body = new HashMap<>();

        List<Error> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(this::toError)
                .collect(toList());

        body.put("errors", errors);

        return new ResponseEntity<>(body, headers, status);
    }

    private Error toError(FieldError fieldError) {
        return new Error(
                fieldError.getField(),
                fieldError.getCode(),
                fieldError.getDefaultMessage(),
                fieldError.getRejectedValue()
        );
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handle(ValidationException ex) {
        Map<String, Object> body = new HashMap<>();

        body.put("errors", ex.getErrors());

        return new ResponseEntity<>(body, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
