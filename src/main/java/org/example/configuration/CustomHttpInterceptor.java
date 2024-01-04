package org.example.configuration;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

public class CustomHttpInterceptor implements HandlerInterceptor {
    protected static final Logger logger = LoggerFactory.getLogger(CustomHttpInterceptor.class);

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        logger.info("REQUEST {}, ENDPOINT {}", request.getMethod(), request.getRequestURI());
    }


    @Override
    public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response,
                                final Object handler, final Exception ex) {
        logger.info("SERVICE RESPONSE {}, TRANSACTION ID {}", response.getStatus(), UUID.randomUUID());
    }
}