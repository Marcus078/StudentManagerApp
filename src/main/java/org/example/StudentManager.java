package org.example;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class StudentManager {
    private Connection connection;

    public StudentManager() {
        try {
            this.connection = DatabaseManager.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean addStudent(Student student) {
        try {
            String query = "INSERT INTO students (id_number, name, surname, email, qualification, enrollment_date) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, student.getIdNumber());
            stmt.setString(2, student.getName());
            stmt.setString(3, student.getSurname());
            stmt.setString(4, student.getEmail());
            stmt.setString(5, student.getQualification());
            stmt.setTimestamp(6, new java.sql.Timestamp(student.enrollmentDate.getTime()));
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    // Unified method to fetch students (active or removed) with pagination
    public List<Student> getStudents(boolean removed, int page, int pageSize) {
        List<Student> students = new ArrayList<>();
        try {
            int offset = page * pageSize;
            String query = "SELECT * FROM students WHERE removed_at IS " +
                    (removed ? "NOT NULL" : "NULL") + " LIMIT ? OFFSET ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, pageSize);
            stmt.setInt(2, offset);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                students.add(extractStudentFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    // Unified search method by ID Number or Name
    public List<Student> searchStudents(boolean removed, String query) {
        List<Student> students = new ArrayList<>();
        try {
            String sql = "SELECT * FROM students WHERE removed_at IS " +
                    (removed ? "NOT NULL" : "NULL") + " AND (id_number = ? OR LOWER(name) LIKE ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, query);
            stmt.setString(2, "%" + query.toLowerCase() + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                students.add(extractStudentFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    // Unified sort method
    public List<Student> sortStudents(boolean removed, String sortField, int page, int pageSize) {
        List<Student> students = new ArrayList<>();
        try {
            int offset = page * pageSize;
            String query = "SELECT * FROM students WHERE removed_at IS " +
                    (removed ? "NOT NULL" : "NULL") +
                    " ORDER BY " + sortField + " LIMIT ? OFFSET ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, pageSize);
            stmt.setInt(2, offset);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                students.add(extractStudentFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    // Update deletion logic to mark as removed
    public boolean removeStudentByIdNumber(String idNumber) {
        try {
            String query = "UPDATE students SET removed_at = ? WHERE id_number = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            stmt.setString(2, idNumber);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Restore student by ID Number
    public boolean restoreStudentByIdNumber(String idNumber) {
        try {
            String query = "UPDATE students SET removed_at = NULL WHERE id_number = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, idNumber);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    // Helper method to extract a student from a ResultSet
    private Student extractStudentFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String idNumber = rs.getString("id_number");
        String name = rs.getString("name");
        String surname = rs.getString("surname");
        String email = rs.getString("email");
        String qualification = rs.getString("qualification");
        Date enrollmentDate = rs.getDate("enrollment_date");
        return new Student(id, idNumber, name, surname, email, qualification, enrollmentDate);
    }

    // Get total student count (active or removed)
    public int getTotalStudentCount(boolean removed) {
        int count = 0;
        try {
            String query = "SELECT COUNT(*) FROM students WHERE removed_at IS " +
                    (removed ? "NOT NULL" : "NULL");
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    // Get available courses
    public List<String> getAvailableCourses() {
        List<String> courses = new ArrayList<>();
        try {
            String query = "SELECT DISTINCT qualification FROM students WHERE removed_at IS NULL";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                courses.add(rs.getString("qualification"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    // Get available years
    public List<Integer> getAvailableYears() {
        List<Integer> years = new ArrayList<>();
        try {
            String query = "SELECT DISTINCT YEAR(enrollment_date) AS year FROM students WHERE removed_at IS NULL";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                years.add(rs.getInt("year"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return years;
    }

    // Get student count by course
    public int getStudentCountByCourse(String course) {
        int count = 0;
        try {
            String query = "SELECT COUNT(*) AS count FROM students WHERE qualification = ? AND removed_at IS NULL";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, course);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    // Get student count by year
    public int getStudentCountByYear(int year) {
        int count = 0;
        try {
            String query = "SELECT COUNT(*) AS count FROM students WHERE YEAR(enrollment_date) = ? AND removed_at IS NULL";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, year);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    // Get student count by course and year
    public int getStudentCountByCourseAndYear(String course, int year) {
        int count = 0;
        try {
            String query = "SELECT COUNT(*) AS count FROM students WHERE qualification = ? AND YEAR(enrollment_date) = ? AND removed_at IS NULL";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, course);
            stmt.setInt(2, year);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
}
