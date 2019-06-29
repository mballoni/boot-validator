package br.com.mballoni;

import br.com.mballoni.custom.NoPeriodsOverlap;
import br.com.mballoni.validation.ExampleAssignmentNoOverlap;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.*;
import java.util.List;

@Getter
@Setter
@ToString
@ExampleAssignmentNoOverlap(groups = {ExampleRequest.UpdateExample.class, ExampleRequest.CreateExample.class})
public class ExampleRequest {

    @NotNull(groups = {UpdateExample.class})
    @Min(value = 10, groups = {UpdateExample.class})
    @Max(value = 20, groups = {UpdateExample.class})
    @Null(groups = {CreateExample.class})
    private Long id;

    @NotNull(groups = {CreateExample.class})
    @NotEmpty(groups = {UpdateExample.class, CreateExample.class})
    @NotBlank(groups = {UpdateExample.class, CreateExample.class})
    private String name;

    @NoPeriodsOverlap(groups = {UpdateExample.class, CreateExample.class})
    private List<Assignment> assignments;


    public interface CreateExample {
    }

    public interface UpdateExample {
    }
}