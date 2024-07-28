package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class JDBCReader {
    private static final String SELECT_ALL = "SELECT * FROM teachers";
    private static final String SELECT_PARAMETRIZED = "SELECT * FROM teachers WHERE id = ?";
    private static final String INSERT = "INSERT INTO teachers (id, first_name, last_name, school, hire_date, salary) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE teachers SET first_name = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM teachers WHERE id = ?";

    public static List<Teacher> getTeachers() {
        List<Teacher> teachers = new ArrayList<>();
        try (Connection connection = DataBaseManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Teacher teacher = new Teacher(
                        resultSet.getLong("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("school"),
                        resultSet.getDate("hire_date"),
                        resultSet.getDouble("salary")
                );
                teachers.add(teacher);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        return teachers;
    }

    public static void insertTeacher(Teacher teacher) {
        try (Connection connection = DataBaseManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT);
            preparedStatement.setLong(1, teacher.getId());
            preparedStatement.setString(2, teacher.getFirstName());
            preparedStatement.setString(3, teacher.getLastName());
            preparedStatement.setString(4, teacher.getSchool());
            preparedStatement.setDate(5, new java.sql.Date(teacher.getHireDate().getTime()));
            preparedStatement.setDouble(6, teacher.getSalary());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void updateTeacherName(String name, long id) {
        try (Connection connection = DataBaseManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE);
            preparedStatement.setString(1, name);
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void deleteTeacher(long id) {
        try (Connection connection = DataBaseManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void main(String[] args) {
        // Додавання нового вчителя
        Teacher newTeacher = new Teacher(6, "John", "Doe", "Springfield High", new Date(), 50000.0);
        insertTeacher(newTeacher);

        // Оновлення імені вчителя з id 6
        updateTeacherName("Jane", 6);

        // Отримання всіх вчителів
        List<Teacher> result = getTeachers();
        result.forEach(System.out::println);

        // Видалення вчителя з id 6
        deleteTeacher(6);
    }
}
