package br.com.mballoni.validatorboot;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Error implements Serializable {
  private final String field;
  private final String code;
  private final String message;
  private final Object rejectedValue;
}
