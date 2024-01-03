package org.example.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "org.springdoc")
@Import({ org.springdoc.core.SpringDocConfiguration.class,
        org.springdoc.core.SpringDocWebMvcConfiguration.class,
        org.springdoc.ui.SwaggerConfig.class,
        org.springdoc.core.SwaggerUiConfigProperties.class,
        org.springdoc.core.SwaggerUiOAuthProperties.class,
        org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration.class
})
public class OpenApiConfig implements WebMvcConfigurer {

}