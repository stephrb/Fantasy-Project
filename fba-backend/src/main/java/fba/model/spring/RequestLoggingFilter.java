package fba.model.spring;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.time.Instant;

public class RequestLoggingFilter implements Filter {

    private final Connection connection;

    public RequestLoggingFilter(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        LogService.logRequest((HttpServletRequest) request, (HttpServletResponse) response, connection);
        chain.doFilter(request, response);

    }

}