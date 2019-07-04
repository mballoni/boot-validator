package br.com.mballoni.validatorboot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashMap;
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

        Map<String, Object> body = new LinkedHashMap<>();

        List<Error> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> new Error(
                                fieldError.getField(),
                                fieldError.getCode(),
                                fieldError.getDefaultMessage(),
                                fieldError.getRejectedValue()
                        )
                )
                .collect(toList());

        body.put("errors", errors);

        return new ResponseEntity<>(body, headers, status);
    }
}
