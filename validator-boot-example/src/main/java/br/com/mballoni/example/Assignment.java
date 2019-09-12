package br.com.mballoni.example;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Period;

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
