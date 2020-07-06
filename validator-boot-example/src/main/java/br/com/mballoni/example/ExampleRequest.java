package br.com.mballoni.example;

import br.com.mballoni.example.validation.ExampleAssignmentNoOverlap;
import br.com.mballoni.example.validation.NoDuplicatedEmail;
import java.util.List;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@ExampleAssignmentNoOverlap(
    groups = {ExampleRequest.UpdateExample.class, ExampleRequest.CreateExample.class})
public class ExampleRequest {

  @NotNull(groups = {UpdateExample.class})
  @Min(
      value = 10,
      groups = {UpdateExample.class})
  @Max(
      value = 20,
      groups = {UpdateExample.class})
  @Null(groups = {CreateExample.class})
  private Long id;

  @NotBlank(groups = {UpdateExample.class, CreateExample.class})
  private String name;

  @NoDuplicatedEmail(groups = {CreateExample.class})
  private String email;

  private List<Assignment> assignments;

  public interface CreateExample {}

  public interface UpdateExample {}
}
