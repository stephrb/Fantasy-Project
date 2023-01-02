package fba;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ConnectionTest {

    @Autowired
    private Connection connection;

    @Test
    public void testConnection() throws Exception {
        String sql = "INSERT INTO Errors (league_id, user_id, error_message, error_stacktrace) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, 1);
            stmt.setInt(2, 1);
            stmt.setString(3, "test");
            stmt.setString(4, "test2");
            stmt.executeUpdate();
        }
    }

}