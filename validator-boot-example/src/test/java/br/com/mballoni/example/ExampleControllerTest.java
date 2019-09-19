package br.com.mballoni.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(
        useDefaultFilters = false,
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = RestController.class),
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = TestConfiguration.class)
        })
public class ExampleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JdbcTemplate jdbcTemplate;

    @Test
    public void validate_basic_input_data() throws Exception {
        ExampleRequest request = new ExampleRequest();
        request.setEmail("me@abacate.com");
        request.setName("");
        request.setId(10L);

        when(jdbcTemplate.query(anyString(), any(Object[].class), any(RowMapper.class)))
                .thenReturn(List.of(1));


        this.mockMvc.perform(
                post("/example")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request))
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[?(@.field=='email')].code", contains("NoDuplicatedEmail")))
                .andExpect(jsonPath("$.errors[?(@.field=='id')].code", contains("Null")))
                .andExpect(jsonPath("$.errors[?(@.field=='name')].code", contains("NotBlank")));
    }
}
