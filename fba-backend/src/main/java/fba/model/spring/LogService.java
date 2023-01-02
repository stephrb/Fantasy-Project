package fba.model.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.message.callback.PrivateKeyCallback;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Service
public class LogService {

    public static void logRequest(HttpServletRequest request, HttpServletResponse response, Connection connection) {

        // Extract request information
        String requestMethod = request.getMethod();
        if (requestMethod.equals("OPTIONS")) {
            return;
        }
        String requestUri = request.getRequestURI();
        String leagueId = request.getHeader("leagueId");
        String userId = request.getHeader("userId");
        Enumeration<String> requestHeaderNames = request.getHeaderNames();
        Map<String, String> requestHeaders = new HashMap<>();
        while (requestHeaderNames.hasMoreElements()) {
            String headerName = requestHeaderNames.nextElement();
            requestHeaders.put(headerName, request.getHeader(headerName));
        }

        // Extract response information
        int responseStatusCode = response.getStatus();
        Collection<String> responseHeaderNames = response.getHeaderNames();
        Map<String, String> responseHeaders = new HashMap<>();
        for (String headerName : responseHeaderNames) {
            responseHeaders.put(headerName, response.getHeader(headerName));
        }

        // Create prepared statement
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement("INSERT INTO Logs (request_method, request_uri, league_id, user_id, request_headers, response_status_code, response_headers) VALUES (?, ?, ?, ?, ?, ?, ?)");

            // Set values for prepared statement
            stmt.setString(1, requestMethod);
            stmt.setString(2, requestUri);
            stmt.setString(3, leagueId);
            stmt.setString(4, userId);
            stmt.setString(5, requestHeaders.toString());
            stmt.setInt(6, responseStatusCode);
            stmt.setString(7, responseHeaders.toString());

            // Execute prepared statement
            stmt.executeUpdate();
        } catch (SQLException e) {
            // Handle database errors
            System.out.println("ERROR: " + e);
        } finally {
            // Close prepared statement
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    // Ignore
                }
            }
        }
    }

    public static void logError(String errorMessage, String stackTrace, Connection connection) {

        String sql = "INSERT INTO Errors (error_message, error_stacktrace) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, errorMessage);
            statement.setString(2, stackTrace);
            statement.executeUpdate();
        } catch (SQLException e) {
            // handle the exception
            System.out.println("ERROR: " + e);
        }
    }

    private String getRequestBody(HttpServletRequest request) {
        // code to read the request body from the request object
        return null;
    }

    private String getResponseBody(HttpServletResponse response) {
        // code to read the response body from the response object
        return null;
    }
}
