package br.com.mballoni.validatorboot;

import static org.hamcrest.Matchers.contains;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.mballoni.autoconfigure.ValidationAutoConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@ImportAutoConfiguration(ValidationAutoConfiguration.class)
@WebMvcTest
class ValidationErrorAdviceTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @Test
  @DisplayName("should translate the validation exception with the error protocol")
  void applies_validation_and_translates_default_group() throws Exception {
    TestRequest request = new TestRequest(9L, "");

    this.mockMvc
        .perform(
            post("/test")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errors[?(@.field=='id')].code", contains("Min")))
        .andExpect(jsonPath("$.errors[?(@.field=='id')].rejectedValue", contains(9)))
        .andExpect(
            jsonPath("$.errors[?(@.field=='id')].message", contains("Should be at least 10")))
        .andExpect(jsonPath("$.errors[?(@.field=='name')].code", contains("NotBlank")))
        .andExpect(jsonPath("$.errors[?(@.field=='name')].rejectedValue", contains("")))
        .andExpect(jsonPath("$.errors[?(@.field=='name')].message", contains("Need a name!")));
  }

  @Test
  @DisplayName("should translate the validation exception irrespective on how it was thrown")
  void intercepts_ValidationException() throws Exception {
    TestRequest request = new TestRequest(9L, "");

    this.mockMvc
        .perform(
            post("/testValidationException")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isUnprocessableEntity())
        .andExpect(jsonPath("$.errors[?(@.field=='id')].code", contains("Min")))
        .andExpect(jsonPath("$.errors[?(@.field=='id')].rejectedValue", contains(9)))
        .andExpect(
            jsonPath("$.errors[?(@.field=='id')].message", contains("Should be at least 10")))
        .andExpect(jsonPath("$.errors[?(@.field=='name')].code", contains("NotBlank")))
        .andExpect(jsonPath("$.errors[?(@.field=='name')].rejectedValue", contains("")))
        .andExpect(jsonPath("$.errors[?(@.field=='name')].message", contains("Need a name!")));
  }

  @RestController
  public static class TestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    @PostMapping(value = "/test", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void create(@RequestBody @Validated TestRequest request) {
      LOGGER.info("CREATE: {}", request);
    }

    @PostMapping(value = "/testValidationException", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void get() {

      List<Error> errors =
          List.of(
              new Error("id", "Min", "Should be at least 10", 9),
              new Error("name", "NotBlank", "Need a name!", ""));

      throw new ValidationException(errors);
    }
  }

  @Getter
  @AllArgsConstructor
  public static class TestRequest {

    @NotNull
    @Min(value = 10, message = "Should be at least 10")
    @Max(value = 20)
    private final Long id;

    @NotBlank(message = "Need a name!")
    private final String name;
  }

  @Configuration
  public static class TestConfiguration {

    @Bean
    public TestController testController() {
      return new TestController();
    }
  }
}
