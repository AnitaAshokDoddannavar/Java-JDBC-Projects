package com.foodordering;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection getConnection() {

        Connection con = null;

        try {

            String url =
                    "jdbc:mysql://localhost:3307/food_ordering";

            String username = "root";
            String password = "YOUR_MYSQL_PASSWORD";

            con = DriverManager.getConnection(
                    url,
                    username,
                    password
            );

        } catch (Exception e) {

            e.printStackTrace();
        }

        return con;
    }
}