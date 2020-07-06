package br.com.mballoni.example;

import br.com.mballoni.example.ExampleRequest.CreateExample;
import br.com.mballoni.example.ExampleRequest.UpdateExample;
import br.com.mballoni.validatorboot.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ExampleController {

  @Autowired private Validator validator;

  @PostMapping(value = "/example", consumes = MediaType.APPLICATION_JSON_VALUE)
  public void create(@RequestBody @Validated(CreateExample.class) ExampleRequest request) {
    log.info("CREATE: {}", request);
  }

  @PutMapping(value = "/example", consumes = MediaType.APPLICATION_JSON_VALUE)
  public void update(@RequestBody @Validated(UpdateExample.class) ExampleRequest request) {
    log.info("UPDATE: {}", request);
  }

  @PostMapping(value = "/exampleBeanValidation", consumes = MediaType.APPLICATION_JSON_VALUE)
  public void createBean(@RequestBody ExampleRequest request) {
    validator.validate(request, CreateExample.class);
    log.info("CREATE: {}", request);
  }
}
