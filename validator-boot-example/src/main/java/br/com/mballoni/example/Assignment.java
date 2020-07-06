package br.com.mballoni.example;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import java.time.Period;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Assignment {

  private LocalDate start;
  private LocalDate end;

  @JsonIgnore
  public Period getPeriod() {
    return Period.between(start, end);
  }
}
