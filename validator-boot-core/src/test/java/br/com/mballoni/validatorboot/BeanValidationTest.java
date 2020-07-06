package br.com.mballoni.validatorboot;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class BeanValidationTest {

  private BeanValidation sut;

  @Rule public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setUp() {
    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    sut = new BeanValidation(validator);
  }

  @Test
  public void applies_validation_to_default_group() {
    Subject emptySubject = new Subject();

    thrown.expect(ValidationException.class);
    thrown.expect(
        hasProperty(
            "errors",
            contains(
                allOf(
                    hasProperty("field", is("id")),
                    hasProperty("code", is("NotNull")),
                    hasProperty("message", is("Should not be null :(")),
                    hasProperty("rejectedValue", is(nullValue()))))));

    sut.validate(emptySubject);
  }

  @Test
  public void applies_validation_to_SPECIFIED_group() {
    Subject emptySubject = new Subject();

    thrown.expect(ValidationException.class);
    thrown.expect(
        hasProperty(
            "errors",
            contains(
                allOf(
                    hasProperty("field", is("anotherField")),
                    hasProperty("code", is("NotNull")),
                    hasProperty("message", is("Oh noh!")),
                    hasProperty("rejectedValue", is(nullValue()))))));

    sut.validate(emptySubject, Subject.Group1.class);
  }

  static class Subject {

    @NotNull(message = "Should not be null :(")
    private String id;

    @NotNull(groups = Group1.class, message = "Oh noh!")
    private String anotherField;

    public interface Group1 {}
  }
}
