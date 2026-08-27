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

	        // 1. Customer ID Validation
	        System.out.print("Enter Customer ID: ");
	        int customerId = sc.nextInt();

	        if (!ValidationUtil.isValidPositiveNumber(customerId)) {

	            System.out.println("Invalid Customer ID!");
	            return;
	        }

	        // Check whether customer exists
	        String customerSql = "SELECT customer_id FROM customers WHERE customer_id = ?";
       
	        PreparedStatement customerPs = con.prepareStatement(customerSql);
            
	        customerPs.setInt(1, customerId);

	        ResultSet customerRs = customerPs.executeQuery();

	        if (!customerRs.next()) {

	            System.out.println("Customer ID not found!");
	                    
	            customerRs.close();
	            customerPs.close();

	            con.rollback();
	            return;
	        }

	        customerRs.close();
	        customerPs.close();

	        // 2. Number of Items Validation

	        System.out.print("How many different food items do you want to order? ");
	                
	        int itemCount = sc.nextInt();

	        if (!ValidationUtil.isValidPositiveNumber(itemCount)) {

	            System.out.println("Number of food items must be greater than 0.");
	            con.rollback();
	            return;
	        }

	        // Store Food Details
	        double totalAmount = 0;

	        int[] foodIds = new int[itemCount];
	        int[] quantities = new int[itemCount];
	        double[] prices = new double[itemCount];

	        
	        // 3. Food ID & Quantity Validation
	   
	        for (int i = 0; i < itemCount; i++) {

	            System.out.println( "\nFood Item " + (i + 1));
	                   

	            // Food ID
	            System.out.print("Enter Food ID: ");
	            int foodId = sc.nextInt();

	            if (!ValidationUtil.isValidPositiveNumber(foodId)) {

	                System.out.println("Invalid Food ID!");
	                con.rollback();
	                return;
	            }

	            foodIds[i] = foodId;

	            // Quantity
	            System.out.print("Enter Quantity: ");
	            int quantity = sc.nextInt();

	            if (!ValidationUtil.isValidPositiveNumber(quantity)) {

	                System.out.println( "Quantity must be greater than 0.");
	                con.rollback();
	                return;
	            }

	            quantities[i] = quantity;

	            // Check Food Availability

	            String priceSql =
	                    "SELECT price FROM menu " +
	                    "WHERE food_id = ? " +
	                    "AND available = true";

	            PreparedStatement pricePs = con.prepareStatement(priceSql);
 
	            pricePs.setInt(1, foodIds[i]);

	            ResultSet rs = pricePs.executeQuery();

	            if (rs.next()) {

	                prices[i] =  rs.getDouble("price");                

	                totalAmount = totalAmount  + (prices[i] * quantities[i]);
	                                     

	            } else {

	                System.out.println("Food item not available!");
	                rs.close();
	                pricePs.close();

	                con.rollback();
	                return;
	            }

	            rs.close();
	            pricePs.close();
	        }

	        // 4. Insert Master Record
	       
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
	                    
	            // 5. Insert Detail Records
	           
	            String itemSql =
	                    "INSERT INTO order_items " +
	                    "(order_id, food_id, quantity, item_price) " +
	                    "VALUES (?, ?, ?, ?)";

	            itemPs =  con.prepareStatement(itemSql);
	                  
	            for (int i = 0; i < itemCount; i++) {

	                itemPs.setInt(1, orderId);
	                itemPs.setInt(2, foodIds[i]);
	                itemPs.setInt(3, quantities[i]);
	                itemPs.setDouble(4, prices[i]);

	                itemPs.executeUpdate();
	            }

	            con.commit();
	            System.out.println("\nTransaction committed successfully!");	                  
	            System.out.println("\nOrder placed successfully!");	                   
	            System.out.println( "Order ID     : " + orderId);                   
	            System.out.println( "Total Amount : ₹" + totalAmount);	            
	            System.out.println("Order Status : PLACED");
	                   
	        }
	        generatedKeys.close();


	    } catch (Exception e) {

	        try {

	            if (con != null) {

	                con.rollback();
	                System.out.println("Transaction rolled back!");
	                        
	            }

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
	
	// Update Order Status
	public void updateOrderStatus(int orderId, String status) {

	    String sql =
	            "UPDATE orders SET order_status = ? " +
	            "WHERE order_id = ?";

	    try {

	        Connection con = DBConnection.getConnection();

	        PreparedStatement ps = con.prepareStatement(sql);

	        ps.setString(1, status);
	        ps.setInt(2, orderId);

	        int rows = ps.executeUpdate();

	        if (rows > 0) {

	            System.out.println("Order status updated successfully!");
	                    
	            System.out.println("Order ID     : " + orderId);
	                    
	            System.out.println("Current Status : " + status);
	                    

	        } else {

	            System.out.println( "Order ID not found!");
	                   
	        }

	        ps.close();
	        con.close();

	    } catch (Exception e) {

	        e.printStackTrace();
	    }
	}
	
	// Track Order Status
	public void trackOrderStatus(int orderId) {

	    String sql =
	            "SELECT o.order_id, c.customer_name, " +
	            "o.order_date, o.total_amount, o.order_status " +
	            "FROM orders o " +
	            "JOIN customers c " +
	            "ON o.customer_id = c.customer_id " +
	            "WHERE o.order_id = ?";

	    try {

	        Connection con = DBConnection.getConnection();

	        PreparedStatement ps = con.prepareStatement(sql);

	        ps.setInt(1, orderId);

	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {

	            System.out.println("\n========== ORDER STATUS ==========");
	                    
	            System.out.println("Order ID       : " + rs.getInt("order_id"));
	                    	                    
	            System.out.println("Customer       : " + rs.getString("customer_name"));
	                                       
	            System.out.println("Order Date     : " + rs.getDate("order_date"));
	                    	                    
	            System.out.println("Total Amount   : ₹" + rs.getDouble("total_amount"));
	                    	                   
	            System.out.println( "Order Status   : " + rs.getString("order_status"));
	                   	                    
	            System.out.println("==================================");
	                    
	        } else {

	            System.out.println( "Order ID not found!");
	                   
	        }

	        rs.close();
	        ps.close();
	        con.close();

	    } catch (Exception e) {

	        e.printStackTrace();
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
         
	        System.out.println("========== ORDER HISTORY ==========");
	                
	        boolean found = false;

	        while (rs.next()) {

	            found = true;

	            System.out.println("Order ID     : " + rs.getInt("order_id"));
	                    	                    
	            System.out.println("Order Date   : " + rs.getDate("order_date"));
	                    	                    
	            System.out.println( "Food Name    : " + rs.getString("food_name"));
	                   	                    
	            System.out.println("Quantity     : " + rs.getInt("quantity"));
	                    	                    
	            System.out.println( "Item Price   : ₹" + rs.getDouble("item_price"));
	                   	                    
	            System.out.println( "Total Amount : ₹" + rs.getDouble("total_amount"));
	                   	                    
	            System.out.println( "-----------------------------------");
	                   
	        }
	        if (!found) {

	            System.out.println("No orders found for this customer.");
	                    
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

	        PreparedStatement ps = con.prepareStatement(sql);
           
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

	                System.out.println("Order ID : " + rs.getInt("order_id"));
	                        	                        
	                System.out.println("Customer : " + rs.getString("customer_name"));
	                        	                        
	                System.out.println("Date     : " + rs.getDate("order_date"));
	                        	                        
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
	            System.out.println("Total Amount = ₹" + totalAmount);	                    
	            System.out.println( "====================================");
	                   
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
	// View All Orders - Admin
	public void viewAllOrders() {

	    String sql =
	            "SELECT o.order_id, c.customer_name, " +
	            "o.order_date, o.total_amount, " +
	            "o.order_status " +
	            "FROM orders o " +
	            "JOIN customers c " +
	            "ON o.customer_id = c.customer_id " +
	            "ORDER BY o.order_id";

	    try {

	        Connection con = DBConnection.getConnection();
	                
	        PreparedStatement ps = con.prepareStatement(sql);
	                
	        ResultSet rs = ps.executeQuery();
	                
	        System.out.println("\n========== ALL ORDERS ==========");
	                
	        boolean found = false;

	        while (rs.next()) {

	            found = true;

	            System.out.println("Order ID     : "  + rs.getInt("order_id"));
	                                  
	            System.out.println("Customer     : " + rs.getString("customer_name"));
	                    	                    
	            System.out.println("Order Date   : " + rs.getDate("order_date"));
	                    	                   
	            System.out.println("Total Amount : ₹" + rs.getDouble("total_amount"));
	                    	                    
	            System.out.println( "Status       : "  + rs.getString("order_status"));
	                   	                  
	            System.out.println("--------------------------------");
	                    
	        }
	        if (!found) {

	            System.out.println( "No orders found.");
	                   
	        }
	        rs.close();
	        ps.close();
	        con.close();

	    } catch (Exception e) {

	        e.printStackTrace();
	    }
	}
}