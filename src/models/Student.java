package models;

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
        System.out.printf("models.Grade added successfully to student with ID %d", this.id);
    }
}
