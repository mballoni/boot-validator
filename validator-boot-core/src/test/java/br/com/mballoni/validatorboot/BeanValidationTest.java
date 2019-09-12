package br.com.mballoni.validatorboot;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import java.util.List;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;

public class BeanValidationTest {

    private BeanValidation sut;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        sut = new BeanValidation(validator);
    }

    @Test
    public void applies_validation_to_default_group() {
        Subject subject = new Subject();

        thrown.expect(ValidationException.class);
        thrown.expect(hasProperty("errors", is(getErrorList("id", "NotNull", "Should not be null :(", null))));

        sut.validate(subject);
    }

    @Test
    public void applies_validation_to_SPECIFIED_group() {
        Subject subject = new Subject();

        thrown.expect(ValidationException.class);
        thrown.expect(hasProperty("errors", is(getErrorList("anotherField", "NotNull", "Oh noh!", null))));

        sut.validate(subject, Subject.Group1.class);
    }

    private List<Error> getErrorList(String field, String code, String message, Object rejectedValue) {
        return List.of(new Error(field, code, message, rejectedValue));
    }

    static class Subject {

        @NotNull(message = "Should not be null :(")
        private String id;

        @NotNull(groups = Group1.class, message = "Oh noh!")
        private String anotherField;


        public interface Group1 {
        }
    }
}
