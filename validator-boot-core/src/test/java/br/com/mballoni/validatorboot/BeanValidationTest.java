package br.com.mballoni.validatorboot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BeanValidationTest {

  private BeanValidation sut;

  @BeforeEach
  public void setUp() {
    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    sut = new BeanValidation(validator);
  }

  @Test
  @DisplayName("when not specifying group should be applied to default")
  public void appliesValidationToDefaultGroup() {
    Subject emptySubject = new Subject();

    ValidationException thrown =
        assertThrows(ValidationException.class, () -> sut.validate(emptySubject));

    assertThat(thrown.getErrors())
        .extracting("field", "code", "message", "rejectedValue")
        .containsExactlyInAnyOrder(tuple("id", "NotNull", "Should not be null :(", null));
  }

  @Test
  @DisplayName("should apply validation to specified group")
  public void appliesValidationToSpecifiedGroup() {
    Subject emptySubject = new Subject();

    ValidationException thrown =
        assertThrows(
            ValidationException.class, () -> sut.validate(emptySubject, Subject.Group1.class));

    assertThat(thrown.getErrors())
        .extracting("field", "code", "message", "rejectedValue")
        .containsExactlyInAnyOrder(tuple("anotherField", "NotNull", "Oh noh!", null));
  }

  static class Subject {

    @NotNull(message = "Should not be null :(")
    private String id;

    @NotNull(groups = Group1.class, message = "Oh noh!")
    private String anotherField;

    public interface Group1 {}
  }
}
