package br.com.mballoni;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.*;

@Getter
@Setter
@ToString
public class ExampleRequest {

    @NotNull
    @Min(value = 10)
    @Max(value = 20)
    private Long id;

    @NotNull
    @NotEmpty
    @NotBlank
    private String name;
}
