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
        	System.out.println("6. Track Order Status");
        	System.out.println("7. Admin Login");
        	System.out.println("8. Exit");

        	System.out.print("Enter your choice: ");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:

                    sc.nextLine();

                    String name;
                    String email;
                    String phone;

                    // Name Validation
                    while (true) {

                        System.out.print("Enter Customer Name: ");
                        name = sc.nextLine();

                        if (ValidationUtil.isValidName(name)) {
                            break;
                        }

                        System.out.println( "Invalid name! Use letters and spaces only." );
                                                 
                    }

                    // Email Validation
                    while (true) {

                        System.out.print("Enter Email: ");
                        email = sc.nextLine();

                        if (ValidationUtil.isValidEmail(email)) {
                            break;
                        }

                        System.out.println("Invalid email! Please enter a valid email.");             
                        
                    }

                    // Phone Validation
                    while (true) {

                        System.out.print("Enter Phone: ");
                        phone = sc.nextLine();

                        if (ValidationUtil.isValidPhone(phone)) {
                            break;
                        }

                        System.out.println("Invalid phone! Enter exactly 10 digits." );              
                                
                    }

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

                    System.out.print("Enter Order ID: ");

                    int trackOrderId = sc.nextInt();

                    orderDAO.trackOrderStatus(trackOrderId);

                    break;

                case 7:

                    sc.nextLine();

                    System.out.print("Enter Admin Username: ");
                    String username = sc.nextLine();

                    System.out.print("Enter Admin Password: ");
                    String password = sc.nextLine();

                    if (username.equals("admin") && password.equals("admin123")) {

                        System.out.println("\nAdmin login successful!");

                        adminMenu(sc, orderDAO);

                    } else {

                        System.out.println("Invalid admin username or password!");
                    }

                    break;


                case 8:

                    System.out.println("Thank you for using Online Food Ordering System!");
                                                  
                    sc.close();
                    System.exit(0);


                default:

                    System.out.println("Invalid choice!");
            }
        }
    }
    
    public static void adminMenu( Scanner sc,OrderDAO orderDAO)
    {
           
        while (true) {

            System.out.println("\n========================================");
                    
            System.out.println("             ADMIN MENU");
                    
            System.out.println("========================================");
                    
            System.out.println("1. View All Orders");

            System.out.println("2. Update Order Status");

            System.out.println("3. Track Order Status");

            System.out.println("4. Logout");

            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();


            switch (choice) {

            case 1:

                orderDAO.viewAllOrders();

                break;


            case 2:

                System.out.print("Enter Order ID: ");
                        
                int orderId = sc.nextInt();

                System.out.println("\n========== UPDATE ORDER STATUS ==========");
     
                System.out.println("1. PREPARING");

                System.out.println( "2. OUT_FOR_DELIVERY");
                       
                System.out.println("3. DELIVERED");

                System.out.println("4. CANCELLED");

                System.out.print("Enter status choice: ");
                        
                int statusChoice = sc.nextInt();

                String status = "";

                switch (statusChoice) {

                case 1:

                    status = "PREPARING";
                    break;

                case 2:

                    status = "OUT_FOR_DELIVERY";
                    break;

                case 3:

                    status = "DELIVERED";
                    break;

                case 4:

                    status = "CANCELLED";
                    break;

                default:
                    System.out.println("Invalid status choice!");
                            
                }

                if (!status.isEmpty()) {

                    orderDAO.updateOrderStatus(orderId, status);
                                                        
                }
                break;
                
            case 3:

                System.out.print("Enter Order ID: ");
                
                int trackOrderId = sc.nextInt();

                orderDAO.trackOrderStatus(trackOrderId);
                        
                break;


            case 4:

                System.out.println("Admin logged out successfully!");    
                return;


            default:

                System.out.println("Invalid choice!");
                        
            }
        }
    }
}