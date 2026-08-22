package com.foodordering;

public class CustomerTest {

    public static void main(String[] args) {

        CustomerDAO dao = new CustomerDAO();

        dao.addCustomer(
                "Kiran",
                "kiran@gmail.com",
                "9876543213"
        );

        dao.viewCustomers();
    }
}