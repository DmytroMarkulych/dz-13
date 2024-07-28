package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseManager {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "username";
    private static final String PASSWORD = "password";

    private static Connection connection;

    private DataBaseManager() {
        // Private constructor to prevent instantiation
    }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return connection;
    }

    public static void closeConnection() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    public static void executeSqlScript(String filePath) {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }

            String[] sqlCommands = sb.toString().split(";");

            for (String sqlCommand : sqlCommands) {
                if (!sqlCommand.trim().isEmpty()) {
                    stmt.execute(sqlCommand);
                }
            }

            System.out.println("SQL script executed successfully.");
        } catch (IOException | SQLException e) {
            System.out.println("Error executing SQL script: " + e.getMessage());
        }
    }
}
