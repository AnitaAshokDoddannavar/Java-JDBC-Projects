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
                String productName;

                while (true) {

                    System.out.print("Enter Product Name: ");
                    productName = sc.nextLine();

                    if (ValidationUtil.isValidProductName(productName)) {
                        break;
                    }

                    System.out.println("Invalid product name! Use letters, numbers and spaces only.");                          
                }

                double price;

                while (true) {

                    System.out.print("Enter Price: ");
                    price = sc.nextDouble();

                    if (ValidationUtil.isValidPrice(price)) {
                        break;
                    }
                    System.out.println("Invalid price! Price must be greater than 0.");
                            
                }

                int quantity;

                while (true) {

                    System.out.print("Enter Quantity: ");
                    quantity = sc.nextInt();

                    if (ValidationUtil.isValidQuantity(quantity)) {
                        break;
                    }
                    System.out.println("Invalid quantity! Quantity must be greater than 0.");                        
                }

                int supplierId;

                while (true) {

                    System.out.print("Enter Supplier ID: ");
                    supplierId = sc.nextInt();

                    if (!ValidationUtil.isValidId(supplierId)) {
                        System.out.println("Invalid Supplier ID! Enter a positive number.");        
                        continue;
                    }

                    if (!dao.supplierExists(supplierId)) {
                        System.out.println("Supplier ID not found! Please enter an existing Supplier ID.");                                
                        continue;
                    }
                    break;
                }
                dao.addProduct(productName, price, quantity, supplierId );                                                                                                          
                break;

                
            case 2:

                    dao.viewProducts();
                    break;
          
                 
            case 3:

                int productId;
                while (true) {

                    System.out.print("Enter Product ID: ");
                    productId = sc.nextInt();

                    if (ValidationUtil.isValidId(productId)) {
                        break;
                    }
                    System.out.println("Invalid Product ID! Enter a positive number.");                          
                }

                int newQuantity;

                while (true) {

                    System.out.print("Enter New Quantity: ");
                    newQuantity = sc.nextInt();

                    if (ValidationUtil.isValidQuantity(newQuantity)) {
                        break;
                    }
                    System.out.println("Invalid quantity! Quantity must be greater than 0.");                          
                }
                dao.updateStock( productId, newQuantity );                                                           
                break;
           
            case 4:

                int saleProductId;

                while (true) {

                    System.out.print("Enter Product ID: ");
                    saleProductId = sc.nextInt();

                    if (ValidationUtil.isValidId(saleProductId)) {
                        break;
                    }
                    System.out.println("Invalid Product ID! Enter a positive number.");                         
                }

                int quantitySold;

                while (true) {

                    System.out.print("Enter Quantity Sold: ");
                    quantitySold = sc.nextInt();

                    if (ValidationUtil.isValidQuantity(quantitySold)) {
                        break;
                    }
                    System.out.println("Invalid quantity! Quantity sold must be greater than 0.");                         
                }
                dao.recordSale(saleProductId, quantitySold);                                      
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
                break;


            default:
                System.out.println("Invalid choice!");
            }
        }
    }
}