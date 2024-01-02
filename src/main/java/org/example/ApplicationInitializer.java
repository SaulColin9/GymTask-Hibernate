package org.example;

import org.example.configuration.BeanConfiguration;
import org.example.configuration.WebConfiguration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class ApplicationInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.getEnvironment().setActiveProfiles("jpa");
        ctx.register(BeanConfiguration.class);
        ServletRegistration.Dynamic servletRegistration =
                servletContext.addServlet("mvc", new DispatcherServlet(ctx));
        servletRegistration.setLoadOnStartup(1);
        servletRegistration.addMapping("/");

    }
}
