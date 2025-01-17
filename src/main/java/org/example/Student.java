package org.example;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Student {
    // Instance variables to store student details
    private int id; // Unique student ID
    private String idNumber;  // National ID or similar identifier
    private String name; // Student's first name
    private String surname; // Student's surname
    private String email; // Student's email address
    private String qualification;  // Student's qualification (e.g., degree or diploma)
    Date enrollmentDate; // Date the student was enrolled

    // Constructor to initialize the student object with the provided values
    public Student(int id, String idNumber, String name, String surname, String email, String qualification, Date enrollmentDate) {
        this.id = id;
        this.idNumber = idNumber;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.qualification = qualification;
        this.enrollmentDate = enrollmentDate; // Initialize enrollment date
    }

    // Getter methods to access the student details
    public int getId() {
        return id; // Return the student ID
    }

    public String getIdNumber() {
        return idNumber; // Return the student's ID number (national ID or similar)
    }

    public String getName() {
        return name; // Return the student's name
    }

    public String getSurname() {
        return surname; // Return the student's surname
    }

    public String getEmail() {
        return email; // Return the student's email address
    }

    public String getQualification() {
        return qualification; // Return the student's qualification
    }

    // Get the formatted enrollment date as a string
    public String getEnrollmentDate() {
        // Format the enrollment date to a human-readable string (e.g., "Mon 01-Jan-2025")
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE dd-MMM-yyyy", Locale.getDefault());
        return dateFormat.format(enrollmentDate); // Format the date and return as string
    }

    // Override the toString method to provide a custom string representation of the student object
    @Override
    public String toString() {
        // Return a string containing key details about the student
        return "ID Number: " + idNumber + ", Name: " + name + " " + surname + ", Email: " + email
                + ", Qualification: " + qualification + ", Date: " + getEnrollmentDate();
    }
}
