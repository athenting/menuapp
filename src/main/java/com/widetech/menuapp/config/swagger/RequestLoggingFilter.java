package com.widetech.menuapp.config.swagger;

/**
 * Author: athen
 * Date: 2/16/2024
 * Description:
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class RequestLoggingFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            String url = ((HttpServletRequest)request).getRequestURL().toString();
            String query = ((HttpServletRequest)request).getQueryString();
            logger.info("Incoming request: URL=[{}] query=[{}]", url, query);
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}