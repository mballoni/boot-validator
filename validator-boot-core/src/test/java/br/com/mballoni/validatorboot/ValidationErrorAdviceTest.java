package br.com.mballoni.validatorboot;

import br.com.mballoni.autoconfigure.ValidationAutoConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static org.hamcrest.Matchers.contains;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@ImportAutoConfiguration(ValidationAutoConfiguration.class)
@WebMvcTest(controllers = ValidationErrorAdviceTest.TestController.class)
public class ValidationErrorAdviceTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void applies_validation_and_translates_default_group() throws Exception {
        TestRequest request = new TestRequest();
        request.setName("");
        request.setId(9L);

        this.mockMvc.perform(
                post("/test")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request))
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[?(@.field=='id')].code", contains("Min")))
                .andExpect(jsonPath("$.errors[?(@.field=='id')].rejectedValue", contains(9)))
                .andExpect(jsonPath("$.errors[?(@.field=='id')].message", contains("Should be at least 10")))
                .andExpect(jsonPath("$.errors[?(@.field=='name')].code", contains("NotBlank")))
                .andExpect(jsonPath("$.errors[?(@.field=='name')].rejectedValue", contains("")))
                .andExpect(jsonPath("$.errors[?(@.field=='name')].message", contains("Need a name!")));
    }


    @RestController
    public static class TestController {
        private Logger log = LoggerFactory.getLogger(TestController.class);

        @PostMapping(value = "/test", consumes = MediaType.APPLICATION_JSON_VALUE)
        public void create(@RequestBody @Validated TestRequest request) {
            log.info("CREATE: {}", request);
        }
    }

    @Getter
    @Setter
    public static class TestRequest {

        @NotNull
        @Min(value = 10, message = "Should be at least 10")
        @Max(value = 20)
        private Long id;

        @NotBlank(message = "Need a name!")
        private String name;

    }

    @Configuration
    public static class TestConfiguration {

        @Bean
        public TestController testController() {
            return new TestController();
        }
    }
}
