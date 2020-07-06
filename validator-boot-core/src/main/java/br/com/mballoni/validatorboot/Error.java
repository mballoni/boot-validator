package br.com.mballoni.validatorboot;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Error implements Serializable {
  private String field;
  private String code;
  private String message;
  private Object rejectedValue;
}
