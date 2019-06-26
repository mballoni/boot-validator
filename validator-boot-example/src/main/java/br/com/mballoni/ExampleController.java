package br.com.mballoni;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ExampleController {

    @PostMapping(value ="/doit",consumes = MediaType.APPLICATION_JSON_VALUE)
    public void doItNow(@RequestBody @Validated ExampleRequest request) {
        log.info("Request Object: {}", request);
    }
}
