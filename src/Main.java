import models.Grade;
import models.Student;
import enums.Subject;

Scanner scanner = new Scanner(System.in);
ArrayList<Student> students = new ArrayList<>();
Subject[] subjects = Subject.values();


void main() {
    System.out.println("Welcome to the student management system!");

    main_loop:
    while (true) {
        printMenu();
        System.out.print("Input: ");
        int input = Integer.parseInt(scanner.nextLine());

        switch (input) {
            case 1 -> addStudent();
            case 2 -> printStudentAverage();
            case 3 -> printStudents();
            case 4 -> addStudentGrade();
            case 5 -> {
                System.out.println("Goodbye!");
                break main_loop;
            }
            default -> System.out.println("Invalid input.");
        }
    }
}

void printMenu() {
    System.out.println("What would you like to do?");
    System.out.println("1. Add student record.");
    System.out.println("2. Calculate student average.");
    System.out.println("3. Print all students.");
    System.out.println("4. Add student grade.");
    System.out.println("5. Exit");
}
void addStudent() {
    System.out.print("Enter first name: ");
    String firstName = scanner.nextLine();
    System.out.print("Enter last name: ");
    String lastName = scanner.nextLine();
    Student student = new Student(firstName, lastName);
    students.add(student);
    System.out.printf("models.Student %s %s was successfully added", student.getFirstName(), student.getLastName());
}

void printStudents() {
    for(Student student: students) {
        System.out.printf("%d: %s %s", student.getId(), student.getFirstName(), student.getLastName());
        System.out.println();
    }
}
void printStudentAverage() {
    if (students.isEmpty()) {
        System.out.println("There are no students in the system!");
        return;
    }
    System.out.printf("Enter a student number(1 - %d): ", Student.studentCount);
    int id = Integer.parseInt(scanner.nextLine());
    Student student = students.get(id - 1);
    System.out.printf("%d: %s %s", student.getId(), student.getFirstName(), student.getLastName());
    System.out.printf("Average: %.2f%n", student.gradeAverage());
    System.out.println();
}
void addStudentGrade() {
    if (students.isEmpty()) {
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
    Student student = students.get(studentId - 1);
    student.addGrade(grade);
    System.out.printf("%s grade was added successfully to %s %s", grade.getSubject(), student.getFirstName(), student.getLastName());
}
void printSubjects() {
    for(int i = 0; i < subjects.length; i++) {
        System.out.printf("%d: %s", i + 1, subjects[i]);
        System.out.println();
    }
}
