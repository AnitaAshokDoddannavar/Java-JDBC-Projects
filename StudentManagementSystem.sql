-- STUDENT MANAGEMENT SYSTEM

CREATE DATABASE student_management;

USE student_management;

SHOW DATABASES;

-- students table
CREATE TABLE students (
    student_id INT PRIMARY KEY AUTO_INCREMENT,
    student_name VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    phone VARCHAR(15)
);

DESC students;

-- courses table
CREATE TABLE courses (
    course_id INT PRIMARY KEY AUTO_INCREMENT,
    course_name VARCHAR(100) NOT NULL,
    duration INT
);

-- enrollments table
CREATE TABLE enrollments (
    enrollment_id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT,
    course_id INT,
    enrollment_date DATE,

    FOREIGN KEY (student_id)
        REFERENCES students(student_id),

    FOREIGN KEY (course_id)
        REFERENCES courses(course_id)
);

-- attendance table
CREATE TABLE attendance (
    attendance_id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT,
    attendance_date DATE,
    status VARCHAR(10),

    FOREIGN KEY (student_id)
        REFERENCES students(student_id)
);

-- marks table
CREATE TABLE marks (
    mark_id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT,
    subject VARCHAR(100),
    marks INT,

    FOREIGN KEY (student_id)
        REFERENCES students(student_id)
);

-- Sample data
INSERT INTO students (student_name, email, phone)
VALUES
('Anita', 'anita@gmail.com', '9876543210'),
('Rahul', 'rahul@gmail.com', '9876543211'),
('Priya', 'priya@gmail.com', '9876543212');

SELECT * FROM students;

INSERT INTO courses (course_name, duration)
VALUES
('Java', 3),
('SQL', 2),
('Python', 3);

SELECT * FROM courses;

INSERT INTO enrollments
(student_id, course_id, enrollment_date)
VALUES
(1, 1, '2026-08-20'),
(2, 2, '2026-08-20'),
(3, 3, '2026-08-20');

SELECT * FROM enrollments;

INSERT INTO attendance
(student_id, attendance_date, status)
VALUES
(1, '2026-08-20', 'Present'),
(2, '2026-08-20', 'Present'),
(3, '2026-08-20', 'Absent');

SELECT * FROM attendance;

INSERT INTO marks
(student_id, subject, marks)
VALUES
(1, 'Java', 85),
(1, 'SQL', 90),
(1, 'Python', 80),
(2, 'Java', 75),
(2, 'SQL', 82),
(2, 'Python', 78),
(3, 'Java', 88),
(3, 'SQL', 91),
(3, 'Python', 86);

SELECT * FROM marks;