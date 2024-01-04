//package org.example;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.ServletRegistration;
//import org.example.configuration.BeanConfiguration;
//import org.springframework.web.WebApplicationInitializer;
//import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
//import org.springframework.web.servlet.DispatcherServlet;
//
//
//
//public class ApplicationInitializer implements WebApplicationInitializer {
//
//    @Override
//    public void onStartup(jakarta.servlet.ServletContext servletContext) throws ServletException {
//        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
//        ctx.getEnvironment().setActiveProfiles("jpa");
//        ctx.register(BeanConfiguration.class);
//        ServletRegistration.Dynamic servletRegistration =
//                servletContext.addServlet("mvc", new DispatcherServlet(ctx));
//
//        servletRegistration.setLoadOnStartup(1);
//        servletRegistration.addMapping("/");
//    }
//}
