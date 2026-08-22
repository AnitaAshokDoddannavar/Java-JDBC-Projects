package com.inventorymanagement;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        ProductDAO dao = new ProductDAO();

        while (true) {

            System.out.println("\n========================================");
            System.out.println("       INVENTORY MANAGEMENT SYSTEM");
            System.out.println("========================================");
            System.out.println("1. Add Product");
            System.out.println("2. View Products");
            System.out.println("3. Update Stock");
            System.out.println("4. Record Sale");
            System.out.println("5. Batch Add Products");
            System.out.println("6. Generate Report");
            System.out.println("7. Exit");

            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:

                    sc.nextLine();

                    System.out.print("Enter Product Name: ");
                    String productName = sc.nextLine();

                    System.out.print("Enter Price: ");
                    double price = sc.nextDouble();

                    System.out.print("Enter Quantity: ");
                    int quantity = sc.nextInt();

                    System.out.print("Enter Supplier ID: ");
                    int supplierId = sc.nextInt();

                    dao.addProduct(
                            productName,
                            price,
                            quantity,
                            supplierId
                    );

                    break;

                case 2:

                    dao.viewProducts();
                    break;

                case 3:

                    System.out.print("Enter Product ID: ");
                    int productId = sc.nextInt();

                    System.out.print("Enter New Quantity: ");
                    int newQuantity = sc.nextInt();

                    dao.updateStock(
                            productId,
                            newQuantity
                    );

                    break;

                case 4:

                    System.out.print("Enter Product ID: ");
                    int saleProductId = sc.nextInt();

                    System.out.print("Enter Quantity Sold: ");
                    int quantitySold = sc.nextInt();

                    dao.recordSale(
                            saleProductId,
                            quantitySold
                    );

                    break;

                case 5:

                    dao.addProductsBatch();
                    break;

                case 6:

                    dao.generateReport();
                    break;

                case 7:

                    System.out.println("Thank you for using Inventory Management System!");
                            
                    
                    sc.close();
                    System.exit(0);

                default:

                    System.out.println("Invalid choice!");
            }
        }
    }
}