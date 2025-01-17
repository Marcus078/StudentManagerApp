
package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    // Database connection details
    private static final String URL = "Your_database_url"; // Database URL with the database name 'studentApp'
    private static final String USERNAME = "Your_username"; // Username for the database connection
    private static final String PASSWORD = "Your Password";

    // Private constructor to prevent instantiation of the DatabaseManager class
    // This ensures that the class is used only for providing a database connection
    private DatabaseManager() {
    }

     //Establishes and returns a connection to the MySQL database.

    public static Connection getConnection() throws SQLException {
        // Using DriverManager to establish a connection to the MySQL database using the provided URL, username, and password
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}

