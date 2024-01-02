package org.example.configuration;

import org.apache.commons.io.IOUtils;
import org.example.service.TraineeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class CustomHttpInterceptor extends HandlerInterceptorAdapter {
    protected static final Logger logger = LoggerFactory.getLogger(CustomHttpInterceptor.class);

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response,
                             final Object handler)
            throws Exception {

        logger.info(request.toString());
//        logger.info(request.getPathInfo());
//        System.out.println(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
//        IOUtils.toString(request.getInputStream());
//        String requestBody = IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8);
//        System.out.println(requestBody);


        logger.info(request.getRequestURI());
        System.out.println("hello");
        return super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response,
                                final Object handler, final Exception ex) {
        // Logs here
        logger.info(response.getStatus() + "");
        System.out.println("hello");

    }
}
