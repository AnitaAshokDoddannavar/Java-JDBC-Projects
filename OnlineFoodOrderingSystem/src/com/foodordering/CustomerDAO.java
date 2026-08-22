package com.foodordering;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CustomerDAO {

    // Add Customer
    public void addCustomer(String name, String email, String phone) {

        String sql = "INSERT INTO customers "
                   + "(customer_name, email, phone) "
                   + "VALUES (?, ?, ?)";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, phone);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Customer added successfully!");
            }

            ps.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    // View Customers
    public void viewCustomers() {

        String sql = "SELECT * FROM customers";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            System.out.println("----- Customer Details -----");

            while (rs.next()) {

                System.out.println(
                        "Customer ID   : "
                        + rs.getInt("customer_id"));

                System.out.println(
                        "Name          : "
                        + rs.getString("customer_name"));

                System.out.println(
                        "Email         : "
                        + rs.getString("email"));

                System.out.println(
                        "Phone         : "
                        + rs.getString("phone"));

                System.out.println("----------------------------");
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}