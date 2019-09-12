package br.com.mballoni.validatorboot;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class Error implements Serializable {
    private String field;
    private String code;
    private String message;
    private Object rejectedValue;
}
