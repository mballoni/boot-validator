package br.com.mballoni.example.validation;

import org.eclipse.jetty.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class NoDuplicatedEmailValidator implements ConstraintValidator<NoDuplicatedEmail, String> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        return StringUtil.isBlank(value) ||
                jdbcTemplate.query("SELECT COUNT(1) FROM email WHERE address = ?",
                        new Object[]{value},
                        (rs, rowNum) -> rs.getInt(1)).get(0) == 0;
    }
}
