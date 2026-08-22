package com.studentmanagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CourseDAO {

    // 1. Add Course
    public void addCourse(String courseName, int duration) {

        String sql = "INSERT INTO courses (course_name, duration) VALUES (?, ?)";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, courseName);
            ps.setInt(2, duration);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Course added successfully!");
            }

            ps.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
    
 // 2. View Courses
    public void viewCourses() {

        String sql = "SELECT * FROM courses";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            System.out.println("----- Course Details -----");

            while (rs.next()) {

                System.out.println("Course ID   : "
                        + rs.getInt("course_id"));

                System.out.println("Course Name : "
                        + rs.getString("course_name"));

                System.out.println("Duration    : "
                        + rs.getInt("duration") + " months");

                System.out.println("--------------------------");
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
    
 // 3. Enroll Student in Course
    public void enrollStudent(int studentId, int courseId) {

        String sql = "INSERT INTO enrollments " +
                     "(student_id, course_id, enrollment_date) " +
                     "VALUES (?, ?, CURDATE())";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, studentId);
            ps.setInt(2, courseId);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Student enrolled successfully!");
            }

            ps.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
    
 // 4. View Enrollment Details
    public void viewEnrollments() {

        String sql = "SELECT e.enrollment_id, s.student_name, "
                   + "c.course_name, e.enrollment_date "
                   + "FROM enrollments e "
                   + "JOIN students s ON e.student_id = s.student_id "
                   + "JOIN courses c ON e.course_id = c.course_id "
                   + "ORDER BY e.enrollment_id";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            System.out.println("----- Enrollment Details -----");

            while (rs.next()) {

                System.out.println("Enrollment ID   : "
                        + rs.getInt("enrollment_id"));

                System.out.println("Student Name    : "
                        + rs.getString("student_name"));

                System.out.println("Course Name     : "
                        + rs.getString("course_name"));

                System.out.println("Enrollment Date : "
                        + rs.getDate("enrollment_date"));

                System.out.println("------------------------------");
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}