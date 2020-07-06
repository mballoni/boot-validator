package br.com.mballoni.example;

import br.com.mballoni.autoconfigure.ValidationAutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
@ImportAutoConfiguration(ValidationAutoConfiguration.class)
public class TestConfiguration {}
