package Panels;

import org.example.StudentManager;
import org.example.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class AddNewStudentPanel extends JPanel {
    private JTextField idField;
    private JTextField nameField;
    private JTextField surnameField;
    private JTextField emailField;
    private JComboBox<String> qualificationComboBox;
    private JButton addButton;
    private StudentManager studentManager;

    public AddNewStudentPanel(StudentManager manager) {
        this.studentManager = manager;
        // Set the layout for better control of component positioning and sizing
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Add padding around components

        // Title label (Add new student)
        JLabel titleLabel = new JLabel("Add New Student");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18)); // Set the title font to bold and larger size
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;  // Span across two columns for the title
        add(titleLabel, gbc);

        // Create labels and fields for student details
        JLabel idLabel = new JLabel("Student ID:");
        idField = new JTextField(40);  // Set the width of the text field
        idField.setPreferredSize(new Dimension(idField.getPreferredSize().width, 30));  // Increase height

        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField(40);
        nameField.setPreferredSize(new Dimension(nameField.getPreferredSize().width, 30));  // Increase height

        JLabel surnameLabel = new JLabel("Surname:");
        surnameField = new JTextField(40);
        surnameField.setPreferredSize(new Dimension(surnameField.getPreferredSize().width, 30));  // Increase height

        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField(40);
        emailField.setPreferredSize(new Dimension(emailField.getPreferredSize().width, 30));  // Increase height

        JLabel qualificationLabel = new JLabel("Qualification:");
        qualificationComboBox = new JComboBox<>(new String[]{"Diploma in IT Management","Diploma in IT Network Management","Diploma in IT in Software Development", "Bachelor of Business Administration", "Bachelor of Public Administration"
                ,"Higher Certificate in Legal Studies"
        });
        qualificationComboBox.setPreferredSize(new Dimension(idField.getPreferredSize().width, 30));  // Set the height to match the text fields

        addButton = new JButton("Add Student");

        // Action listener for the "Add Student" button
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check if all fields are filled
                if (idField.getText().trim().isEmpty() ||
                        nameField.getText().trim().isEmpty() ||
                        surnameField.getText().trim().isEmpty() ||
                        emailField.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Error: All fields must be filled.");
                    return;
                }

                try {
                    String idNumber = idField.getText().trim();
                    String name = nameField.getText().trim();
                    String surname = surnameField.getText().trim();
                    String email = emailField.getText().trim();
                    String qualification = (String) qualificationComboBox.getSelectedItem();

                    // Validate ID Number (must be exactly 13 digits)
                    if (!idNumber.matches("\\d{13}")) {
                        JOptionPane.showMessageDialog(null, "Error: ID Number must be exactly 13 digits.");
                        return;
                    }

                    // Validate Name and Surname (must contain only letters)
                    if (!name.matches("[a-zA-Z]+")) {
                        JOptionPane.showMessageDialog(null, "Error: Name must contain only letters.");
                        return;
                    }
                    if (!surname.matches("[a-zA-Z]+")) {
                        JOptionPane.showMessageDialog(null, "Error: Surname must contain only letters.");
                        return;
                    }

                    // Validate Email (basic format validation)
                    if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                        JOptionPane.showMessageDialog(null, "Error: Invalid email format.");
                        return;
                    }

                    // Use the current date for enrollment
                    Date enrollmentDate = new Date();

                    // Create a new Student object
                    Student newStudent = new Student(0, idNumber, name, surname, email, qualification, enrollmentDate);  // ID is auto-generated
                    if (studentManager.addStudent(newStudent)) {
                        JOptionPane.showMessageDialog(null, "Student Added Successfully!");

                        // Clear the fields after successful submission
                        idField.setText("");
                        nameField.setText("");
                        surnameField.setText("");
                        emailField.setText("");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error: Duplicate ID or Email found.");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                }
            }
        });

        // Add components to the panel using GridBagLayout
        gbc.gridwidth = 1;  // Reset gridwidth after title

        // Add Student ID
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(idLabel, gbc);
        gbc.gridx = 1;
        add(idField, gbc);

        // Add Name
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(nameLabel, gbc);
        gbc.gridx = 1;
        add(nameField, gbc);

        // Add Surname
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(surnameLabel, gbc);
        gbc.gridx = 1;
        add(surnameField, gbc);

        // Add Email
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(emailLabel, gbc);
        gbc.gridx = 1;
        add(emailField, gbc);

        // Add Qualification
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(qualificationLabel, gbc);
        gbc.gridx = 1;
        add(qualificationComboBox, gbc);

        // Add "Add Student" Button
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;  // Span across two columns for the button
        add(addButton, gbc);
    }

    // Custom painting to mimic the CardView (rounded corners, shadow, etc.)
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw a rounded rectangle with shadow
        g2d.setColor(new Color(240, 240, 240)); // Card background color
        g2d.fillRoundRect(10, 10, getWidth() - 20, getHeight() - 20, 20, 20);

        // Add shadow effect
        g2d.setColor(new Color(0, 0, 0, 50)); // Shadow color with transparency
        g2d.fillRoundRect(15, 15, getWidth() - 20, getHeight() - 20, 20, 20); // Draw shadow
    }
}
