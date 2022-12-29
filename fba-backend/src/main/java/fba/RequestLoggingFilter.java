package fba;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.Instant;

public class RequestLoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        System.out.printf("[%s] %s %s %s%n", req.getMethod(), req.getRequestURI(), req.getRemoteAddr(), Instant.now().toString());
        chain.doFilter(request, response);
    }

}