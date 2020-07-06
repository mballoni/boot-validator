package br.com.mballoni.example;

import static org.hamcrest.Matchers.contains;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.mballoni.example.validation.NoDuplicatedEmailValidator;
import br.com.mballoni.validatorboot.ValidationErrorAdvice;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@Disabled
@ExtendWith(MockitoExtension.class)
public class NoSpringExampleControllerTest {

  private MockMvc mockMvc;

  private ObjectMapper objectMapper;

  @Mock private JdbcTemplate jdbcTemplate;

  @InjectMocks private NoDuplicatedEmailValidator noDuplicatedEmailValidator;

  @BeforeEach
  public void setUp() {
    objectMapper = new ObjectMapper();
    objectMapper.findAndRegisterModules();

    mockMvc =
        MockMvcBuilders.standaloneSetup(new ExampleController())
            .setControllerAdvice(new ValidationErrorAdvice())
            .build();
  }

  @Test
  @DisplayName("should apply basic validation")
  public void validate_basic_input_data() throws Exception {
    ExampleRequest request = new ExampleRequest();
    request.setEmail("me@abacate.com");
    request.setName("");
    request.setId(10L);

    when(jdbcTemplate.query(anyString(), any(Object[].class), any(RowMapper.class)))
        .thenReturn(List.of(1));

    this.mockMvc
        .perform(
            post("/example")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errors[?(@.field=='email')].code", contains("NoDuplicatedEmail")))
        .andExpect(jsonPath("$.errors[?(@.field=='id')].code", contains("Null")))
        .andExpect(jsonPath("$.errors[?(@.field=='name')].code", contains("NotBlank")));
  }
}
