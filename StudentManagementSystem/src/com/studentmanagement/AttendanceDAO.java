package com.studentmanagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AttendanceDAO {

    // 1. Mark Attendance
    public void markAttendance(int studentId, String attendanceDate, String status) {

        String sql = "INSERT INTO attendance "
                   + "(student_id, attendance_date, status) "
                   + "VALUES (?, ?, ?)";

        try {

            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, studentId);
            ps.setString(2, attendanceDate);
            ps.setString(3, status);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Attendance marked successfully!");
            }

            ps.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
    
 // 2. View Attendance
    public void viewAttendance() {

        String sql = "SELECT * FROM attendance";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            System.out.println("----- Attendance Details -----");

            while (rs.next()) {

                System.out.println("Attendance ID   : " + rs.getInt("attendance_id"));
                
                System.out.println("Student ID      : " + rs.getInt("student_id"));
                        
                System.out.println("Attendance Date : " + rs.getDate("attendance_date"));
                        
                System.out.println("Status          : " + rs.getString("status"));
                        
                System.out.println("-------------------------------");
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
    
 // 3. View Attendance with Student Name
    public void viewAttendanceWithStudentName() {

        String sql = "SELECT a.attendance_id, s.student_name, "
                   + "a.attendance_date, a.status "
                   + "FROM attendance a "
                   + "JOIN students s ON a.student_id = s.student_id "
                   + "ORDER BY a.attendance_id";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            System.out.println("----- Attendance Details -----");

            while (rs.next()) {

                System.out.println("Attendance ID   : " + rs.getInt("attendance_id"));
                        
                System.out.println("Student Name    : "  + rs.getString("student_name"));
                    
                System.out.println("Attendance Date : " + rs.getDate("attendance_date"));
                       
                System.out.println("Status          : " + rs.getString("status"));
                        
                System.out.println("-------------------------------");
            }
            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}