package com.studentmanagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StudentDAO {

    // 1. Add Student
    public void addStudent(String name, String email, String phone) {

        String sql = "INSERT INTO students " +
                     "(student_name, email, phone) VALUES (?, ?, ?)";

        try {

            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, phone);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Student added successfully!");
            }
            ps.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    // 2. View Students
    public void viewStudents() {

        String sql = "SELECT * FROM students";

        try {

            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            System.out.println("----- Student Details -----");

            while (rs.next()) {

                System.out.println("Student ID   : " + rs.getInt("student_id"));

                System.out.println("Name         : " + rs.getString("student_name"));
                        
                System.out.println("Email        : " + rs.getString("email"));
                        
                System.out.println("Phone        : " + rs.getString("phone"));
                        
                System.out.println("---------------------------");
            }
            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
 // 3. Update Student
    public void updateStudent(int studentId, String name, String email, String phone) {

        String sql = "UPDATE students SET student_name = ?, email = ?, phone = ? "
                   + "WHERE student_id = ?";

        try {

            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, phone);
            ps.setInt(4, studentId);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Student updated successfully!");
            } else {
                System.out.println("Student ID not found.");
            }

            ps.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
    
 // 4. Delete Student
    public void deleteStudent(int studentId) {

        String sql = "DELETE FROM students WHERE student_id = ?";

        try {

            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, studentId);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Student deleted successfully!");
            } else {
                System.out.println("Student ID not found.");
            }

            ps.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
    
    public boolean studentExists(int studentId) {

        String sql = "SELECT student_id FROM students WHERE student_id = ?";

        try {

            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, studentId);

            ResultSet rs = ps.executeQuery();

            boolean exists = rs.next();

            rs.close();
            ps.close();
            con.close();

            return exists;

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }
}