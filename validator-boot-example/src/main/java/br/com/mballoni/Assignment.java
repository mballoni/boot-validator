package br.com.mballoni;

import br.com.mballoni.custom.PeriodHolder;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Period;

@Getter
@Setter
public class Assignment implements PeriodHolder {

    private LocalDate start;
    private LocalDate end;

    @Override
    @JsonIgnore
    public Period getPeriod() {
        return Period.between(start, end);
    }
}
