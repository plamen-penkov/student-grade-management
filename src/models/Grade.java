package models;

import enums.Subject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Grade {
    private final int id;
    private final Subject subject;
    private final float gradeScore;
    private final int studentId;

    public Grade(Subject subject, float gradeScore) {
        this.id = 0;
        this.subject = subject;
        this.gradeScore = gradeScore;
        this.studentId = 0;
    }
    private Grade(int id, Subject subject, float gradeScore, int student_id) {
        this.id = id;
        this.subject = subject;
        this.gradeScore = gradeScore;
        this.studentId = student_id;
    }

    public static List<Grade> getGradesByStudentId(int studentId, Connection connection) throws SQLException {
        String sql = "SELECT id, subject, grade_score, student_id FROM grades WHERE student_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, studentId);

        List<Grade> studentGrades = new ArrayList<>();
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            Subject subject = Subject.valueOf(resultSet.getString("subject"));
            float gradeScore = resultSet.getFloat("grade_score");
            studentId = resultSet.getInt("student_id");
            Grade grade = new Grade(id, subject, gradeScore, studentId);
            studentGrades.add(grade);
        }

        return studentGrades;
    }

    public int getId() { return this.id ;}

    public Subject getSubject() { return this.subject; }

    public float getGrade() { return this.gradeScore; }

    public int getStudentId() { return this.studentId; }

    public static Grade create(Subject subject, float gradeScore, int student_id, Connection connection) throws SQLException {
        Grade grade = new Grade(subject, gradeScore);
        String sql = "INSERT INTO grades(subject, grade_score, student_id) VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, String.valueOf(subject));
        statement.setFloat(2, gradeScore);
        statement.setInt(3, student_id);

        statement.executeUpdate();

        return grade;
    }
}
