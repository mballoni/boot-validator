package br.com.mballoni;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.*;

@Getter
@Setter
@ToString
public class ExampleRequest {

    @NotNull(groups = {UpdateExample.class})
    @Min(value = 10, groups = {UpdateExample.class})
    @Max(value = 20, groups = {UpdateExample.class})
    @Null(groups = {CreateExample.class})
    private Long id;

    @NotNull(groups = {UpdateExample.class, CreateExample.class})
    @NotEmpty(groups = {UpdateExample.class, CreateExample.class})
    @NotBlank(groups = {UpdateExample.class, CreateExample.class})
    private String name;


    public interface CreateExample {
    }

    public interface UpdateExample {
    }
}