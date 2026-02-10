# 📚 Student Grade Management System

A command-line Java application for managing student records and grades, backed by an SQLite database.

## Features

- **Student Management** — Add and list student records
- **Grade Tracking** — Assign grades (2.00 – 6.00) across six subjects
- **Average Calculation** — Compute per-student grade averages
- **Persistent Storage** — All data is stored in a local SQLite database (`student-grade.db`)

## Supported Subjects

| # | Subject            |
|---|--------------------|
| 1 | English            |
| 2 | Mathematics        |
| 3 | Physical Education |
| 4 | Physics            |
| 5 | Chemistry          |
| 6 | Biology            |

## Project Structure

```
├── src/
│   ├── Main.java            # Entry point & CLI menu loop
│   ├── enums/
│   │   └── Subject.java     # Subject enum
│   └── models/
│       ├── Student.java     # Student model & DB operations
│       └── Grade.java       # Grade model & DB operations
├── student-grade.db          # SQLite database (auto-created)
└── .gitignore
```

## Prerequisites

- **Java 21+** (uses unnamed classes & instance main methods — [JEP 463](https://openjdk.org/jeps/463))
- **SQLite JDBC driver** on the classpath

## Getting Started

### Clone the repository

```bash
git clone https://github.com/plamen-penkov/student-grade-management.git
cd student-grade-management
```

### Run with IntelliJ IDEA

Open the project in IntelliJ IDEA — it will pick up the `.iml` configuration automatically. Run `Main.java`.

### Run from the command line

```bash
# Compile
javac -d out src/enums/Subject.java src/models/Student.java src/models/Grade.java src/Main.java

# Run (ensure the SQLite JDBC jar is on the classpath)
java -cp out:sqlite-jdbc-<version>.jar Main
```

> **Note:** The SQLite database file `student-grade.db` is created automatically in the project root on first run.

## Usage

```
Welcome to the student management system!
What would you like to do?
1. Add student record.
2. Print all students.
3. Print a students grades.
4. Calculate student average.
5. Add student grade.
6. Exit
Input:
```

### Example — Adding a student and a grade

```
Input: 1
Enter first name: John
Enter last name: Doe
Student John Doe was successfully added

Input: 5
Enter a student id(1 - 1): 1
1: ENGLISH
2: MATHEMATICS
3: PHYSICAL_EDUCATION
4: PHYSICS
5: CHEMISTRY
6: BIOLOGY
Enter subject id: 2
Enter grade score: 5.50
MATHEMATICS grade was added successfully to John Doe.
```

## Database Schema

```sql
CREATE TABLE students (
    id         INTEGER PRIMARY KEY AUTOINCREMENT,
    first_name TEXT NOT NULL,
    last_name  TEXT NOT NULL
);

CREATE TABLE grades (
    id         INTEGER PRIMARY KEY AUTOINCREMENT,
    subject    TEXT CHECK (subject IN ('ENGLISH','MATHEMATICS',
               'PHYSICAL_EDUCATION','PHYSICS','CHEMISTRY','BIOLOGY')),
    grade_score NUMBER,
    student_id INTEGER,
    FOREIGN KEY (student_id) REFERENCES students (id) ON DELETE CASCADE
);
```

## License

This project is provided for educational purposes.
