//package org.example.configuration;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.UUID;
//
//public class CustomHttpInterceptor extends HandlerInterceptorAdapter {
//    protected static final Logger logger = LoggerFactory.getLogger(CustomHttpInterceptor.class);
//
//    @Override
//    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response,
//                             final Object handler)
//            throws Exception {
//        logger.info("REQUEST {}, ENDPOINT {}", request.getMethod(), request.getRequestURI());
//        return super.preHandle(request, response, handler);
//    }
//
//    @Override
//    public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response,
//                                final Object handler, final Exception ex) {
//        logger.info("SERVICE RESPONSE {}, TRANSACTION ID {}", response.getStatus(), UUID.randomUUID());
//    }
//}