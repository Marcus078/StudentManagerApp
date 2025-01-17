package Panels;

import org.example.Student;
import org.example.StudentManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ViewAllStudentPanel extends JPanel {
    private final StudentManager studentManager;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JComboBox<String> sortComboBox;
    private int currentPage = 0;
    private final int pageSize = 37;
    private String sortField = "name";

    public ViewAllStudentPanel(StudentManager studentManager) {
        this.studentManager = studentManager;

        setLayout(new BorderLayout());

        // Initialize table
        tableModel = new DefaultTableModel(new String[]{"ID", "ID Number", "Name", "Surname", "Email", "Qualification", "Enrollment Date"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Search and sorting controls
        JPanel controlsPanel = new JPanel();
        searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        JButton clearSearchButton = new JButton("Clear Search");
        sortComboBox = new JComboBox<>(new String[]{"Name", "ID Number", "Qualification", "Enrollment Date"});
        controlsPanel.add(new JLabel("Search:"));
        controlsPanel.add(searchField);
        controlsPanel.add(searchButton);
        controlsPanel.add(clearSearchButton);
        controlsPanel.add(new JLabel("Sort by:"));
        controlsPanel.add(sortComboBox);
        add(controlsPanel, BorderLayout.NORTH);

        // Pagination and delete buttons
        JPanel bottomPanel = new JPanel();
        JButton nextPageButton = new JButton("Next");
        JButton prevPageButton = new JButton("Previous");
        JButton deleteButton = new JButton("Delete Selected");
        bottomPanel.add(prevPageButton);
        bottomPanel.add(nextPageButton);
        bottomPanel.add(deleteButton);
        add(bottomPanel, BorderLayout.SOUTH);

        // Load initial student data
        loadStudents();

        // Action listeners
        searchButton.addActionListener(e -> searchStudents());
        clearSearchButton.addActionListener(e -> clearSearch());
        sortComboBox.addActionListener(e -> updateSortField());
        nextPageButton.addActionListener(e -> {
            currentPage++;
            loadStudents();
        });
        prevPageButton.addActionListener(e -> {
            if (currentPage > 0) {
                currentPage--;
                loadStudents();
            }
        });
        deleteButton.addActionListener(e -> deleteSelectedStudent());
    }

    private void loadStudents() {
        List<Student> students = studentManager.sortStudents(false, sortField, currentPage, pageSize);
        populateTable(students);

        // Disable Next button if no students to load
        if (students.size() < pageSize) {
            // Disable "Next" button when no more students
            // Assuming you have a reference to the button from the action listener
            // nextPageButton.setEnabled(false);
        }
    }

    private void searchStudents() {
        String query = searchField.getText().trim();
        List<Student> students = studentManager.searchStudents(false, query);
        if (students.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No students found.");
        } else {
            populateTable(students);
        }
    }

    private void clearSearch() {
        searchField.setText(""); // Clear search field
        currentPage = 0; // Reset to the first page
        loadStudents(); // Reload the first page with default sorting
    }

    private void updateSortField() {
        switch (sortComboBox.getSelectedItem().toString()) {
            case "ID Number":
                sortField = "id_number";
                break;
            case "Qualification":
                sortField = "qualification";
                break;
            case "Enrollment Date":
                sortField = "enrollment_date";
                break;
            default:
                sortField = "name";
        }
        loadStudents();
    }

    private void deleteSelectedStudent() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student to delete.");
            return;
        }

        Object idNumberObject = tableModel.getValueAt(selectedRow, 1);
        if (idNumberObject instanceof String) {
            String idNumber = (String) idNumberObject;

            int confirmation = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete the student with ID Number: " + idNumber + "?",
                    "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirmation == JOptionPane.YES_OPTION) {
                if (studentManager.removeStudentByIdNumber(idNumber)) {
                    tableModel.removeRow(selectedRow);
                    JOptionPane.showMessageDialog(this, "Student deleted successfully.");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete the student.");
                }
            }
        }
    }

    private void populateTable(List<Student> students) {
        tableModel.setRowCount(0);
        for (Student student : students) {
            tableModel.addRow(new Object[]{
                    student.getId(), student.getIdNumber(), student.getName(),
                    student.getSurname(), student.getEmail(),
                    student.getQualification(), student.getEnrollmentDate()
            });
        }
    }
}


