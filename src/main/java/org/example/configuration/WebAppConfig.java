//package org.example.configuration;
//
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Import;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//
//@Configuration
//@EnableWebMvc
//@ComponentScan(basePackages = {"org.springdoc"})
//@Import({ org.springdoc.core.SpringDocConfiguration.class,
//        org.springdoc.core.SpringDocWebMvcConfiguration.class,
//        org.springdoc.ui.SwaggerConfig.class,
//        org.springdoc.core.SwaggerUiConfigProperties.class,
//        org.springdoc.core.SwaggerUiOAuthProperties.class,
//        org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration.class
//})
//
//public class WebAppConfig extends WebMvcConfigurerAdapter {
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        System.out.println("hello");
//        System.out.println("hello");
//        System.out.println("hello");
//        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
//        System.out.println("hello");
//        System.out.println("hello");
//        System.out.println("hello");
//        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
//        System.out.println("hello");
//        System.out.println("hello");
//        System.out.println("hello");
//    }
//
//}