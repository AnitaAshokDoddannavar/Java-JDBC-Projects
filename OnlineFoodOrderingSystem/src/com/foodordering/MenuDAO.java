package com.foodordering;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MenuDAO {

    // View Food Menu
    public void viewMenu() {

        String sql = "SELECT * FROM menu WHERE available = true";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            System.out.println("========== FOOD MENU ==========");

            while (rs.next()) {

                System.out.println(
                        "Food ID   : "
                        + rs.getInt("food_id"));

                System.out.println(
                        "Food Name : "
                        + rs.getString("food_name"));

                System.out.println(
                        "Category  : "
                        + rs.getString("category"));

                System.out.println(
                        "Price     : ₹"
                        + rs.getDouble("price"));

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