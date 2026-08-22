package com.foodordering;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class OrderDAO {

	public void placeOrder() {

	    Scanner sc = new Scanner(System.in);

	    Connection con = null;
	    PreparedStatement orderPs = null;
	    PreparedStatement itemPs = null;

	    try {

	        con = DBConnection.getConnection();
	        con.setAutoCommit(false);

	        System.out.print("Enter Customer ID: ");
	        int customerId = sc.nextInt();

	        System.out.print("How many different food items do you want to order? ");
	        int itemCount = sc.nextInt();

	        // Calculate total first
	        double totalAmount = 0;

	        // Store food details temporarily
	        int[] foodIds = new int[itemCount];
	        int[] quantities = new int[itemCount];
	        double[] prices = new double[itemCount];

	        for (int i = 0; i < itemCount; i++) {

	            System.out.println("\nFood Item " + (i + 1));

	            System.out.print("Enter Food ID: ");
	            foodIds[i] = sc.nextInt();

	            System.out.print("Enter Quantity: ");
	            quantities[i] = sc.nextInt();

	            String priceSql =
	                    "SELECT price FROM menu " +
	                    "WHERE food_id = ? AND available = true";

	            PreparedStatement pricePs = con.prepareStatement(priceSql);
	                    

	            pricePs.setInt(1, foodIds[i]);

	            ResultSet rs = pricePs.executeQuery();

	            if (rs.next()) {

	                prices[i] = rs.getDouble("price");

	                totalAmount = totalAmount + (prices[i] * quantities[i]);

	                        
	            } else {

	            	System.out.println("Food item not available!");

	            	con.rollback();

	            	rs.close();
	            	pricePs.close();
	            	return;
	            }

	            rs.close();
	            pricePs.close();
	        }

	        // Insert Master record
	        String orderSql =
	                "INSERT INTO orders " +
	                "(customer_id, order_date, total_amount) " +
	                "VALUES (?, CURDATE(), ?)";

	        orderPs = con.prepareStatement(
	                orderSql,
	                java.sql.Statement.RETURN_GENERATED_KEYS
	        );

	        orderPs.setInt(1, customerId);
	        orderPs.setDouble(2, totalAmount);

	        orderPs.executeUpdate();

	        ResultSet generatedKeys = orderPs.getGeneratedKeys();
	                

	        if (generatedKeys.next()) {

	            int orderId = generatedKeys.getInt(1);

	            // Insert Detail records
	            String itemSql =
	                    "INSERT INTO order_items " +
	                    "(order_id, food_id, quantity, item_price) " +
	                    "VALUES (?, ?, ?, ?)";

	            itemPs = con.prepareStatement(itemSql);

	            for (int i = 0; i < itemCount; i++) {

	                itemPs.setInt(1, orderId);
	                itemPs.setInt(2, foodIds[i]);
	                itemPs.setInt(3, quantities[i]);
	                itemPs.setDouble(4, prices[i]);

	                itemPs.executeUpdate();
	                
	                
	            }
	            
	            con.commit();

	            System.out.println("Transaction committed successfully!");

	            System.out.println("\nOrder placed successfully!");
	                    

	            System.out.println("Order ID     : " + orderId);
	                    

	            System.out.println("Total Amount : ₹" + totalAmount);
	                    
	        }

	        generatedKeys.close();

	    }  catch (Exception e) {

	        try {

	            if (con != null) {
	                con.rollback();
	            }

	            System.out.println("Transaction rolled back!");

	        } catch (Exception rollbackException) {

	            rollbackException.printStackTrace();
	        }

	        e.printStackTrace();
	    } finally {

	        try {

	            if (itemPs != null) {
	                itemPs.close();
	            }

	            if (orderPs != null) {
	                orderPs.close();
	            }

	            if (con != null) {
	                con.close();
	            }

	        } catch (Exception e) {

	            e.printStackTrace();
	        }
	    }
	}
	
	// View Order History
	public void viewOrderHistory(int customerId) {

	    String sql =
	            "SELECT o.order_id, o.order_date, "
	          + "m.food_name, oi.quantity, "
	          + "oi.item_price, o.total_amount "
	          + "FROM orders o "
	          + "JOIN order_items oi "
	          + "ON o.order_id = oi.order_id "
	          + "JOIN menu m "
	          + "ON oi.food_id = m.food_id "
	          + "WHERE o.customer_id = ? "
	          + "ORDER BY o.order_id";

	    try {

	        Connection con = DBConnection.getConnection();

	        PreparedStatement ps = con.prepareStatement(sql);
	        
	        ps.setInt(1, customerId);

	        ResultSet rs = ps.executeQuery();
        

	        
	        System.out.println(
	                "========== ORDER HISTORY ==========");

	        boolean found = false;

	        while (rs.next()) {

	            found = true;

	            System.out.println(
	                    "Order ID     : "
	                    + rs.getInt("order_id"));

	            System.out.println(
	                    "Order Date   : "
	                    + rs.getDate("order_date"));

	            System.out.println(
	                    "Food Name    : "
	                    + rs.getString("food_name"));

	            System.out.println(
	                    "Quantity     : "
	                    + rs.getInt("quantity"));

	            System.out.println(
	                    "Item Price   : ₹"
	                    + rs.getDouble("item_price"));

	            System.out.println(
	                    "Total Amount : ₹"
	                    + rs.getDouble("total_amount"));

	            System.out.println(
	                    "-----------------------------------");
	        }

	        if (!found) {

	            System.out.println(
	                    "No orders found for this customer.");
	        }

	        rs.close();
	        ps.close();
	        con.close();

	    } catch (Exception e) {

	        e.printStackTrace();
	    }
	}
	
	// Generate Bill
	public void generateBill(int orderId) {

	    String sql =
	            "SELECT o.order_id, c.customer_name, "
	          + "o.order_date, m.food_name, "
	          + "oi.quantity, oi.item_price, "
	          + "(oi.quantity * oi.item_price) AS item_total, "
	          + "o.total_amount "
	          + "FROM orders o "
	          + "JOIN customers c "
	          + "ON o.customer_id = c.customer_id "
	          + "JOIN order_items oi "
	          + "ON o.order_id = oi.order_id "
	          + "JOIN menu m "
	          + "ON oi.food_id = m.food_id "
	          + "WHERE o.order_id = ?";

	    try {

	        Connection con = DBConnection.getConnection();

	        PreparedStatement ps =
	                con.prepareStatement(sql);

	        ps.setInt(1, orderId);

	        ResultSet rs = ps.executeQuery();

	        boolean found = false;
	        double totalAmount = 0;

	        System.out.println("\n====================================");
	        System.out.println("              FOOD BILL");
	        System.out.println("====================================");

	        while (rs.next()) {

	            if (!found) {

	                found = true;

	                System.out.println(
	                        "Order ID : "
	                        + rs.getInt("order_id"));

	                System.out.println(
	                        "Customer : "
	                        + rs.getString("customer_name"));

	                System.out.println(
	                        "Date     : "
	                        + rs.getDate("order_date"));

	                System.out.println("------------------------------------");
	            }

	            System.out.println(
	                    rs.getString("food_name")
	                    + "  "
	                    + rs.getInt("quantity")
	                    + " × ₹"
	                    + rs.getDouble("item_price")
	                    + " = ₹"
	                    + rs.getDouble("item_total"));

	            // Store total amount
	            totalAmount = rs.getDouble("total_amount");
	        }

	        if (found) {

	            System.out.println("------------------------------------");

	            System.out.println(
	                    "Total Amount = ₹" + totalAmount);

	            System.out.println(
	                    "====================================");

	        } else {

	            System.out.println("Order not found.");
	        }

	        rs.close();
	        ps.close();
	        con.close();

	    } catch (Exception e) {

	        e.printStackTrace();
	    }
	}
}