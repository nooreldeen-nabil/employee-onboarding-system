package com.example.employeeonboarding.config;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RequestLoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        System.out.println("Request received: " + req.getMethod() + " " + req.getRequestURI());
        System.out.println("Remote Address: " + req.getRemoteAddr());
        System.out.println("Authorization Header: " + req.getHeader("Authorization"));
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("RequestLoggingFilter initialized");
    }

    @Override
    public void destroy() {
        System.out.println("RequestLoggingFilter destroyed");
    }
}