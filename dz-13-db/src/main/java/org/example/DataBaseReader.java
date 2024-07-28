package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBaseReader {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "username";
    private static final String PASSWORD = "password";

    public static void main(String[] args) {
        List<Teacher> teachers = getAllTeachers();

        // Виведення всіх вчителів
        for (Teacher teacher : teachers) {
            System.out.println(teacher);
        }
    }

    public static List<Teacher> getAllTeachers() {
        List<Teacher> teachers = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM teachers")) {

            while (rs.next()) {
                long id = rs.getLong("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String school = rs.getString("school");
                Date hireDate = rs.getDate("hire_date");
                double salary = rs.getDouble("salary");

                Teacher teacher = new Teacher(id, firstName, lastName, school, hireDate, salary);
                teachers.add(teacher);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error reading data: " + e.getMessage());
        }

        return teachers;
    }
}
