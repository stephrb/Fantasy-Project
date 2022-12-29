package fba;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class RequestLoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        System.out.println(String.format("[%s] %s %s", req.getMethod(), req.getRequestURI(), req.getRemoteAddr()));
        chain.doFilter(request, response);
    }

}