package org.example.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@EnableWebMvc
public class WebConfiguration extends WebMvcConfigurationSupport {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        System.out.println("helllo");
        System.out.println("helllo");
        System.out.println("helllo");
        System.out.println("helllo");
        System.out.println("helllo");
        System.out.println("helllo");
        System.out.println("helllo");
        System.out.println("helllo");
        System.out.println("helllo");
        System.out.println("helllo");
        System.out.println("helllo");
        System.out.println("helllo");
        System.out.println("helllo");
        System.out.println("helllo");
        System.out.println("helllo");
        System.out.println("helllo");
        registry.
                addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/")
                .resourceChain(false);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CustomHttpInterceptor()).addPathPatterns("/endpoints");
    }


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/swagger-ui/")
                .setViewName("forward:" + "/swagger-ui/index.html");
    }
}
