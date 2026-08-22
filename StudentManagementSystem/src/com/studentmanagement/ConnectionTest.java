package com.studentmanagement;

import java.sql.Connection;

public class ConnectionTest {

    public static void main(String[] args) {

        try {

            Connection con = DBConnection.getConnection();

            System.out.println("Database connected successfully!");

            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