/*
package Panels;

import org.example.Student;
import org.example.StudentManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ViewAllStudentPanel extends JPanel {
    private final StudentManager studentManager;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JComboBox<String> sortComboBox;
    private int currentPage = 0;
    private final int pageSize = 37;
    private String sortField = "name"; // Default sort field

    public ViewAllStudentPanel(StudentManager studentManager) {
        this.studentManager = studentManager;
        setLayout(new BorderLayout());

        // Table setup
        tableModel = new DefaultTableModel(new String[]{"ID", "ID Number", "Name", "Surname", "Email", "Qualification", "Enrollment Date"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Controls setup (above the table)
        JPanel controlsPanel = new JPanel();
        searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        sortComboBox = new JComboBox<>(new String[]{"Name", "ID Number", "Qualification", "Enrollment Date"});

        controlsPanel.add(new JLabel("Search:"));
        controlsPanel.add(searchField);
        controlsPanel.add(searchButton);
        controlsPanel.add(new JLabel("Sort by:"));
        controlsPanel.add(sortComboBox);
        add(controlsPanel, BorderLayout.NORTH);

        // Bottom panel for the buttons
        JPanel bottomPanel = new JPanel();
        JButton nextPageButton = new JButton("Next");
        JButton prevPageButton = new JButton("Previous");
        JButton deleteButton = new JButton("Delete Selected");

        bottomPanel.add(prevPageButton);
        bottomPanel.add(nextPageButton);
        bottomPanel.add(deleteButton);
        add(bottomPanel, BorderLayout.SOUTH);

        // Load initial data
        loadStudents();

        // Action listeners
        searchButton.addActionListener(e -> searchStudents());
        sortComboBox.addActionListener(e -> updateSortField());
        nextPageButton.addActionListener(e -> {
            currentPage++;
            loadStudents();
        });
        prevPageButton.addActionListener(e -> {
            if (currentPage > 0) {
                currentPage--;
                loadStudents();
            }
        });
        deleteButton.addActionListener(e -> deleteSelectedStudent());
    }

    private void loadStudents() {
        List<Student> students = studentManager.sortStudents(false, sortField, currentPage, pageSize);
        populateTable(students);
    }

    private void searchStudents() {
        String query = searchField.getText().trim();
        List<Student> students = studentManager.searchStudents(false, query);
        populateTable(students);
    }

    private void updateSortField() {
        switch (sortComboBox.getSelectedItem().toString()) {
            case "ID Number":
                sortField = "id_number";
                break;
            case "Qualification":
                sortField = "qualification";
                break;
            case "Enrollment Date":
                sortField = "enrollment_date";
                break;
            default:
                sortField = "name";
        }
        loadStudents(); // Reload with the new sort option
    }

    private void deleteSelectedStudent() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student to delete.");
            return;
        }

        Object idNumberObject = tableModel.getValueAt(selectedRow, 1); // ID Number is in the second column
        if (idNumberObject instanceof String) {
            String idNumber = (String) idNumberObject;

            int confirmation = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete the student with ID Number: " + idNumber + "?",
                    "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirmation == JOptionPane.YES_OPTION) {
                if (studentManager.removeStudentByIdNumber(idNumber)) {
                    tableModel.removeRow(selectedRow); // Remove from table
                    JOptionPane.showMessageDialog(this, "Student deleted successfully.");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete the student.");
                }
            }
        }
    }

    private void populateTable(List<Student> students) {
        tableModel.setRowCount(0); // Clear existing data
        for (Student student : students) {
            tableModel.addRow(new Object[]{
                    student.getId(), student.getIdNumber(), student.getName(),
                    student.getSurname(), student.getEmail(),
                    student.getQualification(), student.getEnrollmentDate()
            });
        }
    }
}
*/
