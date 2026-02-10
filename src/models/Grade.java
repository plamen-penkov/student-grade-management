package models;

import enums.Subject;

public class Grade {
    private static int gradeCount = 0;
    private final int id;
    private final Subject subject;
    private final float grade;

    public Grade(Subject subject, float grade) {
        gradeCount++;
        this.id = gradeCount;
        this.subject = subject;
        this.grade = grade;
    }

    public int getId() { return this.id ;}

    public Subject getSubject() {
        return subject;
    }

    public float getGrade() {
        return grade;
    }
}
