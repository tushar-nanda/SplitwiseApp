package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/splitwise_single_group";
    private static final String USER = "root";
    private static final String PASSWORD = "Sachin@1234";

    public static Connection getConnection() {
        try {
            // Load the JDBC driver explicitly (optional but recommended)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Add connection properties to prevent timeout
            String urlWithProperties = URL +
                    "?autoReconnect=true" +
                    "&useSSL=false" +
                    "&allowPublicKeyRetrieval=true";

//            return DriverManager.getConnection(urlWithProperties, USER, PASSWORD);
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Error connecting to database: " + e.getMessage());
            throw new RuntimeException("Database connection failed", e);
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver not found: " + e.getMessage());
            throw new RuntimeException("JDBC Driver not found", e);
        }
    }
}