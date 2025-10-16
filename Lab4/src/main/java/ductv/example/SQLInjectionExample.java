package ductv.example;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SQLInjectionExample {
    private static final Logger logger = Logger.getLogger(SQLInjectionExample.class.getName());

    public static void main(String[] args) {
        String userInput = (args != null && args.length > 0) ? args[0] : null;
        if (userInput == null) {
            logger.warning("Missing username parameter");
            return;
        }
        if (userInput.length() > 100) {
            logger.warning("Username parameter too long");
            return;
        }

        String dbUrl = System.getenv("DB_URL");
        String dbUser = System.getenv("DB_USER");
        String dbPass = System.getenv("DB_PASS");

        if (dbUrl == null || dbUser == null || dbPass == null) {
            logger.severe("Database connection info not set in environment variables");
            return;
        }

        String sql = "SELECT id, username, email FROM users WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, userInput);

            logger.log(Level.INFO, () -> "Executing prepared statement for username (length=" + userInput.length() + ")");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String username = rs.getString("username");
                    logger.log(Level.INFO, () -> "Found user id=" + id + ", username=" + username);
                }
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Database error while querying users", e);
        }
    }
}
