Overview:

The Student Management System is a Java application designed to efficiently manage student records. It provides functionalities for managing student data, viewing statistics, 
and generating visual insights. The application is built with a focus on user-friendly interfaces and scalability.

1 Add New Students: Allows adding new student records with validation for ID number, name, surname, and email format.

2 View All Students: Displays a paginated table of all active students with search and sorting functionality.

3 View Removed Students: Displays a paginated table of removed students.

4 Search Functionality: Enables searching for students by ID number or name.

5 Sorting Functionality: Allows sorting students by name, ID number, qualification, or enrollment date.

6 Pagination: Implements pagination for efficient viewing of large student datasets.

7 Data Validation: Includes input validation to ensure data integrity.

8 Soft Delete: Students are marked as "removed" rather than permanently deleted, allowing for restoration.

9 Database Interaction: Uses JDBC to interact with a MySQL database.

Technologies Used

Programming Language: Java
IDE: Intellij IDE
GUI Framework: Swing
Charting Library: JFreeChart
Database: MySQL
Build Tool: Maven
Version Control: GitHub

Setup and Installation

Prerequisites
Java Development Kit (JDK) 8 or higher.
Maven (for building the project).
MySQL (for the database).
An IDE like IntelliJ IDEA, Eclipse, or NetBeans (optional)

Steps
1 Clone the repository:
git clone https://github.com/Marcus078/StudentManagerApp.git

2 Navigate to the project directory:
cd student-management-system

3 Configure the database:
*   Open the 'DatabaseManager.java' file.
    *   Update the following constants with your database credentials:
      
    private static final String URL = "jdbc:mysql://localhost:3306/studentApp"; // Replace with your URL
    private static final String USERNAME = "your_db_username"; // Replace with your username
    private static final String PASSWORD = "your_db_password"; // Replace with your password

4 Create a MySQL database (e.g., student_management).
Create database studentApp;
use studentApp;
CREATE TABLE students (
        id INT AUTO_INCREMENT PRIMARY KEY,
        id_number VARCHAR(13) UNIQUE NOT NULL,
        name VARCHAR(255) NOT NULL,
        surname VARCHAR(255) NOT NULL,
        email VARCHAR(255) UNIQUE NOT NULL,
        qualification VARCHAR(255),
        enrollment_date DATE,
        removed_at TIMESTAMP NULL
    );
    
5 Build the project:
mvn clean install

6 Run the application:
mvn exec:java

Usage
Launch the application.
Navigate through the tabs to:
Add new students.
View student records.
Analyze data using charts.
Use dropdowns to filter data and explore insights.

Screenshots:
![stats panel](https://github.com/user-attachments/assets/401e8cef-46d8-41a7-93da-e17342ff569e)
![view all students panel](https://github.com/user-attachments/assets/9f0b3065-cebc-4b7c-99e3-4eecea59ee37)
![add new student panel](https://github.com/user-attachments/assets/ff85e52d-292e-4785-b552-c17044040cd6)




Future Enhancements

Add user authentication and role-based access.
Export data to CSV or PDF formats.
Enable multi-language support.
Implement additional analytics features.

Contributing
We welcome contributions to this project. To contribute:
1 Fork the repository.

2 Create a feature branch:
git checkout -b feature-name

3 Commit your changes:
git commit -m "Add your message here"

4 Push to your fork:
git push origin feature-name

3 Submit a pull request.

Feel free to reach out if you have any questions or suggestions!

