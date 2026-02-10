import models.Grade;
import models.Student;
import enums.Subject;

import java.sql.*;

Connection conn;
Scanner scanner = new Scanner(System.in);
ArrayList<Student> _students = new ArrayList<>();
Subject[] subjects = Subject.values();


void main() throws SQLException {
    connectDB();
    System.out.println("Welcome to the student management system!");

    main_loop:
    while (true) {
        printMenu();
        System.out.print("Input: ");
        int input = Integer.parseInt(scanner.nextLine());

        switch (input) {
            case 1 -> addStudent();
            case 2 -> printStudents();
            case 3 -> printStudentGrades();
            case 4 -> printStudentAverage();
            case 5 -> addStudentGrade();
            case 6 -> {
                System.out.println("Goodbye!");
                break main_loop;
            }
            default -> System.out.println("Invalid input.");
        }
    }
}

void connectDB() {
    String url = "jdbc:sqlite:student-grade.db";
    try {
        conn = DriverManager.getConnection(url);
        System.out.println("Connected successfully.");
        Statement statement = conn.createStatement();
        String sql = "CREATE TABLE IF NOT EXISTS students (id INTEGER PRIMARY KEY AUTOINCREMENT, first_name TEXT NOT NULL, last_name TEXT NOT NULL);";
        statement.execute(sql);
        System.out.println("Student table created.");
        sql = "CREATE TABLE IF NOT EXISTS grades (id INTEGER PRIMARY KEY AUTOINCREMENT, subject TEXT CHECK (subject IN ('ENGLISH', 'MATHEMATICS', 'PHYSICAL_EDUCATION', 'PHYSICS', 'CHEMISTRY', 'BIOLOGY')), grade_score NUMBER);";
        statement.execute(sql);
        System.out.println("Grades table created.");
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
}

void printMenu() {
    System.out.println("What would you like to do?");
    System.out.println("1. Add student record.");
    System.out.println("2. Print all students.");
    System.out.println("3. Print a students grades.");
    System.out.println("4. Calculate student average.");
    System.out.println("5. Add student grade.");
    System.out.println("6. Exit");
}
void addStudent() {
    System.out.print("Enter first name: ");
    String firstName = scanner.nextLine();
    System.out.print("Enter last name: ");
    String lastName = scanner.nextLine();
    try {
        Student student = Student.create(firstName, lastName, conn);
        System.out.printf("Student %s %s was successfully added", student.getFirstName(), student.getLastName());
    } catch (SQLException e) {
        System.out.println("An error occurred.");
        System.out.println(e.getMessage());
    }
}

void printStudents() {
    try {
        List<Student> students = Student.getAll(conn);
        if (students.isEmpty()) {
            System.out.println("There are no students in the system.");
        }
        for(Student student: students) {
            System.out.printf("%d: %s %s", student.getId(), student.getFirstName(), student.getLastName());
            System.out.println();
        }
    } catch (SQLException e) {
        System.out.println("An error occurred.");
        System.out.println(e.getMessage());
    }
}
void printStudentGrades() {
    if (_students.isEmpty()) {
        System.out.println("There are no students in the system!");
        return;
    }

    try {
        System.out.printf("Enter a student number(1 - %d): ", Student.getStudentCount(conn));
        int id = Integer.parseInt(scanner.nextLine());
        Student student = Student.getById(id, conn);
        if (student == null) {
            System.out.printf("There is no student with an ID = %d\n", id);
            return;
        }

        List<Grade> studentGrades = student.getGrades();
        for(Grade grade: studentGrades) {
            System.out.printf("%s: %.2f%n", grade.getSubject(), grade.getGrade());
        }
    } catch (SQLException e) {
        System.out.println("An error occurred.");
        System.out.println(e.getMessage());
    }
}
void printStudentAverage() {
    if (_students.isEmpty()) {
        System.out.println("There are no students in the system!");
        return;
    }
    System.out.printf("Enter a student number(1 - %d): ", Student.studentCount);
    int id = Integer.parseInt(scanner.nextLine());
    Student student = _students.get(id - 1);
    System.out.printf("%d: %s %s", student.getId(), student.getFirstName(), student.getLastName());
    System.out.printf("Average: %.2f%n", student.gradeAverage());
    System.out.println();
}
void addStudentGrade() {
    if (_students.isEmpty()) {
        System.out.println("There are no students in the system!");
        return;
    }

    System.out.printf("Enter a student id(1 - %d):", Student.studentCount);
    int studentId = Integer.parseInt(scanner.nextLine());
    if (studentId > Student.studentCount) {
        System.out.println("Invalid student id!");
        return;
    }

    printSubjects();
    System.out.print("Enter subject name: ");
    int subjectId = Integer.parseInt(scanner.nextLine());
    if (subjectId > subjects.length) {
        System.out.println("Invalid subject id!");
        return;
    }

    System.out.print("Enter grade score: ");
    float gradeScore = Float.parseFloat(scanner.nextLine());
    if (gradeScore > 6 || gradeScore < 2) {
        System.out.println("Invalid grade score!");
        return;
    }
    Grade grade = new Grade(subjects[subjectId - 1], gradeScore);
    Student student = _students.get(studentId - 1);
    student.addGrade(grade);
    System.out.printf("%s grade was added successfully to %s %s.\n", grade.getSubject(), student.getFirstName(), student.getLastName());
}
void printSubjects() {
    for(int i = 0; i < subjects.length; i++) {
        System.out.printf("%d: %s\n", i + 1, subjects[i]);
    }
}