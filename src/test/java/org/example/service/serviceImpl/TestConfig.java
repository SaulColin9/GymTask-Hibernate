package org.example.service.serviceImpl;

import org.example.configuration.BeanConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({BeanConfiguration.class})
public class TestConfig {
}
