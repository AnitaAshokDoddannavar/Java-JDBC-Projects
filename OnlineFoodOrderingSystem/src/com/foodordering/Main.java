package com.foodordering;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        CustomerDAO customerDAO = new CustomerDAO();
        MenuDAO menuDAO = new MenuDAO();
        OrderDAO orderDAO = new OrderDAO();

        while (true) {

            System.out.println("\n========================================");
            System.out.println("       ONLINE FOOD ORDERING SYSTEM");
            System.out.println("========================================");
            System.out.println("1. Customer Registration");
            System.out.println("2. View Menu");
            System.out.println("3. Place Order");
            System.out.println("4. Order History");
            System.out.println("5. Generate Bill");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:

                    sc.nextLine();

                    System.out.print("Enter Customer Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Email: ");
                    String email = sc.nextLine();

                    System.out.print("Enter Phone: ");
                    String phone = sc.nextLine();

                    customerDAO.addCustomer(
                            name,
                            email,
                            phone
                    );

                    break;

                case 2:

                    menuDAO.viewMenu();
                    break;

                case 3:

                    orderDAO.placeOrder();
                    break;

                case 4:

                    System.out.print("Enter Customer ID: ");
                    int customerId = sc.nextInt();

                    orderDAO.viewOrderHistory(customerId);
                    break;

                case 5:

                    System.out.print("Enter Order ID: ");
                    int orderId = sc.nextInt();

                    orderDAO.generateBill(orderId);
                    break;

                case 6:

                    System.out.println("Thank you for using Online Food Ordering System!");

                            
                    sc.close();
                    System.exit(0);

                default:

                    System.out.println("Invalid choice!");
            }
        }
    }
}