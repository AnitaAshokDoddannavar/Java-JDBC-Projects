package com.inventorymanagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ProductDAO {

    public void addProduct(String productName, double price, int quantity, int supplierId) 
    {
                           
        String sql = "INSERT INTO products "
                   + "(product_name, price, quantity, supplier_id) "
                   + "VALUES (?, ?, ?, ?)";
        try {

            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, productName);
            ps.setDouble(2, price);
            ps.setInt(3, quantity);
            ps.setInt(4, supplierId);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Product added successfully!");
            }
            ps.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
    
    public void viewProducts() {

        String sql = "SELECT * FROM products";

        try {

            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            System.out.println("----- Product Details -----");
            while (rs.next()) {

                System.out.println("Product ID   : " + rs.getInt("product_id"));
                        
                System.out.println("Product Name : " + rs.getString("product_name"));
                        
                System.out.println("Price        : "  + rs.getDouble("price"));
                      
                System.out.println("Quantity     : " + rs.getInt("quantity"));
                        
                System.out.println("Supplier ID  : " + rs.getInt("supplier_id"));
                        
                System.out.println("---------------------------");
            }
            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
    
    public void updateStock(int productId, int newQuantity) {

        String sql = "UPDATE products SET quantity = ? "
                   + "WHERE product_id = ?";

        try {

            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, newQuantity);
            ps.setInt(2, productId);

            int rows = ps.executeUpdate();
            if (rows > 0) {

                System.out.println("Stock updated successfully!");

            } else {

                System.out.println("Product not found!");
            }
            ps.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
    
    public void recordSale(int productId, int quantitySold) {

        String selectSql = "SELECT price, quantity "
                         + "FROM products WHERE product_id = ?";

        String saleSql = "INSERT INTO sales "
                       + "(product_id, quantity_sold, total_amount, sale_date) "
                       + "VALUES (?, ?, ?, CURDATE())";

        String updateSql = "UPDATE products "
                         + "SET quantity = quantity - ? "
                         + "WHERE product_id = ?";

        Connection con = null;
        PreparedStatement selectPs = null;
        PreparedStatement salePs = null;
        PreparedStatement updatePs = null;

        try {

            con = DBConnection.getConnection();
            selectPs = con.prepareStatement(selectSql);
            selectPs.setInt(1, productId);
            ResultSet rs = selectPs.executeQuery();

            if (rs.next()) {

                double price = rs.getDouble("price");
                int currentQuantity = rs.getInt("quantity");

                if (quantitySold > currentQuantity) {

                    System.out.println("Insufficient stock!");

                    rs.close();
                    selectPs.close();
                    con.close();
                    return;
                }

                double totalAmount = price * quantitySold;

                salePs = con.prepareStatement(saleSql);

                salePs.setInt(1, productId);
                salePs.setInt(2, quantitySold);
                salePs.setDouble(3, totalAmount);

                salePs.executeUpdate();

                updatePs = con.prepareStatement(updateSql);

                updatePs.setInt(1, quantitySold);
                updatePs.setInt(2, productId);

                updatePs.executeUpdate();

                System.out.println("Sale recorded successfully!");
                System.out.println("Total Amount : " + totalAmount);

                rs.close();

            } else {

                System.out.println("Product not found!");
            }

            selectPs.close();

            if (salePs != null) {
                salePs.close();
            }

            if (updatePs != null) {
                updatePs.close();
            }

            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
    
 // Batch Processing - Add Multiple Products
    public void addProductsBatch() {

        String sql = "INSERT INTO products "
                   + "(product_name, price, quantity, supplier_id) "
                   + "VALUES (?, ?, ?, ?)";

        try {

            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            // Product 1
            ps.setString(1, "Headphones");
            ps.setDouble(2, 2000.00);
            ps.setInt(3, 10);
            ps.setInt(4, 1);
            ps.addBatch();

            // Product 2
            ps.setString(1, "USB Cable");
            ps.setDouble(2, 500.00);
            ps.setInt(3, 20);
            ps.setInt(4, 2);
            ps.addBatch();

            // Product 3
            ps.setString(1, "Webcam");
            ps.setDouble(2, 2500.00);
            ps.setInt(3, 15);
            ps.setInt(4, 1);
            ps.addBatch();

            int[] results = ps.executeBatch();

            System.out.println(results.length + " products added using batch processing!");

            ps.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public void generateReport() {

        String sql = "SELECT "
                   + "COUNT(*) AS total_products, "
                   + "SUM(quantity) AS total_stock, "
                   + "AVG(price) AS average_price, "
                   + "MAX(price) AS highest_price, "
                   + "MIN(price) AS lowest_price "
                   + "FROM products";

        try {

            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                int totalProducts =  rs.getInt("total_products");
                      
                int totalStock = rs.getInt("total_stock");
                        
                double averagePrice = rs.getDouble("average_price");
                        
                double highestPrice = rs.getDouble("highest_price");
                        
                double lowestPrice = rs.getDouble("lowest_price");
                        
                System.out.println("----- Inventory Report -----");

                System.out.println("Total Products : " + totalProducts);
                        
                System.out.println("Total Stock    : " + totalStock);
                        
                System.out.println( "Average Price  : " + averagePrice);
                       
                System.out.println("Highest Price  : " + highestPrice);
                    
                System.out.println("Lowest Price   : " + lowestPrice);
                        
                System.out.println( "----------------------------");
                       
            }
            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
    
 // Check whether Supplier ID exists
    public boolean supplierExists(int supplierId) {

        String sql = "SELECT supplier_id FROM suppliers WHERE supplier_id = ?";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, supplierId);
            ResultSet rs = ps.executeQuery();

            boolean exists = rs.next();

            rs.close();
            ps.close();
            con.close();

            return exists;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}