package com.studentmanagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MarksDAO {

    // 1. Add Marks
    public void addMarks(int studentId, String subject, int marks) {

        String sql = "INSERT INTO marks (student_id, subject, marks) VALUES (?, ?, ?)";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, studentId);
            ps.setString(2, subject);
            ps.setInt(3, marks);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Marks added successfully!");
            }

            ps.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
    
 // 2. View Marks
    public void viewMarks() {

        String sql = "SELECT * FROM marks";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            System.out.println("----- Marks Details -----");

            while (rs.next()) {

                System.out.println("Mark ID     : "
                        + rs.getInt("mark_id"));

                System.out.println("Student ID  : "
                        + rs.getInt("student_id"));

                System.out.println("Subject     : "
                        + rs.getString("subject"));

                System.out.println("Marks       : "
                        + rs.getInt("marks"));

                System.out.println("-------------------------");
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
    
 // 3. View Marks with Student Name
    public void viewMarksWithStudentName() {

        String sql = "SELECT m.mark_id, s.student_name, "
                   + "m.subject, m.marks "
                   + "FROM marks m "
                   + "JOIN students s ON m.student_id = s.student_id "
                   + "ORDER BY m.mark_id";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            System.out.println("----- Marks Details -----");

            while (rs.next()) {

                System.out.println("Mark ID     : "
                        + rs.getInt("mark_id"));

                System.out.println("Student Name: "
                        + rs.getString("student_name"));

                System.out.println("Subject     : "
                        + rs.getString("subject"));

                System.out.println("Marks       : "
                        + rs.getInt("marks"));

                System.out.println("-------------------------");
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}