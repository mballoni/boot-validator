package br.com.mballoni;

import br.com.mballoni.ExampleRequest.CreateExample;
import br.com.mballoni.ExampleRequest.UpdateExample;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ExampleController {

    @PostMapping(value = "/example", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void create(@RequestBody @Validated(CreateExample.class) ExampleRequest request) {
        log.info("CREATE: {}", request);
    }

    @PutMapping(value = "/example", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody @Validated(UpdateExample.class) ExampleRequest request) {
        log.info("UPDATE: {}", request);
    }
}
