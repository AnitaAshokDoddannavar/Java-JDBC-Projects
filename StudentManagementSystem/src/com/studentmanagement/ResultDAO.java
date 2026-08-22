package com.studentmanagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ResultDAO {

    // Generate Result
    public void generateResult(int studentId) {

        String sql = "SELECT s.student_name, "
                   + "SUM(m.marks) AS total_marks, "
                   + "AVG(m.marks) AS average_marks "
                   + "FROM students s "
                   + "JOIN marks m ON s.student_id = m.student_id "
                   + "WHERE s.student_id = ? "
                   + "GROUP BY s.student_id, s.student_name";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, studentId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                String studentName = rs.getString("student_name");

                int totalMarks = rs.getInt("total_marks");

                double averageMarks = rs.getDouble("average_marks");

                String grade;

                if (averageMarks >= 90) {
                    grade = "A";
                } else if (averageMarks >= 75) {
                    grade = "B";
                } else if (averageMarks >= 60) {
                    grade = "C";
                } else if (averageMarks >= 50) {
                    grade = "D";
                } else {
                    grade = "F";
                }

                System.out.println("----- Student Result -----");
                System.out.println("Student Name : " + studentName);
                System.out.println("Total Marks  : " + totalMarks);
                System.out.println("Average      : " + averageMarks);
                System.out.println("Grade        : " + grade);
                System.out.println("--------------------------");

            } else {

                System.out.println("No marks found for this student.");
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}