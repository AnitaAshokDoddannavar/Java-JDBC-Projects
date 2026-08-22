package com.studentmanagement;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class TransactionDAO {

    public void enrollStudentTransaction(
            int studentId,
            int courseId) {

        String sql = "INSERT INTO enrollments "
                   + "(student_id, course_id, enrollment_date) "
                   + "VALUES (?, ?, CURDATE())";

        Connection con = null;
        PreparedStatement ps = null;

        try {

            con = DBConnection.getConnection();

            // Start transaction
            con.setAutoCommit(false);

            ps = con.prepareStatement(sql);

            ps.setInt(1, studentId);
            ps.setInt(2, courseId);

            ps.executeUpdate();

            // Save the transaction
            con.commit();

            System.out.println("Enrollment transaction committed successfully!");
                    

        } catch (Exception e) {

            try {

                if (con != null) {
                    con.rollback();
                }

                System.out.println("Transaction rolled back.");
                        

            } catch (Exception rollbackException) {

                rollbackException.printStackTrace();
            }

            e.printStackTrace();

        } finally {

            try {

                if (ps != null) {
                    ps.close();
                }

                if (con != null) {
                    con.close();
                }

            } catch (Exception closeException) {

                closeException.printStackTrace();
            }
        }
    }
}