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
        String sql = "INSERT INTO Errors (error_message, error_stacktrace) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "test");
            stmt.setString(2, "test2");
            stmt.executeUpdate();
        }
    }

    @Test
    public void numUsersTest() throws Exception {
        String sql = "SELECT COUNT(DISTINCT user_id, league_id) FROM Logs";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int count = rs.getInt(1);
                System.out.println("Count: " + count);
            }
        }
    }

}