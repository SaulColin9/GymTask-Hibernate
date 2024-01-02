package org.example.configuration;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class RequestCachingFilter extends OncePerRequestFilter {

    protected static final Logger logger = LoggerFactory.getLogger(RequestCachingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        CachedHttpServletRequest cachedHttpServletRequest = new CachedHttpServletRequest(request);
        logger.info("REQUEST DATA: " + IOUtils.toString(cachedHttpServletRequest.getInputStream(), StandardCharsets.UTF_8));
        filterChain.doFilter(cachedHttpServletRequest, response);
    }
}
