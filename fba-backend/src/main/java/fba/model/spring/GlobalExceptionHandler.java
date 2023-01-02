package fba.model.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.Connection;

@ControllerAdvice
public class GlobalExceptionHandler {
    @Autowired
    private Connection connection;
    @ExceptionHandler(Exception.class)
    public void handleException(Exception e) {
        StringBuilder stackTrace = new StringBuilder();
        StackTraceElement[] elements = e.getStackTrace();
        for (StackTraceElement element : elements) {
            stackTrace.append(element.toString());
            stackTrace.append("\n");
        }
        String stackTraceString = stackTrace.toString();

        LogService.logError(e.getMessage(), stackTraceString, connection);
    }
}
