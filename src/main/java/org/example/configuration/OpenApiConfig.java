package org.example.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Import;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//@EnableWebMvc
//@ComponentScan(basePackages = {"org.springdoc"})
//@Import({org.springdoc.core.SpringDocConfiguration.class,
//        org.springdoc.webmvc.core.SpringDocWebMvcConfiguration.class,
//        org.springdoc.webmvc.ui.SwaggerConfig.class,
//        org.springdoc.core.SwaggerUiConfigProperties.class,
//        org.springdoc.core.SwaggerUiOAuthProperties.class,
//        org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration.class})
//
//class OpenApiConfig implements WebMvcConfigurer {
//
//}
//import io.swagger.v3.oas.annotations.OpenAPIDefinition;
//import io.swagger.v3.oas.annotations.servers.Server;
//import io.swagger.v3.oas.annotations.info.Info;
//
//@OpenAPIDefinition(info = @Info(title = "My REST API", version = "1.2.6",
//        description = "My OpenAPIDefinition description"),
//        servers = { @Server(url = "/my-api", description = "Default URL")})
//public class OpenApiConfig { }
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
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        System.out.println("hello");
        System.out.println("hello");
        System.out.println("hello");
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        System.out.println("hello");
        System.out.println("hello");
        System.out.println("hello");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        System.out.println("hello");
        System.out.println("hello");
        System.out.println("hello");
    }
}