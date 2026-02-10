package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Student {
    public static int studentCount = 0;
    private final int id;
    private final String firstName;
    private final String lastName;
    private final List<Grade> grades;

    public Student(String firstName, String lastName) {
        studentCount++;
        this.id = studentCount;
        this.firstName = firstName;
        this.lastName = lastName;
        this.grades = new ArrayList<>();
    }
    private Student(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.grades = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public float gradeAverage() {
        if (grades.isEmpty()) return 0f;

        float sum = 0;
        int count = 0;
        for (Grade grade: grades) {
            sum += grade.getGrade();
            count++;
        }
        return sum / count;
    }
    public void addGrade(Grade grade) {
        grades.add(grade);
        System.out.printf("Grade added successfully to student with ID %d", this.id);
    }
    public static Student create(String firstName, String lastName, Connection connection) throws SQLException {
        Student student = new Student(firstName, lastName);
        String sql = "INSERT INTO students(first_name, last_name) VALUES(?, ?);";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, student.getFirstName());
        statement.setString(2, student.getLastName());

        statement.executeUpdate();

        return student;
    }
    public static Student getById(int student_id, Connection connection) throws SQLException {
        String sql = "SELECT id, first_name, last_name FROM students WHERE id = ?;";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, student_id);
        ResultSet resultSet = statement.executeQuery();
        if (!resultSet.next()) {
            resultSet.close();
            statement.close();
            return null;
        }
        int id = resultSet.getInt("id");
        String firstName = resultSet.getString("first_name");
        String lastName = resultSet.getString("last_name");

        resultSet.close();
        statement.close();
        return new Student(id, firstName, lastName);
    }
    public static ArrayList<Student> getAll(Connection connection) throws SQLException {
        ArrayList<Student> students = new ArrayList<>();
        String sql = "SELECT id, first_name, last_name FROM students";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            Student student = new Student(id, firstName, lastName);
            students.add(student);
        }
        resultSet.close();
        statement.close();
        return students;
    }
    public static int getStudentCount(Connection connection) throws SQLException {
        String sql = "SELECT id FROM students ORDER BY id DESC LIMIT 1;";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        if (!resultSet.next()) {
            return 0;
        }
        return resultSet.getInt("id");
    }
}
