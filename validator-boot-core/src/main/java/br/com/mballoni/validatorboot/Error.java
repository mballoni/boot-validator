package br.com.mballoni.validatorboot;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@EqualsAndHashCode
public class Error {
    private String field;
    private String code;
    private String message;
    private Object rejectedValue;
}
