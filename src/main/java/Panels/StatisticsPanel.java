package Panels;

import org.example.StudentManager;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class StatisticsPanel extends JPanel {
    private StudentManager studentManager;

    public StatisticsPanel(StudentManager studentManager) {
        this.studentManager = studentManager;
        setLayout(new GridLayout(3, 1, 10, 10)); // Three sections stacked vertically
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding around sections

        // Section 1: Pie Chart for Student Distribution by Year
        JPanel pieChartPanel = createPieChart();
        add(pieChartPanel);

        // Section 2: Bar Chart for Single Course
        JPanel singleCourseBarChartPanel = createSingleCourseBarChart();
        add(singleCourseBarChartPanel);

        // Section 3: Bar Chart for Multiple Courses
        JPanel multipleCoursesBarChartPanel = createMultipleCoursesBarChart();
        add(multipleCoursesBarChartPanel);
    }

    /**
     * Creates a pie chart section to display the total students by year.
     */
    private JPanel createPieChart() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel title = new JLabel("Total Students by Year", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(title, BorderLayout.NORTH);

        // Fetch data for pie chart
        DefaultPieDataset dataset = new DefaultPieDataset();
        List<Integer> years = studentManager.getAvailableYears();
        if (years.isEmpty()) {
            JLabel errorLabel = new JLabel("No data available to display.", JLabel.CENTER);
            panel.add(errorLabel, BorderLayout.CENTER);
            return panel;
        }

        for (int year : years) {
            int count = studentManager.getStudentCountByYear(year);
            dataset.setValue(String.valueOf(year), count);
        }

        // Create pie chart
        JFreeChart pieChart = ChartFactory.createPieChart(
                "", // Chart title
                dataset,
                true,
                true,
                false
        );

        ChartPanel chartPanel = new ChartPanel(pieChart);
        panel.add(chartPanel, BorderLayout.CENTER);

        return panel;
    }

     // Bar chart section to display students in a single course by year.

    private JPanel createSingleCourseBarChart() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel title = new JLabel("Compare Students in a Single Course by Year", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(title, BorderLayout.NORTH);

        // Dropdown for Course Selection
        JComboBox<String> courseDropdown = new JComboBox<>();
        List<String> courses = studentManager.getAvailableCourses();
        if (courses.isEmpty()) {
            JLabel errorLabel = new JLabel("No courses available.", JLabel.CENTER);
            panel.add(errorLabel, BorderLayout.CENTER);
            return panel;
        }
        for (String course : courses) {
            courseDropdown.addItem(course);
        }
        panel.add(courseDropdown, BorderLayout.SOUTH);

        // Bar chart dataset
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Create bar chart
        JFreeChart barChart = ChartFactory.createBarChart(
                "",
                "Year",
                "Number of Students",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        ChartPanel chartPanel = new ChartPanel(barChart);
        panel.add(chartPanel, BorderLayout.CENTER);

        // Update bar chart on course selection
        courseDropdown.addActionListener(e -> {
            String selectedCourse = (String) courseDropdown.getSelectedItem();
            dataset.clear();
            for (int year : studentManager.getAvailableYears()) {
                int total = studentManager.getStudentCountByCourseAndYear(selectedCourse, year);
                dataset.addValue(total, selectedCourse, String.valueOf(year));
            }
        });

        return panel;
    }

 //Bar chart section to compare courses in a specific year.

    private JPanel createMultipleCoursesBarChart() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel title = new JLabel("Compare Courses in a Specific Year", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(title, BorderLayout.NORTH);

        // Dropdown for Year Selection
        JComboBox<Integer> yearDropdown = new JComboBox<>();
        List<Integer> years = studentManager.getAvailableYears();
        if (years.isEmpty()) {
            JLabel errorLabel = new JLabel("No years available.", JLabel.CENTER);
            panel.add(errorLabel, BorderLayout.CENTER);
            return panel;
        }
        for (Integer year : years) {
            yearDropdown.addItem(year);
        }
        panel.add(yearDropdown, BorderLayout.SOUTH);

        // Bar chart dataset
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Create bar chart
        JFreeChart barChart = ChartFactory.createBarChart(
                "", // Chart title
                "Course",
                "Number of Students",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        ChartPanel chartPanel = new ChartPanel(barChart);
        panel.add(chartPanel, BorderLayout.CENTER);

        // Update bar chart on year selection
        yearDropdown.addActionListener(e -> {
            int selectedYear = (int) yearDropdown.getSelectedItem();
            dataset.clear();
            for (String course : studentManager.getAvailableCourses()) {
                int total = studentManager.getStudentCountByCourseAndYear(course, selectedYear);
                dataset.addValue(total, course, String.valueOf(selectedYear));
            }
        });

        return panel;
    }
}