package com.inventorymanagement;

import java.sql.Connection;

public class ConnectionTest {

    public static void main(String[] args) {

        Connection con = DBConnection.getConnection();

        if (con != null) {

            System.out.println("Connection successful!");

        } else {

            System.out.println("Connection failed!");
        }
    }
}