package com.studentmanagement;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        StudentDAO studentDAO = new StudentDAO();
        CourseDAO courseDAO = new CourseDAO();
        AttendanceDAO attendanceDAO = new AttendanceDAO();
        MarksDAO marksDAO = new MarksDAO();
        ResultDAO resultDAO = new ResultDAO();

        while (true) {

            System.out.println("\n======================================");
            System.out.println("      STUDENT MANAGEMENT SYSTEM");
            System.out.println("======================================");
            System.out.println("1. Student Registration");
            System.out.println("2. Course Management");
            System.out.println("3. Course Enrollment");
            System.out.println("4. Attendance Management");
            System.out.println("5. Marks Entry");
            System.out.println("6. Generate Result");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    studentMenu(sc, studentDAO);
                    break;

                case 2:
                    courseMenu(sc, courseDAO);
                    break;

                case 3:
                    enrollmentMenu(sc, courseDAO);
                    break;

                case 4:
                    attendanceMenu(sc, attendanceDAO);
                    break;

                case 5:
                    marksMenu(sc, marksDAO);
                    break;

                case 6:
                    System.out.print("Enter Student ID: ");
                    int studentId = sc.nextInt();

                    resultDAO.generateResult(studentId);
                    break;

                case 7:
                    System.out.println("Thank you!");
                    sc.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    // Student Menu
    public static void studentMenu(Scanner sc, StudentDAO dao) {

        while (true) {

            System.out.println("\n----- Student Registration -----");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Back");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:

                    sc.nextLine();

                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Email: ");
                    String email = sc.nextLine();

                    System.out.print("Enter Phone: ");
                    String phone = sc.nextLine();

                    dao.addStudent(name, email, phone);
                    break;

                case 2:

                    dao.viewStudents();
                    break;

                case 3:

                    System.out.print("Enter Student ID: ");
                    int updateId = sc.nextInt();

                    sc.nextLine();

                    System.out.print("Enter New Name: ");
                    String updateName = sc.nextLine();

                    System.out.print("Enter New Email: ");
                    String updateEmail = sc.nextLine();

                    System.out.print("Enter New Phone: ");
                    String updatePhone = sc.nextLine();

                    dao.updateStudent(
                            updateId,
                            updateName,
                            updateEmail,
                            updatePhone
                    );

                    break;

                case 4:

                    System.out.print("Enter Student ID: ");
                    int deleteId = sc.nextInt();

                    dao.deleteStudent(deleteId);
                    break;

                case 5:
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    // Course Menu
    public static void courseMenu(Scanner sc, CourseDAO dao) {

        while (true) {

            System.out.println("\n----- Course Management -----");
            System.out.println("1. Add Course");
            System.out.println("2. View Courses");
            System.out.println("3. Back");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:

                    sc.nextLine();

                    System.out.print("Enter Course Name: ");
                    String courseName = sc.nextLine();

                    System.out.print("Enter Duration (months): ");
                    int duration = sc.nextInt();

                    dao.addCourse(courseName, duration);
                    break;

                case 2:

                    dao.viewCourses();
                    break;

                case 3:
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    // Enrollment Menu
    public static void enrollmentMenu(Scanner sc, CourseDAO dao) {

        while (true) {

            System.out.println("\n----- Course Enrollment -----");
            System.out.println("1. Enroll Student");
            System.out.println("2. View Enrollments");
            System.out.println("3. Back");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:

                    System.out.print("Enter Student ID: ");
                    int studentId = sc.nextInt();

                    System.out.print("Enter Course ID: ");
                    int courseId = sc.nextInt();

                    dao.enrollStudent(studentId, courseId);
                    break;

                case 2:

                    dao.viewEnrollments();
                    break;

                case 3:
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    // Attendance Menu
    public static void attendanceMenu(
            Scanner sc,
            AttendanceDAO dao) {

        while (true) {

            System.out.println("\n----- Attendance Management -----");
            System.out.println("1. Mark Attendance");
            System.out.println("2. View Attendance");
            System.out.println("3. View Attendance with Student Name");
            System.out.println("4. Back");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:

                    System.out.print("Enter Student ID: ");
                    int studentId = sc.nextInt();

                    sc.nextLine();

                    System.out.print("Enter Date (YYYY-MM-DD): ");
                    String date = sc.nextLine();

                    System.out.print("Enter Status (Present/Absent): ");
                    String status = sc.nextLine();

                    dao.markAttendance(
                            studentId,
                            date,
                            status
                    );

                    break;

                case 2:

                    dao.viewAttendance();
                    break;

                case 3:

                    dao.viewAttendanceWithStudentName();
                    break;

                case 4:
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    // Marks Menu
    public static void marksMenu(Scanner sc, MarksDAO dao) {

        while (true) {

            System.out.println("\n----- Marks Management -----");
            System.out.println("1. Add Marks");
            System.out.println("2. View Marks");
            System.out.println("3. View Marks with Student Name");
            System.out.println("4. Back");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:

                    System.out.print("Enter Student ID: ");
                    int studentId = sc.nextInt();

                    sc.nextLine();

                    System.out.print("Enter Subject: ");
                    String subject = sc.nextLine();

                    System.out.print("Enter Marks: ");
                    int marks = sc.nextInt();

                    dao.addMarks(
                            studentId,
                            subject,
                            marks
                    );

                    break;

                case 2:

                    dao.viewMarks();
                    break;

                case 3:

                    dao.viewMarksWithStudentName();
                    break;

                case 4:
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}