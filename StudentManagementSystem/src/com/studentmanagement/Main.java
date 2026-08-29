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
                    attendanceMenu(sc, attendanceDAO, studentDAO);
                    break;

                case 5:
                    marksMenu(sc, marksDAO);
                    break;

                case 6:

                    System.out.print("Enter Student ID: ");
                    int studentId = sc.nextInt();

                    // Student ID validation
                    if (!ValidationUtil.isValidPositiveNumber(studentId)) {
                        System.out.println("Invalid Student ID!");
                        break;
                    }
                    // Check whether student exists
                    if (!studentDAO.studentExists(studentId)) {
                        System.out.println("Student ID not found! Please enter an existing Student ID." );
                                                  
                        break;
                    }
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

                String name;
                String email;
                String phone;

                // Name Validation
                while (true) {

                    System.out.print("Enter Name: ");
                    name = sc.nextLine();

                    if (ValidationUtil.isValidName(name)) {
                        break;
                    }
                    System.out.println( "Invalid name! Use letters and spaces only." );                                       
                }

                // Email Validation
                while (true) {

                    System.out.print("Enter Email: ");
                    email = sc.nextLine();

                    if (ValidationUtil.isValidEmail(email)) {
                        break;
                    }

                    System.out.println("Invalid email! Please enter a valid email.");                                           
                }
                // Phone Validation
                while (true) {

                    System.out.print("Enter Phone: ");
                    phone = sc.nextLine();

                    if (ValidationUtil.isValidPhone(phone)) {
                        break;
                    }
                    System.out.println("Invalid phone! Enter exactly 10 digits.");                                           
                }

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

                    dao.updateStudent(updateId, updateName, updateEmail, updatePhone );                                                                                                                                 
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

                String courseName;
                int duration;

                // Course Name Validation
                while (true) {

                    System.out.print("Enter Course Name: ");
                    courseName = sc.nextLine();

                    if (ValidationUtil.isValidName(courseName)) {
                        break;
                    }
                    System.out.println("Invalid course name! Use letters and spaces only.");                                          
                }

                // Duration Validation
                while (true) {

                    System.out.print("Enter Duration (months): ");
                    duration = sc.nextInt();

                    if (ValidationUtil.isValidDuration(duration)) {
                        break;
                    }
                    System.out.println("Invalid duration! Enter between 1 and 60 months.");                                           
                }

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

                if (!ValidationUtil.isValidPositiveNumber(studentId)) {
                    System.out.println("Invalid Student ID!");
                    break;
                }

                System.out.print("Enter Course ID: ");
                int courseId = sc.nextInt();

                if (!ValidationUtil.isValidPositiveNumber(courseId)) {
                    System.out.println("Invalid Course ID!");
                    break;
                }

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
    public static void attendanceMenu(Scanner sc, AttendanceDAO dao, StudentDAO studentDAO) 
    {
                                  
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

                if (!ValidationUtil.isValidPositiveNumber(studentId)) {
                    System.out.println("Invalid Student ID!");
                    break;
                }

                sc.nextLine();

                String date;

                // Date Validation
                while (true) {

                    System.out.print("Enter Date (YYYY-MM-DD): ");
                    date = sc.nextLine();

                    if (ValidationUtil.isValidDate(date)) {
                        break;
                    }
                    System.out.println("Invalid date! Use format YYYY-MM-DD." );                                          
                }

                String status;

                // Attendance Status Validation
                while (true) {

                    System.out.print("Enter Status (Present/Absent): ");
                    status = sc.nextLine();

                    if (ValidationUtil.isValidAttendanceStatus(status)) {
                        break;
                    }
                    System.out.println("Invalid status! Enter Present or Absent." );                                          
                }
                
                if (!studentDAO.studentExists(studentId)) {
                    System.out.println("Student ID not found! Please enter an existing Student ID.");                                                  
                    break;
                }

                dao.markAttendance(studentId, date, status);                                                         
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

                // Student ID Validation
                System.out.print("Enter Student ID: ");
                int studentId = sc.nextInt();

                if (!ValidationUtil.isValidPositiveNumber(studentId)) {
                    System.out.println("Invalid Student ID!");
                    break;
                }

                // Check whether student exists
                StudentDAO studentDAO = new StudentDAO();

                if (!studentDAO.studentExists(studentId)) {
                    System.out.println("Student ID not found! Please enter an existing Student ID." );                                      
                    break;
                }

                sc.nextLine();

                // Subject Validation
                String subject;

                while (true) {

                    System.out.print("Enter Subject: ");
                    subject = sc.nextLine();

                    if (ValidationUtil.isValidName(subject)) {
                        break;
                    }

                    System.out.println("Invalid subject! Use letters and spaces only.");                 
                }

                // Marks Validation
                int marks;

                while (true) {

                    System.out.print("Enter Marks: ");
                    marks = sc.nextInt();

                    if (ValidationUtil.isValidMarks(marks)) {
                        break;
                    }
                    System.out.println("Invalid marks! Enter marks between 0 and 100.");                                           
                }

                dao.addMarks(studentId, subject, marks);                                                     
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